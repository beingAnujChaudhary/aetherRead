'use client';

import { useState } from 'react';
import { useAnnotationStore } from '@/stores/useAnnotationStore';
import { ANNOTATION_META } from '@/lib/utils';
import type { AnnotationCategory, AnnotationType } from '@/lib/db';
import { X, Plus, Save } from 'lucide-react';

interface AnnotationFormProps {
  documentId: string;
  currentPage: number;
}

const CATEGORIES: AnnotationCategory[] = ['important', 'definition', 'question', 'revision', 'quote'];

const ANNOTATION_TYPE_META: Record<AnnotationType, { label: string; icon: string; color: string }> = {
  highlight:     { label: 'Highlight',     icon: '🟡', color: '#fbbf24' },
  underline:     { label: 'Underline',     icon: '🔵', color: '#60a5fa' },
  strikethrough: { label: 'Strikethrough', icon: '🔴', color: '#f87171' },
};

export default function AnnotationForm({ documentId, currentPage }: AnnotationFormProps) {
  const { isFormOpen, formPage, activeAnnotation, pendingHighlight, createAnnotation, editAnnotation, closeForm } = useAnnotationStore();
  const [note, setNote] = useState(activeAnnotation?.note ?? '');
  const [category, setCategory] = useState<AnnotationCategory>(activeAnnotation?.category ?? 'important');
  const [isSaving, setIsSaving] = useState(false);

  const displayQuote = activeAnnotation?.quote || pendingHighlight?.quote;
  const annotationType: AnnotationType | undefined = pendingHighlight?.annotationType || activeAnnotation?.annotationType;
  const typeMeta = annotationType ? ANNOTATION_TYPE_META[annotationType] : null;

  if (!isFormOpen) return null;

  const handleSave = async () => {
    // Allow saving with just a quote/highlight (no note text required)
    if (!note.trim() && !displayQuote) return;
    setIsSaving(true);
    try {
      if (activeAnnotation) {
        await editAnnotation(activeAnnotation.id, note, category);
      } else {
        await createAnnotation(
          documentId,
          formPage,
          category,
          note,
          pendingHighlight?.quote,
          pendingHighlight?.highlightAreas,
          pendingHighlight?.annotationType,
        );
      }
    } finally {
      setIsSaving(false);
    }
  };

  const headerTitle = activeAnnotation
    ? 'Edit Note'
    : pendingHighlight
    ? `${ANNOTATION_TYPE_META[pendingHighlight.annotationType].label} · Page ${formPage}`
    : `Add Note · Page ${formPage}`;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-6 bg-black/60 backdrop-blur-sm animate-fade-in">
      <div className="glass rounded-2xl border border-[var(--color-border)] w-full max-w-md shadow-2xl animate-scale-in flex flex-col max-h-[90vh]">
        {/* Header */}
        <div className="flex items-center justify-between p-5 border-b border-[var(--color-border)] flex-shrink-0">
          <div className="flex items-center gap-2">
            {typeMeta ? (
              <span
                className="w-3.5 h-3.5 rounded-sm flex-shrink-0"
                style={{ background: typeMeta.color }}
              />
            ) : (
              <Plus size={16} className="text-aether-400" />
            )}
            <h2 className="font-bold text-white text-sm">{headerTitle}</h2>
          </div>
          <button
            id="annotation-form-close"
            onClick={closeForm}
            className="p-1.5 rounded-lg text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 transition-colors"
          >
            <X size={15} />
          </button>
        </div>

        <div className="p-5 space-y-4 overflow-y-auto">
          {/* Category selector */}
          <div>
            <label className="text-xs text-[var(--color-text-muted)] uppercase tracking-wider mb-3 block">
              Category
            </label>
            <div className="flex flex-wrap gap-2">
              {CATEGORIES.map(cat => {
                const meta = ANNOTATION_META[cat];
                const isActive = cat === category;
                return (
                  <button
                    key={cat}
                    id={`annotation-cat-${cat}`}
                    onClick={() => setCategory(cat)}
                    className={`flex items-center gap-1.5 px-3 py-1.5 rounded-full text-xs font-medium border transition-all ${
                      isActive
                        ? `${meta.bg} ${meta.color} ${meta.border}`
                        : 'bg-white/3 text-[var(--color-text-muted)] border-white/10 hover:border-white/20'
                    }`}
                  >
                    {meta.emoji} {meta.label}
                  </button>
                );
              })}
            </div>
          </div>

          {/* Highlighted / Underlined / Strikethrough quote preview */}
          {displayQuote && (
            <div className="rounded-lg overflow-hidden">
              {/* Annotation type badge */}
              {typeMeta && (
                <div
                  className="flex items-center gap-1.5 px-3 py-1 text-xs font-semibold"
                  style={{ background: typeMeta.color + '25', color: typeMeta.color }}
                >
                  <span
                    className="w-2.5 h-2.5 rounded-sm"
                    style={{ background: typeMeta.color }}
                  />
                  {typeMeta.label}
                </div>
              )}
              {/* Quote text with appropriate visual style */}
              <div
                className="p-3"
                style={{ background: typeMeta ? typeMeta.color + '12' : 'rgba(139, 92, 246, 0.1)' }}
              >
                <p
                  className="text-sm italic line-clamp-4"
                  style={{
                    color: 'var(--color-text)',
                    ...(annotationType === 'underline'
                      ? { textDecoration: 'underline', textDecorationColor: '#60a5fa', textUnderlineOffset: '3px' }
                      : annotationType === 'strikethrough'
                      ? { textDecoration: 'line-through', textDecorationColor: '#f87171' }
                      : {}),
                  }}
                >
                  "{displayQuote}"
                </p>
              </div>
            </div>
          )}

          {/* Note textarea */}
          <div>
            <label className="text-xs text-[var(--color-text-muted)] uppercase tracking-wider mb-2 block">
              Note (Markdown supported)
            </label>
            <textarea
              id="annotation-note-input"
              value={note}
              onChange={e => setNote(e.target.value)}
              placeholder={`Add your ${ANNOTATION_META[category].label.toLowerCase()} note here... (optional)`}
              className="w-full h-32 px-4 py-3 rounded-xl glass-light border border-[var(--color-border)] text-sm text-white placeholder:text-[var(--color-text-muted)] focus:border-aether-500/50 outline-none resize-none transition-colors font-mono"
            />
          </div>

          {/* Actions */}
          <div className="flex items-center justify-end gap-3">
            <button
              id="annotation-form-cancel"
              onClick={closeForm}
              className="px-4 py-2 rounded-lg text-sm text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 transition-colors"
            >
              Cancel
            </button>
            <button
              id="annotation-form-save"
              onClick={handleSave}
              disabled={(!note.trim() && !displayQuote) || isSaving}
              className="flex items-center gap-2 px-4 py-2 rounded-lg text-sm font-semibold bg-aether-500 text-white hover:bg-aether-400 disabled:opacity-40 disabled:cursor-not-allowed transition-all shadow-lg shadow-aether-500/25"
            >
              <Save size={13} />
              {isSaving ? 'Saving...' : 'Save'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
