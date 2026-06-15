import { create } from 'zustand';
import type { Document } from '@/lib/db';
import { getAllDocuments, addDocument } from '@/lib/db';

type SortOrder = 'recent' | 'title' | 'progress';

interface LibraryState {
  documents: Document[];
  isLoading: boolean;
  sortOrder: SortOrder;
  searchQuery: string;
  isUploading: boolean;
  uploadProgress: number;

  // Actions
  loadDocuments: () => Promise<void>;
  uploadDocument: (file: File) => Promise<Document | null>;
  setSortOrder: (order: SortOrder) => void;
  setSearchQuery: (q: string) => void;
  getFiltered: () => Document[];
}

export const useLibraryStore = create<LibraryState>()((set, get) => ({
  documents: [],
  isLoading: false,
  sortOrder: 'recent',
  searchQuery: '',
  isUploading: false,
  uploadProgress: 0,

  loadDocuments: async () => {
    set({ isLoading: true });
    try {
      const docs = await getAllDocuments();
      set({ documents: docs });
    } finally {
      set({ isLoading: false });
    }
  },

  uploadDocument: async (file: File) => {
    if (!file.name.toLowerCase().endsWith('.pdf')) return null;
    set({ isUploading: true, uploadProgress: 0 });
    try {
      // Simulate progress for UX feel
      set({ uploadProgress: 30 });
      const doc = await addDocument(file, 0);
      set({ uploadProgress: 100 });
      await get().loadDocuments();
      return doc;
    } catch (e) {
      console.error('Upload failed', e);
      return null;
    } finally {
      setTimeout(() => set({ isUploading: false, uploadProgress: 0 }), 800);
    }
  },

  setSortOrder: (sortOrder) => set({ sortOrder }),
  setSearchQuery: (searchQuery) => set({ searchQuery }),

  getFiltered: () => {
    const { documents, sortOrder, searchQuery } = get();
    let filtered = documents;

    if (searchQuery.trim()) {
      const q = searchQuery.toLowerCase();
      filtered = filtered.filter(d =>
        d.title.toLowerCase().includes(q) ||
        d.author?.toLowerCase().includes(q),
      );
    }

    switch (sortOrder) {
      case 'title':
        return [...filtered].sort((a, b) => a.title.localeCompare(b.title));
      case 'progress':
        return [...filtered].sort((a, b) =>
          ((b.totalPagesRead ?? 0) / (b.pageCount || 1)) -
          ((a.totalPagesRead ?? 0) / (a.pageCount || 1)),
        );
      default: // 'recent'
        return filtered;
    }
  },
}));
