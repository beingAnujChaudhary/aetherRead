'use client';

import { formatDate, formatFileSize, readingProgress, THEMES } from '@/lib/utils';
import type { Document } from '@/lib/db';
import { BookOpen, Clock, FileText, Trash2, ChevronRight } from 'lucide-react';
import Link from 'next/link';

interface DocumentCardProps {
  doc: Document;
  onDelete?: (id: string) => void;
}

export default function DocumentCard({ doc, onDelete }: DocumentCardProps) {
  const progress = readingProgress(doc.totalPagesRead ?? 0, doc.pageCount);
  const themeName = THEMES['dark-abyss'].label; // default display

  return (
    <div className="group relative glass rounded-2xl border border-white/5 hover:border-aether-500/30 transition-all duration-300 hover:-translate-y-1 hover:shadow-xl hover:shadow-aether-500/10 overflow-hidden">
      {/* Color accent bar */}
      <div className="h-1 w-full bg-gradient-to-r from-aether-500 via-purple-500 to-pink-500 opacity-0 group-hover:opacity-100 transition-opacity duration-300" />

      <div className="p-5">
        {/* Header */}
        <div className="flex items-start justify-between gap-3 mb-4">
          <div className="w-10 h-10 rounded-xl bg-aether-500/10 border border-aether-500/20 flex items-center justify-center flex-shrink-0">
            <BookOpen size={18} className="text-aether-400" />
          </div>
          <button
            id={`delete-doc-${doc.id}`}
            onClick={() => onDelete?.(doc.id)}
            className="opacity-0 group-hover:opacity-100 p-1.5 rounded-lg text-[var(--color-text-muted)] hover:text-red-400 hover:bg-red-500/10 transition-all"
            aria-label="Delete document"
          >
            <Trash2 size={14} />
          </button>
        </div>

        {/* Title */}
        <h3 className="font-semibold text-white text-sm leading-snug mb-1 line-clamp-2">
          {doc.title}
        </h3>
        {doc.author && (
          <p className="text-xs text-[var(--color-text-muted)] mb-3">{doc.author}</p>
        )}

        {/* Meta */}
        <div className="flex items-center gap-3 mb-4 text-xs text-[var(--color-text-muted)]">
          <span className="flex items-center gap-1">
            <FileText size={11} />
            {doc.pageCount > 0 ? `${doc.pageCount} pages` : 'Unknown pages'}
          </span>
          <span className="flex items-center gap-1">
            <Clock size={11} />
            {doc.lastOpenedAt ? formatDate(doc.lastOpenedAt) : 'Never opened'}
          </span>
        </div>

        {/* Progress bar */}
        <div className="mb-4">
          <div className="flex justify-between text-xs text-[var(--color-text-muted)] mb-1.5">
            <span>Progress</span>
            <span className="text-aether-400 font-medium">{progress}%</span>
          </div>
          <div className="h-1.5 rounded-full bg-[var(--color-surface-muted)] overflow-hidden">
            <div
              className="h-full rounded-full bg-gradient-to-r from-aether-500 to-purple-500 transition-all duration-500"
              style={{ width: `${progress}%` }}
            />
          </div>
        </div>

        {/* Footer */}
        <div className="flex items-center justify-between">
          <span className="text-xs text-[var(--color-text-muted)]">
            {formatFileSize(doc.fileSize)}
          </span>
          <Link
            id={`open-doc-${doc.id}`}
            href={`/app/reader/${doc.id}`}
            className="flex items-center gap-1 px-3 py-1.5 rounded-lg bg-aether-500 text-white text-xs font-semibold hover:bg-aether-400 transition-colors shadow-md shadow-aether-500/25"
          >
            Read
            <ChevronRight size={12} />
          </Link>
        </div>
      </div>
    </div>
  );
}
