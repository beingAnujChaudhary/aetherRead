'use client';

import { useEffect, useState } from 'react';
import { useRouter, useParams } from 'next/navigation';
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
  // useParams() reads the real document ID from the browser URL at runtime.
  // The static export always generates _placeholder as the id, but useParams()
  // gives us the actual ID the user navigated to.
  const params = useParams();
  const id = typeof params?.id === 'string' ? params.id : Array.isArray(params?.id) ? params.id[0] : null;

  const [document, setDocument] = useState<Document | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const { setDocument: initReader, setTheme, setPage, startSession } = useReaderStore();
  const { loadAnnotations, isFormOpen } = useAnnotationStore();

  useEffect(() => {
    if (!id) return; // Wait until URL params are available
    const loadDocument = async () => {
      try {
        const doc = await db.documents.get(id);
        if (!doc) {
          setError('Document not found. It may have been deleted.');
          return;
        }

        setDocument(doc);

        // Restore reading state
        const savedState = await getReadingState(id);
        if (savedState) {
          setTheme(savedState.activeTheme);
          setPage(savedState.currentPage);
        }

        // Initialize reader and annotations
        initReader(id, doc.pageCount);
        await loadAnnotations(id);
        await updateLastOpened(id);
        startSession();
      } catch (e) {
        setError('Failed to load document. Please try again.');
        console.error(e);
      } finally {
        setIsLoading(false);
      }
    };

    loadDocument();
  }, [id]);

  if (isLoading) {
    return (
      <div className="min-h-screen flex flex-col items-center justify-center gap-4">
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

  if (error || !document) {
    return (
      <div className="min-h-screen flex flex-col items-center justify-center gap-4 px-6">
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

  // By this point id is non-null (document loaded successfully)
  const docId = id!;

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
