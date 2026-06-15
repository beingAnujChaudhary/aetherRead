'use client';

import {
  WifiOff, Brain, Palette, FileText, RefreshCw, BarChart2,
  Smartphone, Lock, Zap, BookMarked,
} from 'lucide-react';

const FEATURES = [
  {
    icon: WifiOff,
    title: 'True Offline-First',
    description: 'Every feature works without internet. PDFs, annotations, and reading progress stored locally via IndexedDB using Dexie.js.',
    color: 'from-blue-500/20 to-blue-600/5',
    iconColor: 'text-blue-400',
    iconBg: 'bg-blue-500/10',
    tag: 'Core',
  },
  {
    icon: Brain,
    title: 'Local AI Brain',
    description: 'Chat with your PDFs using Ollama-powered LLMs. ChromaDB vector store. Your documents never leave your network.',
    color: 'from-aether-500/20 to-aether-600/5',
    iconColor: 'text-aether-400',
    iconBg: 'bg-aether-500/10',
    tag: 'AI',
  },
  {
    icon: Palette,
    title: 'Comfort Engine',
    description: '5 hand-crafted reading themes: Dark Abyss, Book Paper, Sepia Sands, Focus Punch, and Monochrome — switch instantly without re-render.',
    color: 'from-purple-500/20 to-purple-600/5',
    iconColor: 'text-purple-400',
    iconBg: 'bg-purple-500/10',
    tag: 'UX',
  },
  {
    icon: FileText,
    title: 'Page-Level Annotations',
    description: 'Attach rich Markdown notes to any page, categorized as Important, Definition, Question, Revision, or Quote.',
    color: 'from-green-500/20 to-green-600/5',
    iconColor: 'text-green-400',
    iconBg: 'bg-green-500/10',
    tag: 'Notes',
  },
  {
    icon: RefreshCw,
    title: 'Decoupled Sync',
    description: 'Intelligent LWW strategy for reading position. Two-way merge for annotations. Soft-delete pattern ensures zero data loss.',
    color: 'from-orange-500/20 to-orange-600/5',
    iconColor: 'text-orange-400',
    iconBg: 'bg-orange-500/10',
    tag: 'Sync',
  },
  {
    icon: BarChart2,
    title: 'Reading Analytics',
    description: 'WPM tracking, reading streaks, daily goals, and predictive "Time Left in Chapter" algorithm.',
    color: 'from-yellow-500/20 to-yellow-600/5',
    iconColor: 'text-yellow-400',
    iconBg: 'bg-yellow-500/10',
    tag: 'Analytics',
  },
  {
    icon: Smartphone,
    title: 'Native Android App',
    description: 'Kotlin + Jetpack Compose with WindowSizeClass tablet layouts, haptic feedback, and edge-to-edge UI.',
    color: 'from-red-500/20 to-red-600/5',
    iconColor: 'text-red-400',
    iconBg: 'bg-red-500/10',
    tag: 'Mobile',
  },
  {
    icon: Lock,
    title: 'Privacy First',
    description: 'Row Level Security on Supabase, TLS 1.3 in transit, zero frontend API key exposure, full JSON export.',
    color: 'from-cyan-500/20 to-cyan-600/5',
    iconColor: 'text-cyan-400',
    iconBg: 'bg-cyan-500/10',
    tag: 'Security',
  },
  {
    icon: BookMarked,
    title: 'Revision Packs',
    description: 'Auto-compile your highlights into Markdown study guides, Anki CSV flashcards, or Obsidian vault exports.',
    color: 'from-pink-500/20 to-pink-600/5',
    iconColor: 'text-pink-400',
    iconBg: 'bg-pink-500/10',
    tag: 'Study',
  },
];

export default function Features() {
  return (
    <section id="features" className="py-32 px-6">
      <div className="max-w-7xl mx-auto">
        {/* Section header */}
        <div className="text-center mb-16">
          <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full glass border border-aether-500/30 text-sm text-aether-300 mb-6">
            <Zap size={12} />
            Everything you need
          </div>
          <h2 className="text-4xl md:text-5xl font-black text-white mb-4">
            Built for serious readers
          </h2>
          <p className="text-lg text-[var(--color-text-muted)] max-w-2xl mx-auto">
            Every feature is designed around one principle: your reading flow should never be
            interrupted — by internet, by apps, or by distractions.
          </p>
        </div>

        {/* Feature grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
          {FEATURES.map((feature, i) => {
            const Icon = feature.icon;
            return (
              <div
                key={feature.title}
                className={`relative group rounded-2xl p-6 border border-white/5 bg-gradient-to-br ${feature.color} hover:border-white/10 transition-all duration-300 hover:-translate-y-1 cursor-default animate-slide-up`}
                style={{ animationDelay: `${i * 0.07}s` }}
              >
                <div className="flex items-start gap-4 mb-4">
                  <div className={`w-10 h-10 rounded-xl ${feature.iconBg} flex items-center justify-center flex-shrink-0`}>
                    <Icon size={18} className={feature.iconColor} />
                  </div>
                  <span className="px-2 py-0.5 rounded-md text-xs font-medium bg-white/5 text-[var(--color-text-muted)] self-start mt-1">
                    {feature.tag}
                  </span>
                </div>
                <h3 className="text-lg font-bold text-white mb-2">{feature.title}</h3>
                <p className="text-sm text-[var(--color-text-muted)] leading-relaxed">
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
