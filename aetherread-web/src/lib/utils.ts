import { type ClassValue, clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';
import type { ComfortTheme } from './db';

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function formatFileSize(bytes: number): string {
  if (bytes < 1024) return `${bytes} B`;
  if (bytes < 1024 * 1024) return `${(bytes / 1024).toFixed(1)} KB`;
  return `${(bytes / (1024 * 1024)).toFixed(1)} MB`;
}

export function formatDate(iso: string): string {
  const d = new Date(iso);
  const now = new Date();
  const diff = now.getTime() - d.getTime();
  const mins = Math.floor(diff / 60000);
  const hours = Math.floor(diff / 3600000);
  const days = Math.floor(diff / 86400000);

  if (mins < 1) return 'just now';
  if (mins < 60) return `${mins}m ago`;
  if (hours < 24) return `${hours}h ago`;
  if (days < 7) return `${days}d ago`;
  return d.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
}

export function readingProgress(currentPage: number, pageCount: number): number {
  if (pageCount <= 0) return 0;
  return Math.round((currentPage / pageCount) * 100);
}

export function estimateReadingTime(pagesLeft: number, avgWpm = 250): string {
  // Average page ~300 words
  const words = pagesLeft * 300;
  const minutes = Math.round(words / avgWpm);
  if (minutes < 60) return `${minutes} min left`;
  const hours = Math.floor(minutes / 60);
  const mins = minutes % 60;
  return `${hours}h ${mins}m left`;
}

export const THEMES: Record<ComfortTheme, {
  label: string;
  emoji: string;
  description: string;
  bg: string;
  text: string;
  accent: string;
  overlay?: string;
}> = {
  'dark-abyss': {
    label: 'Dark Abyss',
    emoji: '🌑',
    description: 'High-contrast dark mode for night reading',
    bg: '#0D0D0D',
    text: '#E8E8E8',
    accent: '#6C63FF',
  },
  'book-paper': {
    label: 'Book Paper',
    emoji: '📄',
    description: 'Warm paper background for standard reading',
    bg: '#F5F0E8',
    text: '#2C2416',
    accent: '#8B6F47',
  },
  'sepia-sands': {
    label: 'Sepia Sands',
    emoji: '🏜️',
    description: 'Vintage paper tone for eye strain reduction',
    bg: '#EDE0C8',
    text: '#2C1810',
    accent: '#8B4513',
    overlay: 'rgba(200, 169, 126, 0.18)',
  },
  'focus-punch': {
    label: 'Focus Punch',
    emoji: '🎯',
    description: 'Vignette highlight for deep concentration',
    bg: '#1A1A2E',
    text: '#E8E8FF',
    accent: '#F0A500',
  },
  'monochrome': {
    label: 'Monochrome',
    emoji: '⬛',
    description: 'Grayscale, distraction-free reading',
    bg: '#F0F0F0',
    text: '#111111',
    accent: '#555555',
  },
  'garden-sage': {
    label: 'Garden Sage',
    emoji: '🌿',
    description: 'Soft green background, easy on the eyes',
    bg: '#CCE8CC',
    text: '#0A0A0A',
    accent: '#2D6A4F',
  },
};

export const ANNOTATION_META = {
  important:  { emoji: '📌', label: 'Important',  color: 'text-red-400',    bg: 'bg-red-400/10',    border: 'border-red-400/30' },
  definition: { emoji: '📖', label: 'Definition', color: 'text-blue-400',   bg: 'bg-blue-400/10',   border: 'border-blue-400/30' },
  question:   { emoji: '❓', label: 'Question',   color: 'text-yellow-400', bg: 'bg-yellow-400/10', border: 'border-yellow-400/30' },
  revision:   { emoji: '🔄', label: 'Revision',   color: 'text-green-400',  bg: 'bg-green-400/10',  border: 'border-green-400/30' },
  quote:      { emoji: '💬', label: 'Quote',      color: 'text-purple-400', bg: 'bg-purple-400/10', border: 'border-purple-400/30' },
} as const;
