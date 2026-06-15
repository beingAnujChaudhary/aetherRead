'use client';

import { Code2, Database, Smartphone, Server, Brain, Layers } from 'lucide-react';

const STACK_LAYERS = [
  {
    icon: Code2,
    layer: 'Web Frontend',
    border: 'border-blue-200',
    iconColor: 'text-blue-600',
    dot: 'bg-blue-500',
    items: ['Next.js 14 (App Router)', 'TypeScript', 'TailwindCSS', 'Dexie.js (IndexedDB)', 'Zustand', '@react-pdf-viewer/core'],
  },
  {
    icon: Smartphone,
    layer: 'Android App',
    border: 'border-green-200',
    iconColor: 'text-green-600',
    dot: 'bg-green-500',
    items: ['Kotlin + Jetpack Compose', 'Room Database', 'Hilt DI', 'Retrofit', 'AndroidPdfViewer', 'WindowSizeClass'],
  },
  {
    icon: Server,
    layer: 'Backend API',
    border: 'border-orange-200',
    iconColor: 'text-orange-600',
    dot: 'bg-orange-500',
    items: ['FastAPI + Python 3.11', 'PyMuPDF / Marker-pdf', 'Pydantic v2', 'Docker', 'GitHub Actions CI/CD', 'Uvicorn'],
  },
  {
    icon: Brain,
    layer: 'AI / ML Pipeline',
    border: 'border-[var(--land-accent-border)]',
    iconColor: 'text-[var(--land-accent)]',
    dot: 'bg-[var(--land-accent)]',
    items: ['Ollama (llama3.2:3b)', 'ChromaDB Vector Store', 'LangChain', 'RAGAS Evaluation', 'RAG with Citations', 'Local Mesh Networking'],
  },
  {
    icon: Database,
    layer: 'Database & Sync',
    border: 'border-cyan-200',
    iconColor: 'text-cyan-600',
    dot: 'bg-cyan-500',
    items: ['Supabase (PostgreSQL)', 'pgvector extension', 'Supabase Realtime', 'Row Level Security', 'LWW Conflict Resolution', 'Soft-Delete Pattern'],
  },
  {
    icon: Layers,
    layer: 'Architecture',
    border: 'border-pink-200',
    iconColor: 'text-pink-600',
    dot: 'bg-pink-500',
    items: ['Offline-First Design', 'Three-Repo Monorepo', 'ADR Documentation', 'Open-Core Model', 'PWA Support', 'WCAG AA Accessible'],
  },
];

export default function TechStack() {
  return (
    <section id="tech" className="py-32 px-6">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-16">
          <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full border border-cyan-300 bg-cyan-50 text-sm text-cyan-700 mb-6">
            <Layers size={12} />
            Engineering
          </div>
          <h2 className="text-4xl md:text-5xl font-black text-[var(--land-text)] mb-4">
            Production-grade <span className="gradient-text-land">architecture</span>
          </h2>
          <p className="text-lg text-[var(--land-text-muted)] max-w-2xl mx-auto">
            AetherRead uses enterprise-grade, decoupled architecture across three repositories
            with a strict offline-first, privacy-first engineering philosophy.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
          {STACK_LAYERS.map((layer, i) => {
            const Icon = layer.icon;
            return (
              <div
                key={layer.layer}
                className={`rounded-2xl p-6 border ${layer.border} bg-white hover:shadow-lg hover:scale-[1.02] transition-all duration-300 animate-slide-up`}
                style={{ animationDelay: `${i * 0.08}s` }}
              >
                <div className="flex items-center gap-3 mb-5">
                  <div className="w-10 h-10 rounded-xl bg-[var(--land-bg-alt)] flex items-center justify-center">
                    <Icon size={18} className={layer.iconColor} />
                  </div>
                  <h3 className="font-bold text-[var(--land-text)]">{layer.layer}</h3>
                </div>
                <ul className="space-y-2">
                  {layer.items.map(item => (
                    <li key={item} className="flex items-center gap-2 text-sm text-[var(--land-text-muted)]">
                      <div className={`w-1.5 h-1.5 rounded-full flex-shrink-0 ${layer.dot}`} />
                      {item}
                    </li>
                  ))}
                </ul>
              </div>
            );
          })}
        </div>

        {/* Architecture note */}
        <div className="mt-12 rounded-2xl p-8 border border-[var(--land-border)] bg-white text-center shadow-sm">
          <p className="text-[var(--land-text-muted)] text-sm leading-relaxed max-w-3xl mx-auto">
            <span className="text-[var(--land-text)] font-semibold">Three-repository structure: </span>
            <code className="text-blue-600 bg-blue-50 px-1.5 py-0.5 rounded text-xs">aetherread-web</code>{' · '}
            <code className="text-green-600 bg-green-50 px-1.5 py-0.5 rounded text-xs">aetherread-android</code>{' · '}
            <code className="text-orange-600 bg-orange-50 px-1.5 py-0.5 rounded text-xs">aetherread-api</code>
            {' '}— decoupled for independent deployment, versioning, and open-source release.
            Web and Android are MIT/AGPL-3.0. API is proprietary.
          </p>
        </div>
      </div>
    </section>
  );
}
