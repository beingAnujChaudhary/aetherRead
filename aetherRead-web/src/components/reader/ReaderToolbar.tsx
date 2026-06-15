'use client';

import { useState } from 'react';
import { useReaderStore } from '@/stores/useReaderStore';
import { useAnnotationStore } from '@/stores/useAnnotationStore';
import ThemeSelector from './ThemeSelector';
import {
  ArrowLeft, ZoomIn, ZoomOut, ChevronLeft, ChevronRight,
  Palette, StickyNote, Maximize2, Minimize2, Brain,
  BookOpen,
} from 'lucide-react';
import Link from 'next/link';

interface ReaderToolbarProps {
  title: string;
}

export default function ReaderToolbar({ title }: ReaderToolbarProps) {
  const {
    currentPage, pageCount, activeTheme,
    zoomLevel, isFullscreen,
    setPage, setZoom, toggleFullscreen, toggleAnnotations, toggleAIPanel,
    showAnnotations, showAIPanel,
  } = useReaderStore();

  const { annotations } = useAnnotationStore();
  const [showThemes, setShowThemes] = useState(false);
  const [pageInput, setPageInput] = useState(String(currentPage));

  const pageAnnotations = annotations.filter(a => a.pageNumber === currentPage && !a.isDeleted);

  const handlePageJump = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      const n = parseInt(pageInput);
      if (!isNaN(n) && n >= 1 && n <= pageCount) {
        setPage(n);
      }
    }
  };

  return (
    <div className="glass border-b border-[var(--color-border)] h-14 flex items-center justify-between px-4 gap-2 flex-shrink-0 relative z-30">
      {/* Left — back + title */}
      <div className="flex items-center gap-3 min-w-0">
        <Link
          id="reader-back"
          href="/app"
          className="p-2 rounded-lg text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 transition-colors flex-shrink-0"
          title="Back to Library"
        >
          <ArrowLeft size={16} />
        </Link>
        <div className="flex items-center gap-2 min-w-0">
          <BookOpen size={14} className="text-aether-400 flex-shrink-0" />
          <span className="text-sm font-medium text-white truncate max-w-[200px]">{title}</span>
        </div>
        {pageAnnotations.length > 0 && (
          <span className="hidden sm:flex items-center gap-1 px-2 py-0.5 rounded-full bg-aether-500/15 border border-aether-500/30 text-xs text-aether-400">
            📌 {pageAnnotations.length} note{pageAnnotations.length !== 1 ? 's' : ''}
          </span>
        )}
      </div>

      {/* Center — page nav */}
      <div className="flex items-center gap-1 flex-shrink-0">
        <button
          id="reader-prev-page"
          onClick={() => setPage(Math.max(1, currentPage - 1))}
          disabled={currentPage <= 1}
          className="p-2 rounded-lg text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 disabled:opacity-30 disabled:cursor-not-allowed transition-colors"
        >
          <ChevronLeft size={16} />
        </button>
        <div className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-[var(--color-surface-muted)] border border-[var(--color-border)]">
          <input
            id="reader-page-input"
            type="text"
            value={pageInput}
            onChange={e => setPageInput(e.target.value)}
            onKeyDown={handlePageJump}
            onBlur={() => setPageInput(String(currentPage))}
            className="w-10 text-center text-sm text-white bg-transparent outline-none"
          />
          <span className="text-xs text-[var(--color-text-muted)]">/ {pageCount}</span>
        </div>
        <button
          id="reader-next-page"
          onClick={() => setPage(Math.min(pageCount, currentPage + 1))}
          disabled={currentPage >= pageCount}
          className="p-2 rounded-lg text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 disabled:opacity-30 disabled:cursor-not-allowed transition-colors"
        >
          <ChevronRight size={16} />
        </button>
      </div>

      {/* Right — toolbar actions */}
      <div className="flex items-center gap-1 flex-shrink-0">
        {/* Zoom */}
        <button
          id="reader-zoom-out"
          onClick={() => setZoom(zoomLevel - 0.2)}
          className="p-2 rounded-lg text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 transition-colors"
          title="Zoom out"
        >
          <ZoomOut size={15} />
        </button>
        <span className="text-xs text-[var(--color-text-muted)] w-10 text-center">
          {Math.round(zoomLevel * 100)}%
        </span>
        <button
          id="reader-zoom-in"
          onClick={() => setZoom(zoomLevel + 0.2)}
          className="p-2 rounded-lg text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 transition-colors"
          title="Zoom in"
        >
          <ZoomIn size={15} />
        </button>

        <div className="w-px h-5 bg-[var(--color-border)] mx-1" />

        {/* Theme picker */}
        <div className="relative">
          <button
            id="reader-theme-toggle"
            onClick={() => setShowThemes(v => !v)}
            className={`p-2 rounded-lg transition-colors ${showThemes ? 'bg-aether-500/20 text-aether-400' : 'text-[var(--color-text-muted)] hover:text-white hover:bg-white/5'}`}
            title="Reading theme"
          >
            <Palette size={15} />
          </button>
          {showThemes && (
            <>
              <div className="fixed inset-0 z-40" onClick={() => setShowThemes(false)} />
              <div className="relative z-50">
                <ThemeSelector />
              </div>
            </>
          )}
        </div>

        {/* Annotations toggle */}
        <button
          id="reader-annotations-toggle"
          onClick={toggleAnnotations}
          className={`p-2 rounded-lg transition-colors ${showAnnotations ? 'bg-aether-500/20 text-aether-400' : 'text-[var(--color-text-muted)] hover:text-white hover:bg-white/5'}`}
          title="Annotations"
        >
          <StickyNote size={15} />
        </button>

        {/* AI Chat */}
        <button
          id="reader-ai-toggle"
          onClick={toggleAIPanel}
          className={`hidden sm:flex p-2 rounded-lg transition-colors ${showAIPanel ? 'bg-purple-500/20 text-purple-400' : 'text-[var(--color-text-muted)] hover:text-white hover:bg-white/5'}`}
          title="AI Brain (coming soon)"
        >
          <Brain size={15} />
        </button>

        {/* Fullscreen */}
        <button
          id="reader-fullscreen"
          onClick={toggleFullscreen}
          className="p-2 rounded-lg text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 transition-colors"
          title={isFullscreen ? 'Exit fullscreen' : 'Fullscreen'}
        >
          {isFullscreen ? <Minimize2 size={15} /> : <Maximize2 size={15} />}
        </button>
      </div>
    </div>
  );
}
