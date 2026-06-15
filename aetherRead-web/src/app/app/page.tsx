'use client';

import { useEffect, useState } from 'react';
import { useLibraryStore } from '@/stores/useLibraryStore';
import { db } from '@/lib/db';
import DocumentCard from '@/components/library/DocumentCard';
import UploadZone from '@/components/library/UploadZone';
import ReadingStats from '@/components/library/ReadingStats';
import AuthModal from '@/components/auth/AuthModal';
import { useAuth } from '@/contexts/AuthContext';
import Link from 'next/link';
import {
  BookOpen, Search, SlidersHorizontal, Grid3X3, List,
  WifiOff, Loader2, ExternalLink, LogOut, LogIn, UserCircle2,
} from 'lucide-react';

type ViewMode = 'grid' | 'list';

export default function LibraryPage() {
  const { loadDocuments, setSortOrder, setSearchQuery, sortOrder, searchQuery, getFiltered, isLoading } = useLibraryStore();
  const [viewMode, setViewMode] = useState<ViewMode>('grid');
  const [totalPagesRead, setTotalPagesRead] = useState(0);
  const [showAuth, setShowAuth] = useState(false);
  const [showUserMenu, setShowUserMenu] = useState(false);
  const { user, logout } = useAuth();

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
              href="https://beinganujchaudhary.web.app/projects/aetherRead.html"
              target="_blank"
              rel="noopener noreferrer"
              className="flex items-center gap-1.5 text-xs text-[var(--color-text-muted)] hover:text-white transition-colors px-2.5 py-1.5 rounded-lg hover:bg-white/5"
            >
              <ExternalLink size={11} />
              Portfolio
            </a>
            <Link
              href="/"
              className="flex items-center gap-2.5 hover:opacity-80 transition-opacity"
            >
              <div className="w-8 h-8 rounded-lg bg-aether-500 flex items-center justify-center shadow-lg shadow-aether-500/30">
                <BookOpen size={16} className="text-white" />
              </div>
              <span className="font-bold text-lg">
                <span className="gradient-text">Aether</span>
                <span className="text-white">Read</span>
              </span>
            </Link>
          </div>

          <div className="flex items-center gap-3">
            {/* Offline badge */}
            <div className="hidden sm:flex items-center gap-1.5 px-3 py-1.5 rounded-full bg-green-500/10 border border-green-500/20 text-xs text-green-400">
              <WifiOff size={10} />
              Offline Ready
            </div>

            {/* Auth section */}
            {user ? (
              <div className="relative">
                <button
                  id="user-menu-btn"
                  onClick={() => setShowUserMenu(v => !v)}
                  className="flex items-center gap-2 px-3 py-1.5 rounded-xl hover:bg-white/5 transition-colors"
                >
                  {user.photoURL ? (
                    <img src={user.photoURL} alt="avatar" className="w-7 h-7 rounded-full border border-aether-500/40" />
                  ) : (
                    <div className="w-7 h-7 rounded-full bg-aether-500/20 border border-aether-500/40 flex items-center justify-center text-xs font-bold text-aether-400">
                      {(user.displayName ?? user.email ?? 'U')[0].toUpperCase()}
                    </div>
                  )}
                  <span className="hidden sm:flex flex-col leading-none gap-0.5 text-left">
                    <span className="text-white text-xs font-medium">{user.displayName ?? 'User'}</span>
                    <span className="text-[10px] text-[var(--color-text-muted)] truncate max-w-[120px]">{user.email}</span>
                  </span>
                </button>
                {showUserMenu && (
                  <div className="absolute right-0 top-full mt-2 w-44 glass border border-[var(--color-border)] rounded-xl shadow-xl overflow-hidden z-50">
                    <button
                      id="signout-btn"
                      onClick={() => { logout(); setShowUserMenu(false); }}
                      className="w-full flex items-center gap-2.5 px-4 py-3 text-sm text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 transition-colors"
                    >
                      <LogOut size={14} />
                      Sign out
                    </button>
                  </div>
                )}
              </div>
            ) : (
              <button
                id="signin-btn"
                onClick={() => setShowAuth(true)}
                className="flex items-center gap-2 px-4 py-2 rounded-xl bg-aether-500 hover:bg-aether-400 text-white text-sm font-semibold transition-colors shadow-md shadow-aether-500/25"
              >
                <LogIn size={14} />
                Sign In
              </button>
            )}
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

      {/* Auth Modal */}
      {showAuth && (
        <AuthModal onClose={() => setShowAuth(false)} />
      )}
      {/* Auto-close modal when user signs in */}
      {user && showAuth && (() => { setShowAuth(false); return null; })()}
    </div>
  );
}
