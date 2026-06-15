import { create } from 'zustand';
import type { Annotation, AnnotationCategory, AnnotationType } from '@/lib/db';
import {
  getAnnotationsForDocument,
  addAnnotation,
  updateAnnotation,
  softDeleteAnnotation,
} from '@/lib/db';

interface AnnotationState {
  annotations: Annotation[];
  isLoading: boolean;
  activeAnnotation: Annotation | null;
  isFormOpen: boolean;
  formPage: number;
  pendingHighlight: { quote: string; highlightAreas: any[]; annotationType: AnnotationType } | null;

  // Actions
  loadAnnotations: (documentId: string) => Promise<void>;
  createAnnotation: (
    documentId: string,
    page: number,
    category: AnnotationCategory,
    note: string,
    quote?: string,
    highlightAreas?: any[],
    annotationType?: AnnotationType,
  ) => Promise<void>;
  editAnnotation: (id: string, note: string, category: AnnotationCategory) => Promise<void>;
  deleteAnnotation: (id: string) => Promise<void>;
  openForm: (page: number) => void;
  openFormWithHighlight: (page: number, highlight: { quote: string; highlightAreas: any[]; annotationType: AnnotationType }) => void;
  closeForm: () => void;
  setActiveAnnotation: (annotation: Annotation | null) => void;
  getForPage: (page: number) => Annotation[];
}

export const useAnnotationStore = create<AnnotationState>()((set, get) => ({
  annotations: [],
  isLoading: false,
  activeAnnotation: null,
  isFormOpen: false,
  formPage: 1,
  pendingHighlight: null,

  loadAnnotations: async (documentId) => {
    set({ isLoading: true });
    try {
      const annotations = await getAnnotationsForDocument(documentId);
      set({ annotations });
    } finally {
      set({ isLoading: false });
    }
  },

  createAnnotation: async (documentId, page, category, note, quote, highlightAreas, annotationType) => {
    const annotation = await addAnnotation(documentId, page, category, note, quote, highlightAreas, annotationType);
    set(s => ({ annotations: [...s.annotations, annotation], isFormOpen: false }));
  },

  editAnnotation: async (id, note, category) => {
    await updateAnnotation(id, note, category);
    set(s => ({
      annotations: s.annotations.map(a =>
        a.id === id ? { ...a, note, category, updatedAt: new Date().toISOString() } : a,
      ),
      isFormOpen: false,
      activeAnnotation: null,
    }));
  },

  deleteAnnotation: async (id) => {
    await softDeleteAnnotation(id);
    set(s => ({ annotations: s.annotations.filter(a => a.id !== id) }));
  },

  openForm: (page) => set({ isFormOpen: true, formPage: page, activeAnnotation: null, pendingHighlight: null }),
  openFormWithHighlight: (page, highlight) => set({ isFormOpen: true, formPage: page, activeAnnotation: null, pendingHighlight: highlight }),
  closeForm: () => set({ isFormOpen: false, activeAnnotation: null, pendingHighlight: null }),
  setActiveAnnotation: (annotation) => set({ activeAnnotation: annotation }),

  getForPage: (page) => get().annotations.filter(a => a.pageNumber === page),
}));
