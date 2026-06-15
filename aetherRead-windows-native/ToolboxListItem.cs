using System.Windows;
using System.Windows.Controls;
using System.Windows.Media;
using WpfBrushes = System.Windows.Media.Brushes;

namespace aetherRead_windows_native
{
    /// <summary>
    /// A single row in the Toolbox list: emoji icon + title + subtitle + chevron.
    /// </summary>
    public class ToolboxListItem : System.Windows.Controls.Button
    {
        public static readonly DependencyProperty EmojiProperty =
            DependencyProperty.Register(nameof(Emoji), typeof(string), typeof(ToolboxListItem),
                new PropertyMetadata("📄", OnContentChanged));

        public static readonly DependencyProperty TitleProperty =
            DependencyProperty.Register(nameof(Title), typeof(string), typeof(ToolboxListItem),
                new PropertyMetadata("Tool", OnContentChanged));

        public static readonly DependencyProperty SubtitleProperty =
            DependencyProperty.Register(nameof(Subtitle), typeof(string), typeof(ToolboxListItem),
                new PropertyMetadata(string.Empty, OnContentChanged));

        public string Emoji    { get => (string)GetValue(EmojiProperty);    set => SetValue(EmojiProperty, value); }
        public string Title    { get => (string)GetValue(TitleProperty);    set => SetValue(TitleProperty, value); }
        public string Subtitle { get => (string)GetValue(SubtitleProperty); set => SetValue(SubtitleProperty, value); }

        public ToolboxListItem()
        {
            Cursor          = System.Windows.Input.Cursors.Hand;
            HorizontalContentAlignment = System.Windows.HorizontalAlignment.Stretch;
            Padding         = new Thickness(0);
            Background      = WpfBrushes.Transparent;
            BorderThickness = new Thickness(0);
            BuildContent();
        }

        private static void OnContentChanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
            => (d as ToolboxListItem)?.BuildContent();

        private void BuildContent()
        {
            // Outer border (acts as the hover container)
            var border = new Border
            {
                Padding         = new Thickness(16, 14, 16, 14),
                CornerRadius    = new CornerRadius(0),
                BorderThickness = new Thickness(0, 0, 0, 1),
            };
            border.SetResourceReference(Border.BorderBrushProperty, "DividerColor");
            border.SetResourceReference(Border.BackgroundProperty, "AppBackground");

            var row = new Grid();
            row.ColumnDefinitions.Add(new ColumnDefinition { Width = new GridLength(48) });
            row.ColumnDefinitions.Add(new ColumnDefinition { Width = new GridLength(1, GridUnitType.Star) });
            row.ColumnDefinitions.Add(new ColumnDefinition { Width = new GridLength(24) });

            // Emoji
            var emoji = new TextBlock
            {
                Text              = Emoji,
                FontSize          = 26,
                VerticalAlignment = VerticalAlignment.Center,
            };
            Grid.SetColumn(emoji, 0);

            // Title + Subtitle
            var textCol = new StackPanel { VerticalAlignment = VerticalAlignment.Center };

            var titleTb = new TextBlock
            {
                Text       = Title,
                FontWeight = FontWeights.SemiBold,
                FontSize   = 14,
                TextTrimming = TextTrimming.CharacterEllipsis,
            };
            titleTb.SetResourceReference(TextBlock.ForegroundProperty, "TextPrimary");

            var subtitleTb = new TextBlock
            {
                Text        = Subtitle,
                FontSize    = 12,
                TextWrapping = TextWrapping.Wrap,
                Margin      = new Thickness(0, 3, 0, 0),
            };
            subtitleTb.SetResourceReference(TextBlock.ForegroundProperty, "TextSecondary");

            textCol.Children.Add(titleTb);
            textCol.Children.Add(subtitleTb);
            Grid.SetColumn(textCol, 1);

            // Chevron
            var chevron = new TextBlock
            {
                Text              = "›",
                FontSize          = 20,
                VerticalAlignment = VerticalAlignment.Center,
                HorizontalAlignment = System.Windows.HorizontalAlignment.Center,
            };
            chevron.SetResourceReference(TextBlock.ForegroundProperty, "TextSecondary");
            Grid.SetColumn(chevron, 2);

            row.Children.Add(emoji);
            row.Children.Add(textCol);
            row.Children.Add(chevron);
            border.Child = row;

            Content = border;
        }
    }
}
