using Microsoft.Win32;
using PdfiumViewer;
using System.IO;
using System.Windows;

namespace aetherRead_windows_native
{
    public partial class MainWindow : Window
    {
        private PdfViewer _pdfViewer;

        public MainWindow()
        {
            InitializeComponent();
            
            // Initialize PdfiumViewer WinForms control
            _pdfViewer = new PdfViewer();
            FormsHost.Child = _pdfViewer;
        }

        private void BtnLibrary_Click(object sender, RoutedEventArgs e)
        {
            LibraryView.Visibility = Visibility.Visible;
            ToolboxView.Visibility = Visibility.Collapsed;
            ReaderView.Visibility = Visibility.Collapsed;
        }

        private void BtnToolbox_Click(object sender, RoutedEventArgs e)
        {
            LibraryView.Visibility = Visibility.Collapsed;
            ToolboxView.Visibility = Visibility.Visible;
            ReaderView.Visibility = Visibility.Collapsed;
        }

        private void BtnOpenPdf_Click(object sender, RoutedEventArgs e)
        {
            Microsoft.Win32.OpenFileDialog openFileDialog = new Microsoft.Win32.OpenFileDialog();
            openFileDialog.Filter = "PDF files (*.pdf)|*.pdf|All files (*.*)|*.*";
            
            if (openFileDialog.ShowDialog() == true)
            {
                LoadPdf(openFileDialog.FileName);
            }
        }

        private void LoadPdf(string filePath)
        {
            try
            {
                var pdfDocument = PdfDocument.Load(filePath);
                _pdfViewer.Document = pdfDocument;
                
                TxtFileName.Text = Path.GetFileName(filePath);
                
                LibraryView.Visibility = Visibility.Collapsed;
                ToolboxView.Visibility = Visibility.Collapsed;
                ReaderView.Visibility = Visibility.Visible;
            }
            catch (System.Exception ex)
            {
                System.Windows.MessageBox.Show($"Could not load PDF: {ex.Message}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void BtnClosePdf_Click(object sender, RoutedEventArgs e)
        {
            _pdfViewer.Document?.Dispose();
            _pdfViewer.Document = null;
            
            LibraryView.Visibility = Visibility.Visible;
            ToolboxView.Visibility = Visibility.Collapsed;
            ReaderView.Visibility = Visibility.Collapsed;
        }
    }
}