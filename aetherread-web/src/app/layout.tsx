import type { Metadata, Viewport } from 'next';
import './globals.css';
import { AuthProvider } from '@/contexts/AuthContext';

export const metadata: Metadata = {
  title: {
    default: 'aetherRead — Smart Reading & Research Platform',
    template: '%s | aetherRead',
  },
  description:
    'aetherRead is an AI-powered, offline-first PDF reader and research workspace. Built by Anuj Chaudhary (BS Data Science, IIT Madras). Read smarter with local AI, cross-platform sync, and distraction-free themes.',
  keywords: ['PDF reader', 'AI research', 'offline reading', 'annotations', 'RAG', 'local AI', 'IIT Madras', 'Anuj Chaudhary'],
  authors: [{ name: 'Anuj Chaudhary', url: 'https://beinganujchaudhary.web.app' }],
  creator: 'Anuj Chaudhary',
  openGraph: {
    type: 'website',
    locale: 'en_US',
    url: 'https://beinganujchaudhary.web.app',
    siteName: 'aetherRead',
    title: 'aetherRead — Smart Reading & Research Platform',
    description: 'Read, annotate and chat with your PDFs — entirely offline. A portfolio project by Anuj Chaudhary.',
  },
  twitter: {
    card: 'summary_large_image',
    title: 'aetherRead — by Anuj Chaudhary',
    description: 'AI-powered offline PDF reader and research workspace. Portfolio project by Anuj Chaudhary, BS Data Science at IIT Madras.',
    creator: '@beinganujchaudhary',
  },
  manifest: '/projects/aetherRead/manifest.json',
};

export const viewport: Viewport = {
  themeColor: '#EFEAE3',
  colorScheme: 'light dark',
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
      <body>
        <AuthProvider>{children}</AuthProvider>
      </body>
    </html>
  );
}
