'use client';

import { useEffect, useRef, useMemo, useCallback } from 'react';
import { Worker, Viewer, SpecialZoomLevel } from '@react-pdf-viewer/core';
import { pageNavigationPlugin } from '@react-pdf-viewer/page-navigation';
import { zoomPlugin } from '@react-pdf-viewer/zoom';
import { highlightPlugin, RenderHighlightTargetProps, RenderHighlightsProps } from '@react-pdf-viewer/highlight';
import { useAnnotationStore } from '@/stores/useAnnotationStore';
import { useReaderStore } from '@/stores/useReaderStore';
import { THEMES } from '@/lib/utils';
import type { AnnotationType } from '@/lib/db';

import '@react-pdf-viewer/core/lib/styles/index.css';
import '@react-pdf-viewer/page-navigation/lib/styles/index.css';
import '@react-pdf-viewer/highlight/lib/styles/index.css';

interface PDFViewerProps {
  fileData: ArrayBuffer;
  documentId: string;
}

// Use a pinned CDN URL for the worker that matches pdfjs-dist 3.11.174
const WORKER_URL = `https://unpkg.com/pdfjs-dist@3.11.174/build/pdf.worker.min.js`;

// Visual config for each annotation type
const ANNOTATION_TYPE_CONFIG: Record<AnnotationType, { label: string; icon: string; color: string; bg: string }> = {
  highlight:     { label: 'Highlight',     icon: '🟡', color: 'rgba(251, 191,  36, 0.45)', bg: '#fbbf24' },
  underline:     { label: 'Underline',     icon: '🔵', color: 'rgba( 96, 165, 250, 0.85)', bg: '#60a5fa' },
  strikethrough: { label: 'Strikethrough', icon: '🔴', color: 'rgba(248,  113, 113, 0.85)', bg: '#f87171' },
};

export default function PDFViewer({ fileData, documentId }: PDFViewerProps) {
  const { currentPage, zoomLevel, activeTheme, setPage, setDocument } = useReaderStore();
  const { annotations, openFormWithHighlight } = useAnnotationStore();
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
  const renderHighlightTarget = (props: RenderHighlightTargetProps) => {
    const applyAnnotation = (type: AnnotationType) => {
      props.toggle();
      openFormWithHighlight(currentPage, {
        quote: props.selectedText,
        highlightAreas: props.highlightAreas,
        annotationType: type,
      });
    };

    return (
      <div
        style={{
          position: 'absolute',
          left: `${props.selectionRegion.left}%`,
          top: `${props.selectionRegion.top + props.selectionRegion.height}%`,
          zIndex: 20,
          transform: 'translate(-50%, 8px)',
          pointerEvents: 'all',
        }}
      >
        {/* Annotation toolbar popup */}
        <div
          className="flex items-center gap-1 px-2 py-1.5 rounded-xl shadow-2xl border border-white/10"
          style={{ background: 'rgba(15, 17, 28, 0.95)', backdropFilter: 'blur(12px)' }}
        >
          {(Object.entries(ANNOTATION_TYPE_CONFIG) as [AnnotationType, typeof ANNOTATION_TYPE_CONFIG[AnnotationType]][]).map(([type, cfg]) => (
            <button
              key={type}
              id={`annotation-tool-${type}`}
              title={cfg.label}
              onClick={() => applyAnnotation(type)}
              className="flex items-center gap-1.5 px-2.5 py-1 rounded-lg text-xs font-semibold text-white hover:bg-white/10 transition-all"
              style={{ '--accent': cfg.bg } as React.CSSProperties}
            >
              <span
                className="w-3 h-3 rounded-sm flex-shrink-0"
                style={{ background: cfg.bg }}
              />
              {cfg.label}
            </button>
          ))}
          {/* Divider + Note-only button */}
          <div className="w-px h-4 bg-white/10 mx-0.5" />
          <button
            id="annotation-tool-note"
            title="Add Note"
            onClick={() => applyAnnotation('highlight')}
            className="flex items-center gap-1.5 px-2.5 py-1 rounded-lg text-xs font-semibold text-[var(--color-text-muted)] hover:text-white hover:bg-white/10 transition-all"
          >
            📝 Note
          </button>
        </div>
      </div>
    );
  };

  const renderHighlights = (props: RenderHighlightsProps) => {
    const pageAnnotations = annotations.filter(
      (a) => a.documentId === documentId && a.pageNumber - 1 === props.pageIndex && a.highlightAreas?.length,
    );

    return (
      <div>
        {pageAnnotations.flatMap((annotation) =>
          (annotation.highlightAreas || []).map((area, idx) => {
            const type: AnnotationType = annotation.annotationType || 'highlight';
            const cssProps = props.getCssProperties(area, props.rotation);

            if (type === 'highlight') {
              return (
                <div
                  key={`${annotation.id}-${idx}`}
                  style={{
                    ...cssProps,
                    background: ANNOTATION_TYPE_CONFIG.highlight.color,
                    mixBlendMode: 'multiply',
                    borderRadius: '2px',
                  }}
                />
              );
            }

            if (type === 'underline') {
              return (
                <div
                  key={`${annotation.id}-${idx}`}
                  style={{
                    ...cssProps,
                    background: 'transparent',
                    borderBottom: `2px solid ${ANNOTATION_TYPE_CONFIG.underline.color}`,
                    boxSizing: 'border-box',
                  }}
                />
              );
            }

            if (type === 'strikethrough') {
              return (
                <div
                  key={`${annotation.id}-${idx}`}
                  style={{
                    ...cssProps,
                    position: 'absolute',
                    background: 'transparent',
                  }}
                >
                  {/* Strike line drawn at vertical center */}
                  <div
                    style={{
                      position: 'absolute',
                      left: 0,
                      right: 0,
                      top: '50%',
                      height: '2px',
                      background: ANNOTATION_TYPE_CONFIG.strikethrough.color,
                      transform: 'translateY(-50%)',
                    }}
                  />
                </div>
              );
            }

            return null;
          })
        )}
      </div>
    );
  };

  const highlightPluginInstance = highlightPlugin({
    renderHighlightTarget,
    renderHighlights,
  });

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
