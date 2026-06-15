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
      { label: 'Supabase schema hardening', done: true },
    ],
  },
  {
    phase: 'Phase 2',
    title: 'Web MVP',
    status: 'done',
    items: [
      { label: 'PDF rendering with offline storage', done: true },
      { label: '6 reading themes (Comfort Engine)', done: true },
      { label: 'Reading analytics & WPM tracking', done: true },
      { label: 'Cold start onboarding', done: true },
    ],
  },
  {
    phase: 'Phase 3',
    title: 'Native Android',
    status: 'done',
    items: [
      { label: 'Native PDF rendering with memory safety', done: true },
      { label: 'Tablet layout (NavigationRail)', done: true },
      { label: 'Offline-first with Room DB', done: true },
    ],
  },
  {
    phase: 'Phase 4',
    title: 'Windows Native WPF',
    status: 'done',
    items: [
      { label: 'PdfiumViewer C++ rendering', done: true },
      { label: 'Native WPF window & dark mode', done: true },
      { label: 'Xodo-style navigation rail', done: true },
    ],
  },
  {
    phase: 'Phase 5',
    title: 'Decoupled Sync',
    status: 'active',
    items: [
      { label: 'LWW reading position sync', done: false },
      { label: 'Two-way merge for annotations', done: false },
      { label: 'Sync status indicator UI', done: false },
    ],
  },
  {
    phase: 'Phase 6',
    title: 'Local AI Brain',
    status: 'upcoming',
    items: [
      { label: 'PDF extraction with Marker', done: false },
      { label: 'Ollama integration', done: false },
      { label: 'RAGAS evaluation pipeline', done: false },
    ],
  },
];

const STATUS_STYLES = {
  done:     { badge: 'bg-green-50 text-green-700 border-green-200', card: 'border-green-200' },
  active:   { badge: 'bg-[var(--land-accent-light)] text-[var(--land-accent)] border-[var(--land-accent-border)]', card: 'border-[var(--land-accent)] ring-1 ring-[var(--land-accent-border)]' },
  upcoming: { badge: 'bg-[var(--land-bg-alt)] text-[var(--land-text-muted)] border-[var(--land-border)]', card: 'border-[var(--land-border)]' },
};

export default function Roadmap() {
  return (
    <section id="roadmap" className="py-32 px-6">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-16">
          <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full border border-green-300 bg-green-50 text-sm text-green-700 mb-6">
            <Clock size={12} />
            Development Roadmap
          </div>
          <h2 className="text-4xl md:text-5xl font-black text-[var(--land-text)] mb-4">
            From MVP to <span className="gradient-text-land">production</span>
          </h2>
          <p className="text-lg text-[var(--land-text-muted)] max-w-2xl mx-auto">
            Six focused phases, each delivering real value. Currently in Phase 5.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5 mb-16">
          {PHASES.map((phase, i) => {
            const styles = STATUS_STYLES[phase.status as keyof typeof STATUS_STYLES];
            return (
              <div
                key={phase.phase}
                className={`rounded-2xl p-6 border bg-white animate-slide-up ${styles.card}`}
                style={{ animationDelay: `${i * 0.07}s` }}
              >
                <div className="flex items-center justify-between mb-4">
                  <span className="text-xs font-mono text-[var(--land-text-soft)]">{phase.phase}</span>
                  <span className={`px-2 py-0.5 rounded-full text-xs font-medium border ${styles.badge}`}>
                    {phase.status === 'done' ? '✓ Complete' : phase.status === 'active' ? '⚡ Active' : 'Upcoming'}
                  </span>
                </div>
                <h3 className="font-bold text-[var(--land-text)] mb-4">{phase.title}</h3>
                <ul className="space-y-2">
                  {phase.items.map(item => (
                    <li key={item.label} className="flex items-start gap-2 text-sm">
                      {item.done
                        ? <CheckCircle2 size={14} className="text-green-500 flex-shrink-0 mt-0.5" />
                        : <Circle size={14} className="text-[var(--land-border)] flex-shrink-0 mt-0.5" />
                      }
                      <span className={item.done ? 'text-[var(--land-text-soft)] line-through' : 'text-[var(--land-text-muted)]'}>
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
            className="btn-accent group inline-flex items-center gap-2 px-8 py-4 rounded-xl font-bold"
          >
            Try the App Now
            <ArrowRight size={18} className="group-hover:translate-x-1 transition-transform" />
          </Link>
          <p className="mt-4 text-sm text-[var(--land-text-muted)]">
            Offline-first · No account required for local reading
          </p>
        </div>
      </div>
    </section>
  );
}
