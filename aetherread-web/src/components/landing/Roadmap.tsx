'use client';

import { CheckCircle2, Circle, Clock, ArrowRight } from 'lucide-react';
import Link from 'next/link';

const PHASES = [
  {
    phase: 'Phase 1',
    title: 'Foundations',
    status: 'done',
    items: [
      { label: 'Project architecture & ADRs', done: true },
      { label: 'Repository setup (3 repos)', done: true },
      { label: 'Supabase schema hardening', done: false },
    ],
  },
  {
    phase: 'Phase 2',
    title: 'Web MVP',
    status: 'active',
    items: [
      { label: 'PDF rendering with offline storage', done: false },
      { label: '5 reading themes (Comfort Engine)', done: false },
      { label: 'Reading analytics & WPM tracking', done: false },
      { label: 'Cold start onboarding', done: false },
    ],
  },
  {
    phase: 'Phase 3',
    title: 'Native Android',
    status: 'upcoming',
    items: [
      { label: 'Native PDF rendering with memory safety', done: false },
      { label: 'Tablet layout (NavigationRail)', done: false },
      { label: 'Offline-first with Room DB', done: false },
    ],
  },
  {
    phase: 'Phase 4',
    title: 'Decoupled Sync',
    status: 'upcoming',
    items: [
      { label: 'LWW reading position sync', done: false },
      { label: 'Two-way merge for annotations', done: false },
      { label: 'Sync status indicator UI', done: false },
    ],
  },
  {
    phase: 'Phase 5',
    title: 'Local AI Brain',
    status: 'upcoming',
    items: [
      { label: 'PDF extraction with Marker', done: false },
      { label: 'ChromaDB vector storage', done: false },
      { label: 'Ollama integration', done: false },
      { label: 'RAGAS evaluation pipeline', done: false },
    ],
  },
  {
    phase: 'Phase 6',
    title: 'Cloud Workspace',
    status: 'upcoming',
    items: [
      { label: 'Multi-document projects', done: false },
      { label: 'Revision pack generator', done: false },
      { label: 'Hybrid search (keyword + vector)', done: false },
    ],
  },
];

const STATUS_STYLES = {
  done:     { badge: 'bg-green-500/15 text-green-400 border-green-500/30', border: 'border-green-500/20' },
  active:   { badge: 'bg-aether-500/15 text-aether-400 border-aether-500/30', border: 'border-aether-500/40' },
  upcoming: { badge: 'bg-white/5 text-[var(--color-text-muted)] border-white/10', border: 'border-white/5' },
};

export default function Roadmap() {
  return (
    <section id="roadmap" className="py-32 px-6">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-16">
          <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full glass border border-green-500/30 text-sm text-green-300 mb-6">
            <Clock size={12} />
            Development Roadmap
          </div>
          <h2 className="text-4xl md:text-5xl font-black text-white mb-4">
            From MVP to <span className="gradient-text">production</span>
          </h2>
          <p className="text-lg text-[var(--color-text-muted)] max-w-2xl mx-auto">
            Six focused phases, each delivering real value. Currently in Phase 2.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5 mb-16">
          {PHASES.map((phase, i) => {
            const styles = STATUS_STYLES[phase.status as keyof typeof STATUS_STYLES];
            return (
              <div
                key={phase.phase}
                className={`rounded-2xl p-6 border glass animate-slide-up ${
                  phase.status === 'active' ? 'ring-1 ring-aether-500/30 shadow-lg shadow-aether-500/10' : ''
                }`}
                style={{ animationDelay: `${i * 0.07}s`, borderColor: phase.status === 'active' ? 'rgba(108,99,255,0.3)' : 'rgba(255,255,255,0.05)' }}
              >
                <div className="flex items-center justify-between mb-4">
                  <span className="text-xs font-mono text-[var(--color-text-muted)]">{phase.phase}</span>
                  <span className={`px-2 py-0.5 rounded-full text-xs font-medium border ${styles.badge}`}>
                    {phase.status === 'done' ? '✓ Complete' : phase.status === 'active' ? '⚡ Active' : 'Upcoming'}
                  </span>
                </div>
                <h3 className="font-bold text-white mb-4">{phase.title}</h3>
                <ul className="space-y-2">
                  {phase.items.map(item => (
                    <li key={item.label} className="flex items-start gap-2 text-sm">
                      {item.done
                        ? <CheckCircle2 size={14} className="text-green-400 flex-shrink-0 mt-0.5" />
                        : <Circle size={14} className="text-[var(--color-border)] flex-shrink-0 mt-0.5" />
                      }
                      <span className={item.done ? 'text-[var(--color-text-muted)] line-through' : 'text-[var(--color-text-muted)]'}>
                        {item.label}
                      </span>
                    </li>
                  ))}
                </ul>
              </div>
            );
          })}
        </div>

        {/* CTA */}
        <div className="text-center">
          <Link
            id="roadmap-cta"
            href="/app"
            className="group inline-flex items-center gap-2 px-8 py-4 rounded-xl font-bold text-white bg-aether-500 hover:bg-aether-400 transition-all duration-300 shadow-2xl shadow-aether-500/30 hover:shadow-aether-500/50"
          >
            Try the App Now
            <ArrowRight size={18} className="group-hover:translate-x-1 transition-transform" />
          </Link>
          <p className="mt-4 text-sm text-[var(--color-text-muted)]">
            Offline-first · No account required for local reading
          </p>
        </div>
      </div>
    </section>
  );
}
