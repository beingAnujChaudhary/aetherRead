'use client';

import { useEffect, useRef, useMemo, useCallback } from 'react';
import { Worker, Viewer, SpecialZoomLevel } from '@react-pdf-viewer/core';
import { pageNavigationPlugin } from '@react-pdf-viewer/page-navigation';
import { useReaderStore } from '@/stores/useReaderStore';
import { THEMES } from '@/lib/utils';

import '@react-pdf-viewer/core/lib/styles/index.css';
import '@react-pdf-viewer/page-navigation/lib/styles/index.css';

interface PDFViewerProps {
  fileData: ArrayBuffer;
  documentId: string;
}

// Use a pinned CDN URL for the worker that matches pdfjs-dist 3.11.174
const WORKER_URL = `https://unpkg.com/pdfjs-dist@3.11.174/build/pdf.worker.min.js`;

export default function PDFViewer({ fileData, documentId }: PDFViewerProps) {
  const { currentPage, activeTheme, setPage, setDocument } = useReaderStore();
  const theme = THEMES[activeTheme];

  // ── Stable blob URL ───────────────────────────────────────────────────────
  // Create the object URL once and revoke it when the component unmounts or
  // fileData changes, preventing a new URL (and re-render) on every paint.
  const blobUrlRef = useRef<string | null>(null);

  const fileUrl = useMemo(() => {
    // Revoke any previous URL before creating a new one
    if (blobUrlRef.current) {
      URL.revokeObjectURL(blobUrlRef.current);
    }
    const blob = new Blob([fileData], { type: 'application/pdf' });
    const url = URL.createObjectURL(blob);
    blobUrlRef.current = url;
    return url;
  }, [fileData]);

  // Revoke the URL when the component is unmounted
  useEffect(() => {
    return () => {
      if (blobUrlRef.current) {
        URL.revokeObjectURL(blobUrlRef.current);
        blobUrlRef.current = null;
      }
    };
  }, []);

  // ── Page navigation plugin ────────────────────────────────────────────────
  const pageNavigationPluginInstance = pageNavigationPlugin();
  const { jumpToPage } = pageNavigationPluginInstance;

  // Restore page position on first load
  const didRestorePage = useRef(false);
  useEffect(() => {
    if (!didRestorePage.current && currentPage > 1 && jumpToPage) {
      didRestorePage.current = true;
      setTimeout(() => jumpToPage(currentPage - 1), 500);
    }
  }, [jumpToPage]);

  // Jump when toolbar changes the page
  const prevPageRef = useRef(currentPage);
  useEffect(() => {
    if (prevPageRef.current !== currentPage && jumpToPage) {
      prevPageRef.current = currentPage;
      jumpToPage(currentPage - 1);
    }
  }, [currentPage, jumpToPage]);

  const handlePageChange = useCallback(
    (e: { currentPage: number }) => {
      setPage(e.currentPage + 1);
    },
    [setPage],
  );

  const handleDocumentLoad = useCallback(
    (e: { doc: { numPages: number } }) => {
      setDocument(documentId, e.doc.numPages);
    },
    [documentId, setDocument],
  );

  return (
    <div
      className="flex-1 overflow-hidden relative transition-colors duration-500"
      style={{ background: theme.bg }}
      data-reader-theme={activeTheme}
    >
      {/* Theme overlay (sepia / focus-punch) */}
      {theme.overlay && (
        <div
          className="absolute inset-0 pointer-events-none z-10"
          style={{ background: theme.overlay }}
        />
      )}

      <div
        className="h-full overflow-auto transition-all duration-300"
        style={{
          filter: activeTheme === 'monochrome' ? 'grayscale(0.5)' : 'none',
        }}
      >
        <Worker workerUrl={WORKER_URL}>
          <Viewer
            fileUrl={fileUrl}
            defaultScale={SpecialZoomLevel.PageFit}
            plugins={[pageNavigationPluginInstance]}
            onPageChange={handlePageChange}
            onDocumentLoad={handleDocumentLoad}
            theme={{
              theme: activeTheme === 'dark-abyss' || activeTheme === 'focus-punch' ? 'dark' : 'light',
            }}
          />
        </Worker>
      </div>
    </div>
  );
}
