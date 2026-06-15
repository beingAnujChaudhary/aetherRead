import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import type { ComfortTheme } from '@/lib/db';
import { saveReadingState } from '@/lib/db';

interface ReaderState {
  // Current document
  documentId: string | null;
  currentPage: number;
  pageCount: number;

  // Comfort Engine
  activeTheme: ComfortTheme;

  // UI
  zoomLevel: number;
  isFullscreen: boolean;
  showAnnotations: boolean;
  showAIPanel: boolean;

  // Session tracking
  sessionStartTime: number | null;
  sessionPagesRead: number;

  // Actions
  setDocument: (id: string, pageCount: number) => void;
  setPage: (page: number) => void;
  setTheme: (theme: ComfortTheme) => void;
  setZoom: (zoom: number) => void;
  toggleFullscreen: () => void;
  toggleAnnotations: () => void;
  toggleAIPanel: () => void;
  startSession: () => void;
  endSession: () => void;
}

export const useReaderStore = create<ReaderState>()(
  persist(
    (set, get) => ({
      documentId: null,
      currentPage: 1,
      pageCount: 0,
      activeTheme: 'dark-abyss',
      zoomLevel: 1,
      isFullscreen: false,
      showAnnotations: false,
      showAIPanel: false,
      sessionStartTime: null,
      sessionPagesRead: 0,

      setDocument: (id, pageCount) => {
        set({ documentId: id, pageCount, currentPage: 1, sessionPagesRead: 0 });
      },

      setPage: (page) => {
        const { documentId } = get();
        set(s => ({ currentPage: page, sessionPagesRead: s.sessionPagesRead + 1 }));
        if (documentId) {
          saveReadingState(documentId, { currentPage: page }).catch(console.error);
        }
      },

      setTheme: (theme) => {
        const { documentId } = get();
        set({ activeTheme: theme });
        if (documentId) {
          saveReadingState(documentId, { activeTheme: theme }).catch(console.error);
        }
      },

      setZoom: (zoom) => set({ zoomLevel: Math.max(0.5, Math.min(3, zoom)) }),

      toggleFullscreen: () => set(s => ({ isFullscreen: !s.isFullscreen })),
      toggleAnnotations: () => set(s => ({ showAnnotations: !s.showAnnotations })),
      toggleAIPanel: () => set(s => ({ showAIPanel: !s.showAIPanel })),

      startSession: () => set({ sessionStartTime: Date.now(), sessionPagesRead: 0 }),
      endSession: () => set({ sessionStartTime: null }),
    }),
    {
      name: 'aetherread-reader',
      partialize: (s) => ({ activeTheme: s.activeTheme, zoomLevel: s.zoomLevel }),
    },
  ),
);
