'use client';

import { useEffect, useState } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import { db, type Document } from '@/lib/db';
import { useReaderStore } from '@/stores/useReaderStore';
import { useAnnotationStore } from '@/stores/useAnnotationStore';
import { getReadingState, updateLastOpened } from '@/lib/db';
import { Loader2, AlertCircle } from 'lucide-react';
import dynamic from 'next/dynamic';
import ReaderToolbar from '@/components/reader/ReaderToolbar';
import AnnotationPanel from '@/components/reader/AnnotationPanel';
import AnnotationForm from '@/components/reader/AnnotationForm';
import AIChatPanel from '@/components/reader/AIChatPanel';

// Dynamically import PDF viewer to avoid SSR issues with pdfjs
const PDFViewer = dynamic(() => import('@/components/reader/PDFViewer'), { ssr: false });

export default function ReaderClient() {
  const router = useRouter();

  const searchParams = useSearchParams();
  const docId = searchParams?.get('id') ?? null;

  const [document, setDocument] = useState<Document | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const { setDocument: initReader, setTheme, setPage, startSession } = useReaderStore();
  const { loadAnnotations, isFormOpen } = useAnnotationStore();

  useEffect(() => {
    // Guard: wait for pathname to resolve, and ignore the placeholder route
    if (!docId || docId === '_placeholder') {
      setError('No document specified.');
      setIsLoading(false);
      return;
    }

    const loadDocument = async () => {
      try {
        const doc = await db.documents.get(docId);
        if (!doc) {
          setError('Document not found. It may have been deleted.');
          return;
        }

        setDocument(doc);

        // Restore reading state
        const savedState = await getReadingState(docId);
        if (savedState) {
          setTheme(savedState.activeTheme);
          setPage(savedState.currentPage);
        }

        // Initialize reader and annotations
        initReader(docId, doc.pageCount);
        await loadAnnotations(docId);
        await updateLastOpened(docId);
        startSession();
      } catch (e) {
        setError('Failed to load document. Please try again.');
        console.error(e);
      } finally {
        setIsLoading(false);
      }
    };

    loadDocument();
  }, [docId]);

  if (isLoading) {
    return (
      <div className="min-h-screen flex flex-col items-center justify-center gap-4" style={{ background: 'var(--color-bg)' }}>
        <div className="w-14 h-14 rounded-2xl bg-aether-500/10 border border-aether-500/20 flex items-center justify-center">
          <Loader2 size={28} className="text-aether-400 animate-spin" />
        </div>
        <div className="text-center">
          <p className="text-white font-semibold">Loading document...</p>
          <p className="text-sm text-[var(--color-text-muted)] mt-1">Restoring your reading position</p>
        </div>
      </div>
    );
  }

  if (error || !document || !docId) {
    return (
      <div className="min-h-screen flex flex-col items-center justify-center gap-4 px-6" style={{ background: 'var(--color-bg)' }}>
        <div className="w-14 h-14 rounded-2xl bg-red-500/10 border border-red-500/20 flex items-center justify-center">
          <AlertCircle size={28} className="text-red-400" />
        </div>
        <div className="text-center">
          <p className="text-white font-semibold">{error ?? 'Document not found'}</p>
          <button
            onClick={() => router.push('/app')}
            className="mt-4 px-4 py-2 rounded-lg bg-aether-500/15 text-aether-400 text-sm hover:bg-aether-500/25 transition-colors"
          >
            Back to Library
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="h-screen flex flex-col overflow-hidden">
      <ReaderToolbar title={document.title} />

      <div className="flex flex-1 overflow-hidden">
        {/* PDF Viewer */}
        <PDFViewer fileData={document.fileData} documentId={docId} />

        {/* Side panels */}
        <AnnotationPanel documentId={docId} />
        <AIChatPanel />
      </div>

      {/* Annotation form modal */}
      {isFormOpen && <AnnotationForm documentId={docId} currentPage={0} />}
    </div>
  );
}
