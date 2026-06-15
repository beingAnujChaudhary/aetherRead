'use client';

import { useAnnotationStore } from '@/stores/useAnnotationStore';
import { useReaderStore } from '@/stores/useReaderStore';
import { ANNOTATION_META, formatDate } from '@/lib/utils';
import type { Annotation, AnnotationType } from '@/lib/db';
import { X, Plus, Edit2, Trash2, StickyNote, ChevronRight } from 'lucide-react';

interface AnnotationPanelProps {
  documentId: string;
}

const ANNOTATION_TYPE_META: Record<AnnotationType, { label: string; color: string }> = {
  highlight:     { label: 'Highlight',     color: '#fbbf24' },
  underline:     { label: 'Underline',     color: '#60a5fa' },
  strikethrough: { label: 'Strikethrough', color: '#f87171' },
};

function AnnotationItem({ annotation, onEdit, onDelete, onJump }: {
  annotation: Annotation;
  onEdit: (a: Annotation) => void;
  onDelete: (id: string) => void;
  onJump: (page: number) => void;
}) {
  const meta = ANNOTATION_META[annotation.category];
  const typeMeta = annotation.annotationType ? ANNOTATION_TYPE_META[annotation.annotationType] : null;

  return (
    <div className={`group rounded-xl p-3 border ${meta.border} ${meta.bg} transition-all`}>
      <div className="flex items-start justify-between gap-2 mb-2">
        <div className="flex items-center gap-1.5 flex-wrap">
          <span className="text-xs font-medium" style={{ color: 'inherit' }}>
            {meta.emoji} {meta.label}
          </span>
          <span className={`text-xs ${meta.color} opacity-75`}>· P.{annotation.pageNumber}</span>
          {typeMeta && (
            <span
              className="flex items-center gap-1 text-xs px-1.5 py-0.5 rounded-full font-medium"
              style={{ background: typeMeta.color + '20', color: typeMeta.color }}
            >
              <span className="w-1.5 h-1.5 rounded-sm" style={{ background: typeMeta.color }} />
              {typeMeta.label}
            </span>
          )}
        </div>
        <div className="flex items-center gap-1 opacity-0 group-hover:opacity-100 transition-opacity flex-shrink-0">
          <button
            id={`annotation-jump-${annotation.id}`}
            onClick={() => onJump(annotation.pageNumber)}
            className="p-1 rounded text-[var(--color-text-muted)] hover:text-white transition-colors"
            title="Jump to page"
          >
            <ChevronRight size={12} />
          </button>
          <button
            id={`annotation-edit-${annotation.id}`}
            onClick={() => onEdit(annotation)}
            className="p-1 rounded text-[var(--color-text-muted)] hover:text-blue-400 transition-colors"
            title="Edit"
          >
            <Edit2 size={12} />
          </button>
          <button
            id={`annotation-delete-${annotation.id}`}
            onClick={() => onDelete(annotation.id)}
            className="p-1 rounded text-[var(--color-text-muted)] hover:text-red-400 transition-colors"
            title="Delete"
          >
            <Trash2 size={12} />
          </button>
        </div>
      </div>
      <p className="text-sm text-[var(--color-text-muted)] leading-relaxed line-clamp-3">
        {annotation.note}
      </p>
      {annotation.quote && (
        <div className="mt-2 pl-2 border-l-2" style={{ borderColor: typeMeta?.color || '#8b5cf6' }}>
          <p
            className="text-xs text-[var(--color-text-muted)] italic line-clamp-2"
            style={{
              ...(annotation.annotationType === 'underline'
                ? { textDecoration: 'underline', textDecorationColor: '#60a5fa', textUnderlineOffset: '2px' }
                : annotation.annotationType === 'strikethrough'
                ? { textDecoration: 'line-through', textDecorationColor: '#f87171' }
                : {}),
            }}
          >
            "{annotation.quote}"
          </p>
        </div>
      )}
      <p className="text-xs text-[var(--color-text-muted)] opacity-50 mt-2">
        {formatDate(annotation.updatedAt)}
      </p>
    </div>
  );
}

export default function AnnotationPanel({ documentId }: AnnotationPanelProps) {
  const { showAnnotations, toggleAnnotations, currentPage, setPage } = useReaderStore();
  const { annotations, openForm, setActiveAnnotation, deleteAnnotation, isFormOpen } = useAnnotationStore();

  if (!showAnnotations) return null;

  const currentPageAnnotations = annotations.filter(a => a.pageNumber === currentPage);
  const otherAnnotations = annotations.filter(a => a.pageNumber !== currentPage);

  const handleEdit = (annotation: Annotation) => {
    setActiveAnnotation(annotation);
    openForm(annotation.pageNumber);
  };

  return (
    <aside className="w-80 flex-shrink-0 glass border-l border-[var(--color-border)] flex flex-col h-full animate-slide-up">
      {/* Panel header */}
      <div className="flex items-center justify-between px-4 py-3 border-b border-[var(--color-border)]">
        <div className="flex items-center gap-2">
          <StickyNote size={14} className="text-aether-400" />
          <span className="text-sm font-semibold text-white">Notes</span>
          {annotations.length > 0 && (
            <span className="px-1.5 py-0.5 rounded-full bg-aether-500/20 text-aether-400 text-xs font-medium">
              {annotations.length}
            </span>
          )}
        </div>
        <div className="flex items-center gap-1">
          <button
            id="annotation-add"
            onClick={() => openForm(currentPage)}
            className="flex items-center gap-1 px-2.5 py-1.5 rounded-lg bg-aether-500/15 hover:bg-aether-500/25 text-aether-400 text-xs font-medium transition-colors"
          >
            <Plus size={11} />
            Add
          </button>
          <button
            id="annotation-panel-close"
            onClick={toggleAnnotations}
            className="p-1.5 rounded-lg text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 transition-colors"
          >
            <X size={13} />
          </button>
        </div>
      </div>

      {/* Annotations list */}
      <div className="flex-1 overflow-y-auto p-3 space-y-4">
        {/* Current page annotations */}
        {currentPageAnnotations.length > 0 && (
          <div>
            <p className="text-xs font-semibold text-[var(--color-text-muted)] uppercase tracking-wider mb-2 px-1">
              Page {currentPage}
            </p>
            <div className="space-y-2">
              {currentPageAnnotations.map(a => (
                <AnnotationItem
                  key={a.id}
                  annotation={a}
                  onEdit={handleEdit}
                  onDelete={deleteAnnotation}
                  onJump={setPage}
                />
              ))}
            </div>
          </div>
        )}

        {/* Other pages */}
        {otherAnnotations.length > 0 && (
          <div>
            <p className="text-xs font-semibold text-[var(--color-text-muted)] uppercase tracking-wider mb-2 px-1">
              Other Pages
            </p>
            <div className="space-y-2">
              {otherAnnotations.map(a => (
                <AnnotationItem
                  key={a.id}
                  annotation={a}
                  onEdit={handleEdit}
                  onDelete={deleteAnnotation}
                  onJump={setPage}
                />
              ))}
            </div>
          </div>
        )}

        {/* Empty state */}
        {annotations.length === 0 && (
          <div className="flex flex-col items-center justify-center py-16 text-center px-4">
            <div className="w-12 h-12 rounded-xl bg-aether-500/10 flex items-center justify-center mb-3">
              <StickyNote size={20} className="text-aether-400" />
            </div>
            <p className="text-sm font-medium text-white mb-1">No notes yet</p>
            <p className="text-xs text-[var(--color-text-muted)]">
              Add notes to any page — categorized, searchable, and stored offline.
            </p>
            <button
              id="annotation-panel-first-add"
              onClick={() => openForm(currentPage)}
              className="mt-4 flex items-center gap-1.5 px-4 py-2 rounded-xl bg-aether-500/15 hover:bg-aether-500/25 text-aether-400 text-sm font-medium transition-colors"
            >
              <Plus size={13} />
              Add first note
            </button>
          </div>
        )}
      </div>
    </aside>
  );
}
