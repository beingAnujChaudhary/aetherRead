using Microsoft.Win32;
using PdfiumViewer;
using System;
using System.Collections.Generic;
using System.IO;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using WpfColor    = System.Windows.Media.Color;
using WpfColors   = System.Windows.Media.Colors;
using WpfBrush    = System.Windows.Media.SolidColorBrush;
using WpfBrushes  = System.Windows.Media.Brushes;
using WpfOrient   = System.Windows.Controls.Orientation;
using WpfButton   = System.Windows.Controls.Button;
using WpfApp      = System.Windows.Application;

namespace aetherRead_windows_native
{
    public partial class MainWindow : Window
    {
        // ── PDF state ──────────────────────────────────────────
        private PdfViewer _pdfViewer;
        private PdfDocument? _currentDoc;
        private string _currentFilePath = string.Empty;
        private int _currentPage = 0;
        private double _currentZoom = 1.0;

        // ── Theme state ────────────────────────────────────────
        private bool _isDarkMode = false;

        // ── Recent files (in-memory list of (name, path)) ─────
        private readonly List<(string Name, string Path)> _recentFiles = new();

        // ── Nav state ─────────────────────────────────────────
        private enum NavTab { Files, Toolbox }
        private NavTab _activeTab = NavTab.Files;

        public MainWindow()
        {
            InitializeComponent();

            // Initialize PdfiumViewer WinForms control
            _pdfViewer = new PdfViewer
            {
                Dock = System.Windows.Forms.DockStyle.Fill,
                BackColor = System.Drawing.Color.FromArgb(17, 17, 17),
            };
            FormsHost.Child = _pdfViewer;

            // Keyboard shortcuts
            KeyDown += OnKeyDown;
        }

        // ─────────────────────────────────────────────────────────
        //  NAVIGATION
        // ─────────────────────────────────────────────────────────

        private void BtnFiles_Click(object sender, RoutedEventArgs e) => SwitchTab(NavTab.Files);
        private void BtnToolbox_Click(object sender, RoutedEventArgs e) => SwitchTab(NavTab.Toolbox);

        private void SwitchTab(NavTab tab)
        {
            _activeTab = tab;

            // Show/hide views
            FilesView.Visibility   = tab == NavTab.Files   ? Visibility.Visible : Visibility.Collapsed;
            ToolboxView.Visibility = tab == NavTab.Toolbox ? Visibility.Visible : Visibility.Collapsed;
            ReaderView.Visibility  = Visibility.Collapsed;

            // Update active indicator colors
            UpdateNavActiveState();
        }

        private void UpdateNavActiveState()
        {
            // Reset both
            var normalFg = (WpfBrush)FindResource("TextSecondary");
            var activeFg = (WpfBrush)FindResource("NavActiveColor");
            var activeBg = (WpfBrush)FindResource("NavActiveBg");

            // Files button
            BtnFiles.Background   = _activeTab == NavTab.Files   ? activeBg : WpfBrushes.Transparent;
            BtnToolbox.Background = _activeTab == NavTab.Toolbox ? activeBg : WpfBrushes.Transparent;
        }

        // ─────────────────────────────────────────────────────────
        //  THEME TOGGLE
        // ─────────────────────────────────────────────────────────

        private void BtnDarkMode_Click(object sender, RoutedEventArgs e)
        {
            _isDarkMode = !_isDarkMode;
            ApplyTheme();
        }

        private void ApplyTheme()
        {
            var dict = WpfApp.Current.Resources;
            if (_isDarkMode)
            {
                dict["AppBackground"] = new WpfBrush(WpfColor.FromRgb(0x0D, 0x0D, 0x0D));
                dict["SidebarBg"]     = new WpfBrush(WpfColor.FromRgb(0x1A, 0x1A, 0x1A));
                dict["SidebarBorder"] = new WpfBrush(WpfColor.FromRgb(0x2A, 0x2A, 0x2A));
                dict["ContentBg"]     = new WpfBrush(WpfColor.FromRgb(0x11, 0x11, 0x11));
                dict["CardBg"]        = new WpfBrush(WpfColor.FromRgb(0x1E, 0x1E, 0x1E));
                dict["CardBorder"]    = new WpfBrush(WpfColor.FromRgb(0x2E, 0x2E, 0x2E));
                dict["ToolbarBg"]     = new WpfBrush(WpfColor.FromRgb(0x1A, 0x1A, 0x1A));
                dict["ToolbarBorder"] = new WpfBrush(WpfColor.FromRgb(0x2A, 0x2A, 0x2A));
                dict["TextPrimary"]   = new WpfBrush(WpfColor.FromRgb(0xE8, 0xE8, 0xE8));
                dict["TextSecondary"] = new WpfBrush(WpfColor.FromRgb(0x88, 0x88, 0x88));
                dict["NavActiveBg"]   = new WpfBrush(WpfColor.FromRgb(0x2A, 0x12, 0x10));
                dict["HoverBg"]       = new WpfBrush(WpfColor.FromRgb(0x22, 0x22, 0x22));
                dict["DividerColor"]  = new WpfBrush(WpfColor.FromRgb(0x2A, 0x2A, 0x2A));

                TxtThemeIcon.Text = "☀️";
                _pdfViewer.BackColor = System.Drawing.Color.FromArgb(17, 17, 17);
            }
            else
            {
                dict["AppBackground"] = new WpfBrush(WpfColor.FromRgb(0xF0, 0xED, 0xE8));
                dict["SidebarBg"]     = new WpfBrush(WpfColors.White);
                dict["SidebarBorder"] = new WpfBrush(WpfColor.FromRgb(0xE0, 0xE0, 0xE0));
                dict["ContentBg"]     = new WpfBrush(WpfColor.FromRgb(0xF7, 0xF4, 0xF0));
                dict["CardBg"]        = new WpfBrush(WpfColors.White);
                dict["CardBorder"]    = new WpfBrush(WpfColor.FromRgb(0xE8, 0xE4, 0xDE));
                dict["ToolbarBg"]     = new WpfBrush(WpfColors.White);
                dict["ToolbarBorder"] = new WpfBrush(WpfColor.FromRgb(0xE0, 0xE0, 0xE0));
                dict["TextPrimary"]   = new WpfBrush(WpfColor.FromRgb(0x1A, 0x1A, 0x1A));
                dict["TextSecondary"] = new WpfBrush(WpfColor.FromRgb(0x88, 0x88, 0x88));
                dict["NavActiveBg"]   = new WpfBrush(WpfColor.FromRgb(0xFF, 0xF0, 0xEE));
                dict["HoverBg"]       = new WpfBrush(WpfColor.FromRgb(0xF5, 0xF2, 0xEE));
                dict["DividerColor"]  = new WpfBrush(WpfColor.FromRgb(0xE8, 0xE4, 0xDE));

                TxtThemeIcon.Text = "🌙";
                _pdfViewer.BackColor = System.Drawing.Color.FromArgb(247, 244, 240);
            }

            UpdateNavActiveState();
        }

        // ─────────────────────────────────────────────────────────
        //  PROFILE
        // ─────────────────────────────────────────────────────────

        private void BtnProfile_Click(object sender, RoutedEventArgs e)
        {
            System.Windows.MessageBox.Show(
                "My AetherRead\n\nVersion 1.0 (Windows Native WPF)\n\nBuilt by Anuj Chaudhary\nhttps://beinganujchaudhary.web.app",
                "My AetherRead",
                MessageBoxButton.OK,
                MessageBoxImage.Information);
        }

        // ─────────────────────────────────────────────────────────
        //  PDF IMPORT & LOADING
        // ─────────────────────────────────────────────────────────

        private void BtnOpenPdf_Click(object sender, RoutedEventArgs e)
        {
            var dlg = new Microsoft.Win32.OpenFileDialog
            {
                Filter = "PDF files (*.pdf)|*.pdf|All files (*.*)|*.*",
                Title  = "Open PDF — aetherRead"
            };

            if (dlg.ShowDialog() == true)
                LoadPdf(dlg.FileName);
        }

        private void LoadPdf(string filePath)
        {
            try
            {
                var doc = PdfDocument.Load(filePath);
                _currentDoc?.Dispose();
                _currentDoc = doc;
                _currentFilePath = filePath;
                _currentPage = 0;
                _currentZoom = 1.0;

                _pdfViewer.Document = doc;

                var name = Path.GetFileName(filePath);
                TxtFileName.Text = name;
                TxtStatusPath.Text = filePath;
                TxtZoom.Text = "100%";
                UpdatePageInfo();

                // Show reader
                FilesView.Visibility   = Visibility.Collapsed;
                ToolboxView.Visibility = Visibility.Collapsed;
                ReaderView.Visibility  = Visibility.Visible;

                // Add to recent list
                AddToRecent(name, filePath);
            }
            catch (Exception ex)
            {
                System.Windows.MessageBox.Show(
                    $"Could not load PDF:\n{ex.Message}",
                    "Error",
                    MessageBoxButton.OK,
                    MessageBoxImage.Error);
            }
        }

        private void AddToRecent(string name, string path)
        {
            // Remove duplicate
            _recentFiles.RemoveAll(f => f.Path == path);
            _recentFiles.Insert(0, (name, path));

            // Keep max 20
            if (_recentFiles.Count > 20)
                _recentFiles.RemoveAt(_recentFiles.Count - 1);

            RefreshRecentUI();
        }

        private void RefreshRecentUI()
        {
            RecentFilesList.Children.Clear();

            if (_recentFiles.Count == 0)
            {
                RecentFilesList.Children.Add(EmptyState);
                return;
            }

            EmptyState.Visibility = Visibility.Collapsed;

            foreach (var (name, path) in _recentFiles)
            {
                var btn = new WpfButton
                {
                    Style       = FindResource("ToolboxItemStyle") as Style,
                    Margin      = new Thickness(0, 0, 0, 4),
                    HorizontalContentAlignment = System.Windows.HorizontalAlignment.Left,
                    Tag         = path
                };

                var panel = new StackPanel { Orientation = WpfOrient.Horizontal };
                panel.Children.Add(new TextBlock
                {
                    Text       = "📄",
                    FontSize   = 22,
                    Margin     = new Thickness(0, 0, 14, 0),
                    VerticalAlignment = VerticalAlignment.Center
                });

                var textCol = new StackPanel { VerticalAlignment = VerticalAlignment.Center };
                textCol.Children.Add(new TextBlock
                {
                    Text            = name,
                    FontWeight      = FontWeights.SemiBold,
                    FontSize        = 13,
                    TextTrimming    = TextTrimming.CharacterEllipsis,
                    Foreground      = (WpfBrush)FindResource("TextPrimary"),
                });
                textCol.Children.Add(new TextBlock
                {
                    Text         = path,
                    FontSize     = 11,
                    TextTrimming = TextTrimming.CharacterEllipsis,
                    Foreground   = (WpfBrush)FindResource("TextSecondary"),
                    Margin       = new Thickness(0, 2, 0, 0),
                });
                panel.Children.Add(textCol);

                btn.Content = panel;
                btn.Click  += (s, _) => { if (btn.Tag is string p) LoadPdf(p); };

                RecentFilesList.Children.Add(btn);
            }
        }

        // ─────────────────────────────────────────────────────────
        //  READER CONTROLS
        // ─────────────────────────────────────────────────────────

        private void BtnClosePdf_Click(object sender, RoutedEventArgs e)
        {
            ReaderView.Visibility = Visibility.Collapsed;
            SwitchTab(_activeTab);
        }

        private void BtnZoomIn_Click(object sender, RoutedEventArgs e)
        {
            _currentZoom = Math.Min(_currentZoom + 0.25, 4.0);
            ApplyZoom();
        }

        private void BtnZoomOut_Click(object sender, RoutedEventArgs e)
        {
            _currentZoom = Math.Max(_currentZoom - 0.25, 0.25);
            ApplyZoom();
        }

        private void ApplyZoom()
        {
            TxtZoom.Text = $"{(int)(_currentZoom * 100)}%";
            // PdfiumViewer: zoom is applied via Renderer
            try { _pdfViewer.Renderer.Zoom = _currentZoom; } catch { }
        }

        private void BtnPrevPage_Click(object sender, RoutedEventArgs e)
        {
            if (_currentPage > 0)
            {
                _currentPage--;
                try
                {
                    // Scroll viewer based page navigation — reload doc at page offset
                    _pdfViewer.Document = _currentDoc;
                } catch { }
                UpdatePageInfo();
            }
        }

        private void BtnNextPage_Click(object sender, RoutedEventArgs e)
        {
            if (_currentDoc != null && _currentPage < _currentDoc.PageCount - 1)
            {
                _currentPage++;
                try
                {
                    _pdfViewer.Document = _currentDoc;
                } catch { }
                UpdatePageInfo();
            }
        }

        private void UpdatePageInfo()
        {
            int total = _currentDoc?.PageCount ?? 1;
            TxtPageInfo.Text = $"Page {_currentPage + 1} / {total}";
        }

        // ─────────────────────────────────────────────────────────
        //  KEYBOARD SHORTCUTS
        // ─────────────────────────────────────────────────────────

        private void OnKeyDown(object sender, System.Windows.Input.KeyEventArgs e)
        {
            if (ReaderView.Visibility != Visibility.Visible) return;

            switch (e.Key)
            {
                case Key.Left:
                case Key.PageUp:
                    BtnPrevPage_Click(sender, new RoutedEventArgs());
                    break;
                case Key.Right:
                case Key.PageDown:
                    BtnNextPage_Click(sender, new RoutedEventArgs());
                    break;
                case Key.Add:
                case Key.OemPlus when Keyboard.Modifiers == ModifierKeys.Control:
                    BtnZoomIn_Click(sender, new RoutedEventArgs());
                    break;
                case Key.Subtract:
                case Key.OemMinus when Keyboard.Modifiers == ModifierKeys.Control:
                    BtnZoomOut_Click(sender, new RoutedEventArgs());
                    break;
                case Key.Escape:
                    BtnClosePdf_Click(sender, new RoutedEventArgs());
                    break;
            }
        }

        // ─────────────────────────────────────────────────────────
        //  COMING SOON STUB
        // ─────────────────────────────────────────────────────────

        private void ShowComingSoon(object sender, RoutedEventArgs e)
        {
            var btn = sender as WpfButton;
            var title = "this tool";
            // Try to get the title from the button's StackPanel
            if (btn?.Content is StackPanel sp)
                foreach (var child in sp.Children)
                    if (child is TextBlock tb && tb.FontSize <= 12)
                    { title = tb.Text; break; }

            System.Windows.MessageBox.Show(
                $"\"{title}\" is coming soon in a future update.\n\nStay tuned! 🚀",
                "Coming Soon",
                MessageBoxButton.OK,
                MessageBoxImage.Information);
        }
    }
}