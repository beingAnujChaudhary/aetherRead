'use client';

import { useEffect, useState } from 'react';
import { useLibraryStore } from '@/stores/useLibraryStore';
import { db } from '@/lib/db';
import DocumentCard from '@/components/library/DocumentCard';
import UploadZone from '@/components/library/UploadZone';
import ReadingStats from '@/components/library/ReadingStats';
import Link from 'next/link';
import {
  BookOpen, Search, SlidersHorizontal, Grid3X3, List,
  WifiOff, Loader2, ExternalLink,
} from 'lucide-react';

type ViewMode = 'grid' | 'list';

export default function LibraryPage() {
  const { loadDocuments, setSortOrder, setSearchQuery, sortOrder, searchQuery, getFiltered, isLoading } = useLibraryStore();
  const [viewMode, setViewMode] = useState<ViewMode>('grid');
  const [totalPagesRead, setTotalPagesRead] = useState(0);

  useEffect(() => {
    loadDocuments();
  }, [loadDocuments]);

  useEffect(() => {
    db.documents.toArray().then(docs => {
      const total = docs.reduce((acc, d) => acc + (d.totalPagesRead ?? 0), 0);
      setTotalPagesRead(total);
    });
  }, []);

  const filtered = getFiltered();

  const handleDelete = async (id: string) => {
    await db.documents.delete(id);
    await db.readingStates.delete(id);
    await db.annotations.where('documentId').equals(id).delete();
    await loadDocuments();
  };

  return (
    <div className="min-h-screen" style={{ background: 'var(--color-bg)' }}>
      {/* Header */}
      <header className="glass border-b border-[var(--color-border)] sticky top-0 z-40">
        <div className="max-w-7xl mx-auto px-6 h-16 flex items-center justify-between">
          <div className="flex items-center gap-4">
            <a
              href="https://beinganujchaudhary.web.app/projects/AetherRead.html"
              target="_blank"
              rel="noopener noreferrer"
              className="flex items-center gap-1.5 text-xs text-[var(--color-text-muted)] hover:text-white transition-colors px-2.5 py-1.5 rounded-lg hover:bg-white/5"
            >
              <ExternalLink size={11} />
              Portfolio
            </a>
            <div className="flex items-center gap-2.5">
              <div className="w-8 h-8 rounded-lg bg-aether-500 flex items-center justify-center shadow-lg shadow-aether-500/30">
                <BookOpen size={16} className="text-white" />
              </div>
              <span className="font-bold text-lg">
                <span className="gradient-text">Aether</span>
                <span className="text-white">Read</span>
              </span>
            </div>
          </div>

          <div className="flex items-center gap-3">
            <a
              href="https://beinganujchaudhary.web.app"
              target="_blank"
              rel="noopener noreferrer"
              className="hidden sm:flex items-center gap-2 text-xs text-[var(--color-text-muted)] hover:text-aether-400 transition-colors"
            >
              <span className="w-6 h-6 rounded-full bg-aether-500/20 border border-aether-500/40 flex items-center justify-center text-[10px] font-bold text-aether-400">A</span>
              <span className="flex flex-col leading-none gap-0.5">
                <span className="text-white font-medium">Anuj Chaudhary</span>
                <span className="text-[10px] text-[var(--color-text-muted)]">IIT Madras · Data Science</span>
              </span>
            </a>
            <div className="flex items-center gap-1.5 px-3 py-1.5 rounded-full bg-green-500/10 border border-green-500/20 text-xs text-green-400">
              <WifiOff size={10} />
              Offline Ready
            </div>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-6 py-10">
        {/* Page title */}
        <div className="mb-8">
          <h1 className="text-3xl font-black text-white mb-2">Your Library</h1>
          <p className="text-[var(--color-text-muted)]">
            All your PDFs stored securely in your browser — no internet needed.
          </p>
        </div>

        {/* Stats */}
        <ReadingStats
          totalDocuments={filtered.length}
          totalPagesRead={totalPagesRead}
          streakDays={1}
        />

        {/* Upload zone */}
        <UploadZone />

        {/* Toolbar */}
        <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 mb-6">
          {/* Search */}
          <div className="relative w-full sm:w-64">
            <Search size={14} className="absolute left-3 top-1/2 -translate-y-1/2 text-[var(--color-text-muted)]" />
            <input
              id="library-search"
              type="text"
              placeholder="Search documents..."
              value={searchQuery}
              onChange={e => setSearchQuery(e.target.value)}
              className="w-full pl-9 pr-4 py-2.5 rounded-xl glass-light border border-[var(--color-border)] text-sm text-white placeholder:text-[var(--color-text-muted)] focus:border-aether-500/50 outline-none transition-colors"
            />
          </div>

          <div className="flex items-center gap-2">
            {/* Sort */}
            <div className="flex items-center gap-1.5 px-3 py-2 rounded-xl glass-light border border-[var(--color-border)]">
              <SlidersHorizontal size={13} className="text-[var(--color-text-muted)]" />
              <select
                id="library-sort"
                value={sortOrder}
                onChange={e => setSortOrder(e.target.value as 'recent' | 'title' | 'progress')}
                className="bg-transparent text-sm text-[var(--color-text-muted)] focus:outline-none cursor-pointer"
              >
                <option value="recent">Recent</option>
                <option value="title">Title</option>
                <option value="progress">Progress</option>
              </select>
            </div>

            {/* View toggle */}
            <div className="flex items-center rounded-xl glass-light border border-[var(--color-border)] overflow-hidden">
              <button
                id="view-grid"
                onClick={() => setViewMode('grid')}
                className={`p-2.5 transition-colors ${viewMode === 'grid' ? 'bg-aether-500/20 text-aether-400' : 'text-[var(--color-text-muted)] hover:text-white'}`}
              >
                <Grid3X3 size={14} />
              </button>
              <button
                id="view-list"
                onClick={() => setViewMode('list')}
                className={`p-2.5 transition-colors ${viewMode === 'list' ? 'bg-aether-500/20 text-aether-400' : 'text-[var(--color-text-muted)] hover:text-white'}`}
              >
                <List size={14} />
              </button>
            </div>
          </div>
        </div>

        {/* Document grid / list */}
        {isLoading ? (
          <div className="flex items-center justify-center py-24">
            <Loader2 size={32} className="text-aether-400 animate-spin" />
          </div>
        ) : filtered.length === 0 ? (
          <div className="flex flex-col items-center justify-center py-24 text-center">
            <div className="w-20 h-20 rounded-2xl bg-aether-500/10 flex items-center justify-center mb-6">
              <BookOpen size={36} className="text-aether-400" />
            </div>
            <h3 className="text-xl font-bold text-white mb-2">
              {searchQuery ? 'No matching documents' : 'Your library is empty'}
            </h3>
            <p className="text-[var(--color-text-muted)] text-sm max-w-xs">
              {searchQuery
                ? `No documents match "${searchQuery}". Try a different search.`
                : 'Upload your first PDF to get started. Everything stays offline in your browser.'}
            </p>
          </div>
        ) : (
          <div
            className={
              viewMode === 'grid'
                ? 'grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-5'
                : 'flex flex-col gap-3'
            }
          >
            {filtered.map(doc => (
              <DocumentCard key={doc.id} doc={doc} onDelete={handleDelete} />
            ))}
          </div>
        )}
      </main>
    </div>
  );
}
