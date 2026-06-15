'use client';

import {
  WifiOff, Brain, Palette, FileText, RefreshCw, BarChart2,
  Smartphone, Monitor, Lock, Zap, PenTool, Grid,
} from 'lucide-react';

const FEATURES = [
  {
    icon: WifiOff,
    title: 'True Offline-First',
    description: 'Every feature works without internet. PDFs, annotations, and reading progress stored locally via IndexedDB using Dexie.js.',
    border: 'border-blue-200',
    iconColor: 'text-blue-600',
    iconBg: 'bg-blue-50',
    tag: 'Core',
    tagColor: 'bg-blue-50 text-blue-600',
  },
  {
    icon: Brain,
    title: 'Local AI Brain',
    description: 'Chat with your PDFs using Ollama-powered LLMs. ChromaDB vector store. Your documents never leave your network.',
    border: 'border-[var(--land-accent-border)]',
    iconColor: 'text-[var(--land-accent)]',
    iconBg: 'bg-[var(--land-accent-light)]',
    tag: 'AI',
    tagColor: 'bg-[var(--land-accent-light)] text-[var(--land-accent)]',
  },
  {
    icon: Palette,
    title: '6 Comfort Themes',
    description: 'Dark Abyss, Book Paper, Sepia Sands, Focus Punch, Monochrome, Garden Sage — hand-crafted reading themes that switch instantly.',
    border: 'border-purple-200',
    iconColor: 'text-purple-600',
    iconBg: 'bg-purple-50',
    tag: 'UX',
    tagColor: 'bg-purple-50 text-purple-600',
  },
  {
    icon: FileText,
    title: 'Page-Level Annotations',
    description: 'Attach rich Markdown notes to any page, categorized as Important, Definition, Question, Revision, or Quote.',
    border: 'border-green-200',
    iconColor: 'text-green-600',
    iconBg: 'bg-green-50',
    tag: 'Notes',
    tagColor: 'bg-green-50 text-green-600',
  },
  {
    icon: PenTool,
    title: '13 Annotation Tools',
    description: 'Smart Pen, Text Highlight, Freehand Draw, Text Underline, Strikethrough, Squiggly, Sticky Note, Callout, Eraser, Multi-select — with live color picker.',
    border: 'border-amber-200',
    iconColor: 'text-amber-600',
    iconBg: 'bg-amber-50',
    tag: 'Annotate',
    tagColor: 'bg-amber-50 text-amber-600',
  },
  {
    icon: Smartphone,
    title: 'Native Android App',
    description: 'Kotlin + Jetpack Compose. Full Xodo-style UI: Files, Toolbox, My AetherRead, Aether Sign tabs. Annotation tools, comfort themes, reader toolbar.',
    border: 'border-red-200',
    iconColor: 'text-red-600',
    iconBg: 'bg-red-50',
    tag: 'Android',
    tagColor: 'bg-red-50 text-red-600',
  },
  {
    icon: Monitor,
    title: 'Native Windows App',
    description: 'C# WPF (.NET 8) — zero Electron overhead. Google Pdfium rendering engine, left navigation rail, native file dialogs. Windows 10/11.',
    border: 'border-sky-200',
    iconColor: 'text-sky-600',
    iconBg: 'bg-sky-50',
    tag: 'Windows',
    tagColor: 'bg-sky-50 text-sky-600',
  },
  {
    icon: Grid,
    title: 'Toolbox Suite',
    description: 'Scan Document, Image to PDF, eSign PDF, OCR Text Recognition, Convert from/to PDF, and Manage PDF — all in one unified toolbox.',
    border: 'border-teal-200',
    iconColor: 'text-teal-600',
    iconBg: 'bg-teal-50',
    tag: 'Tools',
    tagColor: 'bg-teal-50 text-teal-600',
  },
  {
    icon: RefreshCw,
    title: 'Decoupled Sync',
    description: 'LWW strategy for reading position. Two-way annotation merge. Soft-delete pattern ensures zero data loss across devices.',
    border: 'border-orange-200',
    iconColor: 'text-orange-600',
    iconBg: 'bg-orange-50',
    tag: 'Sync',
    tagColor: 'bg-orange-50 text-orange-600',
  },
  {
    icon: BarChart2,
    title: 'Reading Analytics',
    description: 'WPM tracking, reading streaks, daily goals, and predictive "Time Left in Chapter" algorithm.',
    border: 'border-yellow-200',
    iconColor: 'text-yellow-600',
    iconBg: 'bg-yellow-50',
    tag: 'Analytics',
    tagColor: 'bg-yellow-50 text-yellow-600',
  },
  {
    icon: Lock,
    title: 'Privacy First',
    description: 'Row Level Security on Supabase, TLS 1.3 in transit, zero frontend API key exposure, full JSON export. Your documents stay yours.',
    border: 'border-cyan-200',
    iconColor: 'text-cyan-600',
    iconBg: 'bg-cyan-50',
    tag: 'Security',
    tagColor: 'bg-cyan-50 text-cyan-600',
  },
  {
    icon: FileText,
    title: 'Revision Packs',
    description: 'Auto-compile highlights into Markdown study guides, Anki CSV flashcards, or Obsidian vault exports.',
    border: 'border-pink-200',
    iconColor: 'text-pink-600',
    iconBg: 'bg-pink-50',
    tag: 'Study',
    tagColor: 'bg-pink-50 text-pink-600',
  },
];

export default function Features() {
  return (
    <section id="features" className="py-32 px-6">
      <div className="max-w-7xl mx-auto">
        {/* Section header */}
        <div className="text-center mb-16">
          <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full border border-[var(--land-accent-border)] bg-[var(--land-accent-light)] text-sm text-[var(--land-accent)] mb-6">
            <Zap size={12} />
            Everything you need
          </div>
          <h2 className="text-4xl md:text-5xl font-black text-[var(--land-text)] mb-4">
            Built for serious readers
          </h2>
          <p className="text-lg text-[var(--land-text-muted)] max-w-2xl mx-auto">
            Every feature is designed around one principle: your reading flow should never be
            interrupted — by internet, by apps, or by distractions.
          </p>
        </div>

        {/* Platform badges */}
        <div className="flex flex-wrap justify-center gap-3 mb-12">
          {[
            { label: '🌐 Web App', color: 'bg-[var(--land-accent-light)] text-[var(--land-accent)] border-[var(--land-accent-border)]' },
            { label: '🤖 Android', color: 'bg-red-50 text-red-600 border-red-200' },
            { label: '🖥️ Windows Native', color: 'bg-sky-50 text-sky-600 border-sky-200' },
          ].map(badge => (
            <span key={badge.label} className={`px-4 py-1.5 rounded-full border text-sm font-medium ${badge.color}`}>
              {badge.label}
            </span>
          ))}
        </div>

        {/* Feature grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
          {FEATURES.map((feature, i) => {
            const Icon = feature.icon;
            return (
              <div
                key={feature.title}
                className={`relative group rounded-2xl p-6 border ${feature.border} bg-white hover:shadow-lg transition-all duration-300 hover:-translate-y-1 cursor-default animate-slide-up`}
                style={{ animationDelay: `${i * 0.07}s` }}
              >
                <div className="flex items-start gap-4 mb-4">
                  <div className={`w-10 h-10 rounded-xl ${feature.iconBg} flex items-center justify-center flex-shrink-0`}>
                    <Icon size={18} className={feature.iconColor} />
                  </div>
                  <span className={`px-2 py-0.5 rounded-md text-xs font-medium ${feature.tagColor} self-start mt-1`}>
                    {feature.tag}
                  </span>
                </div>
                <h3 className="text-lg font-bold text-[var(--land-text)] mb-2">{feature.title}</h3>
                <p className="text-sm text-[var(--land-text-muted)] leading-relaxed">
                  {feature.description}
                </p>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
}
