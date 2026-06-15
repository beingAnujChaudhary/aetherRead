import type { Metadata, Viewport } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: {
    default: 'AetherRead — Smart Reading & Research Platform',
    template: '%s | AetherRead',
  },
  description:
    'AetherRead is an AI-powered, offline-first PDF reader and research workspace. Built by Anuj Chaudhary (BS Data Science, IIT Madras). Read smarter with local AI, cross-platform sync, and distraction-free themes.',
  keywords: ['PDF reader', 'AI research', 'offline reading', 'annotations', 'RAG', 'local AI', 'IIT Madras', 'Anuj Chaudhary'],
  authors: [{ name: 'Anuj Chaudhary', url: 'https://beinganujchaudhary.web.app' }],
  creator: 'Anuj Chaudhary',
  openGraph: {
    type: 'website',
    locale: 'en_US',
    url: 'https://beinganujchaudhary.web.app/projects/AetherRead.html',
    siteName: 'AetherRead',
    title: 'AetherRead — Smart Reading & Research Platform',
    description: 'Read, annotate and chat with your PDFs — entirely offline. A portfolio project by Anuj Chaudhary.',
  },
  twitter: {
    card: 'summary_large_image',
    title: 'AetherRead — by Anuj Chaudhary',
    description: 'AI-powered offline PDF reader and research workspace. Portfolio project by Anuj Chaudhary, BS Data Science at IIT Madras.',
    creator: '@beinganujchaudhary',
  },
  manifest: '/manifest.json',
  icons: {
    icon: '/favicon.ico',
    apple: '/apple-touch-icon.png',
  },
};

export const viewport: Viewport = {
  themeColor: '#0f0f1a',
  colorScheme: 'dark',
  width: 'device-width',
  initialScale: 1,
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body>{children}</body>
    </html>
  );
}
