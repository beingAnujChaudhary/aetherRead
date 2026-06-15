'use client';

import { useEffect, useRef, useMemo, useCallback } from 'react';
import { Worker, Viewer, SpecialZoomLevel } from '@react-pdf-viewer/core';
import { pageNavigationPlugin } from '@react-pdf-viewer/page-navigation';
import { zoomPlugin } from '@react-pdf-viewer/zoom';
import { highlightPlugin } from '@react-pdf-viewer/highlight';
import { useReaderStore } from '@/stores/useReaderStore';
import { THEMES } from '@/lib/utils';

import '@react-pdf-viewer/core/lib/styles/index.css';
import '@react-pdf-viewer/page-navigation/lib/styles/index.css';
import '@react-pdf-viewer/highlight/lib/styles/index.css';

interface PDFViewerProps {
  fileData: ArrayBuffer;
  documentId: string;
}

// Use a pinned CDN URL for the worker that matches pdfjs-dist 3.11.174
const WORKER_URL = `https://unpkg.com/pdfjs-dist@3.11.174/build/pdf.worker.min.js`;

export default function PDFViewer({ fileData, documentId }: PDFViewerProps) {
  const { currentPage, zoomLevel, activeTheme, setPage, setDocument } = useReaderStore();
  const theme = THEMES[activeTheme];

  // ── Uint8Array Data ───────────────────────────────────────────────────────
  // We pass a Uint8Array directly to react-pdf-viewer instead of a blob URL.
  // This avoids XMLHttpRequest "Unexpected server response (0)" errors in the worker.
  const pdfData = useMemo(() => new Uint8Array(fileData), [fileData]);

  // ── Page navigation plugin ────────────────────────────────────────────────
  const pageNavigationPluginInstance = pageNavigationPlugin();
  const { jumpToPage } = pageNavigationPluginInstance;

  // ── Zoom plugin ───────────────────────────────────────────────────────────
  const zoomPluginInstance = zoomPlugin();
  const { zoomTo } = zoomPluginInstance;

  // ── Highlight plugin ──────────────────────────────────────────────────────
  const highlightPluginInstance = highlightPlugin();

  useEffect(() => {
    if (zoomTo) {
      zoomTo(zoomLevel);
    }
  }, [zoomLevel, zoomTo]);

  // Restore page position on first load
  const didRestorePage = useRef(false);
  useEffect(() => {
    if (!didRestorePage.current && currentPage > 1 && jumpToPage) {
      didRestorePage.current = true;
      setTimeout(() => jumpToPage(currentPage - 1), 500);
    }
  }, [jumpToPage, currentPage]);

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

  const isDarkTheme = activeTheme === 'dark-abyss' || activeTheme === 'focus-punch';

  return (
    <div
      className="flex-1 overflow-hidden relative transition-colors duration-500"
      style={{ background: theme.bg }}
      data-reader-theme={activeTheme}
    >
      {/* Theme overlay for focus punch etc. */}
      {theme.overlay && (
        <div
          className="absolute inset-0 pointer-events-none z-10"
          style={{ background: theme.overlay }}
        />
      )}

      <div
        className="h-full overflow-auto transition-all duration-300"
        style={{
          filter: isDarkTheme
            ? 'invert(100%) hue-rotate(180deg) brightness(85%) contrast(85%)'
            : activeTheme === 'monochrome'
            ? 'grayscale(0.5)'
            : 'none',
          mixBlendMode: isDarkTheme ? 'normal' : 'multiply',
        }}
      >
        <Worker workerUrl={WORKER_URL}>
          <Viewer
            fileUrl={pdfData}
            defaultScale={SpecialZoomLevel.PageFit}
            plugins={[pageNavigationPluginInstance, zoomPluginInstance, highlightPluginInstance]}
            onPageChange={handlePageChange}
            onDocumentLoad={handleDocumentLoad}
            theme={isDarkTheme ? 'dark' : 'light'}
          />
        </Worker>
      </div>
    </div>
  );
}
