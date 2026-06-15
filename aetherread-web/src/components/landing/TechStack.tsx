'use client';

import { Code2, Database, Smartphone, Server, Brain, Layers } from 'lucide-react';

const STACK_LAYERS = [
  {
    icon: Code2,
    layer: 'Web Frontend',
    color: 'text-blue-400',
    bg: 'bg-blue-500/10',
    border: 'border-blue-500/20',
    items: ['Next.js 14 (App Router)', 'TypeScript', 'TailwindCSS', 'Dexie.js (IndexedDB)', 'Zustand', '@react-pdf-viewer/core'],
  },
  {
    icon: Smartphone,
    layer: 'Android App',
    color: 'text-green-400',
    bg: 'bg-green-500/10',
    border: 'border-green-500/20',
    items: ['Kotlin + Jetpack Compose', 'Room Database', 'Hilt DI', 'Retrofit', 'AndroidPdfViewer', 'WindowSizeClass'],
  },
  {
    icon: Server,
    layer: 'Backend API',
    color: 'text-orange-400',
    bg: 'bg-orange-500/10',
    border: 'border-orange-500/20',
    items: ['FastAPI + Python 3.11', 'PyMuPDF / Marker-pdf', 'Pydantic v2', 'Docker', 'GitHub Actions CI/CD', 'Uvicorn'],
  },
  {
    icon: Brain,
    layer: 'AI / ML Pipeline',
    color: 'text-aether-400',
    bg: 'bg-aether-500/10',
    border: 'border-aether-500/20',
    items: ['Ollama (llama3.2:3b)', 'ChromaDB Vector Store', 'LangChain', 'RAGAS Evaluation', 'RAG with Citations', 'Local Mesh Networking'],
  },
  {
    icon: Database,
    layer: 'Database & Sync',
    color: 'text-cyan-400',
    bg: 'bg-cyan-500/10',
    border: 'border-cyan-500/20',
    items: ['Supabase (PostgreSQL)', 'pgvector extension', 'Supabase Realtime', 'Row Level Security', 'LWW Conflict Resolution', 'Soft-Delete Pattern'],
  },
  {
    icon: Layers,
    layer: 'Architecture',
    color: 'text-pink-400',
    bg: 'bg-pink-500/10',
    border: 'border-pink-500/20',
    items: ['Offline-First Design', 'Three-Repo Monorepo', 'ADR Documentation', 'Open-Core Model', 'PWA Support', 'WCAG AA Accessible'],
  },
];

export default function TechStack() {
  return (
    <section id="tech" className="py-32 px-6">
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-16">
          <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full glass border border-cyan-500/30 text-sm text-cyan-300 mb-6">
            <Layers size={12} />
            Engineering
          </div>
          <h2 className="text-4xl md:text-5xl font-black text-white mb-4">
            Production-grade <span className="gradient-text">architecture</span>
          </h2>
          <p className="text-lg text-[var(--color-text-muted)] max-w-2xl mx-auto">
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
                className={`rounded-2xl p-6 border ${layer.border} ${layer.bg} hover:scale-[1.02] transition-all duration-300 animate-slide-up`}
                style={{ animationDelay: `${i * 0.08}s` }}
              >
                <div className="flex items-center gap-3 mb-5">
                  <div className={`w-10 h-10 rounded-xl bg-white/5 flex items-center justify-center`}>
                    <Icon size={18} className={layer.color} />
                  </div>
                  <h3 className="font-bold text-white">{layer.layer}</h3>
                </div>
                <ul className="space-y-2">
                  {layer.items.map(item => (
                    <li key={item} className="flex items-center gap-2 text-sm text-[var(--color-text-muted)]">
                      <div className={`w-1.5 h-1.5 rounded-full flex-shrink-0 ${layer.color.replace('text-', 'bg-')}`} />
                      {item}
                    </li>
                  ))}
                </ul>
              </div>
            );
          })}
        </div>

        {/* Architecture diagram note */}
        <div className="mt-12 glass rounded-2xl p-8 border border-white/5 text-center">
          <p className="text-[var(--color-text-muted)] text-sm leading-relaxed max-w-3xl mx-auto">
            <span className="text-white font-semibold">Three-repository structure: </span>
            <code className="text-aether-400 bg-aether-500/10 px-1.5 py-0.5 rounded text-xs">aetherread-web</code>{' · '}
            <code className="text-green-400 bg-green-500/10 px-1.5 py-0.5 rounded text-xs">aetherread-android</code>{' · '}
            <code className="text-orange-400 bg-orange-500/10 px-1.5 py-0.5 rounded text-xs">aetherread-api</code>
            {' '}— decoupled for independent deployment, versioning, and open-source release.
            Web and Android are MIT/AGPL-3.0. API is proprietary.
          </p>
        </div>
      </div>
    </section>
  );
}
