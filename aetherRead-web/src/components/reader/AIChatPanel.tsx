'use client';

import { Brain, Sparkles, Wifi, ArrowRight, X } from 'lucide-react';
import { useReaderStore } from '@/stores/useReaderStore';

export default function AIChatPanel() {
  const { showAIPanel, toggleAIPanel, activeTheme } = useReaderStore();

  if (!showAIPanel) return null;

  return (
    <aside className="w-80 flex-shrink-0 glass border-l border-[var(--color-border)] flex flex-col h-full animate-slide-up">
      {/* Header */}
      <div className="flex items-center justify-between px-4 py-3 border-b border-[var(--color-border)]">
        <div className="flex items-center gap-2">
          <div className="w-6 h-6 rounded-lg bg-purple-500/20 flex items-center justify-center">
            <Brain size={12} className="text-purple-400" />
          </div>
          <span className="text-sm font-semibold text-white">AI Brain</span>
          <span className="px-1.5 py-0.5 rounded-full bg-orange-500/15 border border-orange-500/30 text-xs text-orange-400">
            Phase 5
          </span>
        </div>
        <button
          id="ai-panel-close"
          onClick={toggleAIPanel}
          className="p-1.5 rounded-lg text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 transition-colors"
        >
          <X size={13} />
        </button>
      </div>

      {/* Coming soon content */}
      <div className="flex-1 flex flex-col items-center justify-center p-6 text-center">
        <div className="w-20 h-20 rounded-2xl bg-purple-500/10 border border-purple-500/20 flex items-center justify-center mb-6 animate-glow-pulse">
          <Brain size={36} className="text-purple-400" />
        </div>

        <h3 className="text-lg font-bold text-white mb-2">Local AI Brain</h3>
        <p className="text-sm text-[var(--color-text-muted)] leading-relaxed mb-6">
          Chat with your document using locally running AI. No internet required — your files never
          leave your device.
        </p>

        <div className="w-full space-y-3 mb-6">
          {[
            { icon: Sparkles, text: 'Ask questions about the content', color: 'text-aether-400', bg: 'bg-aether-500/10' },
            { icon: Wifi, text: 'Powered by Ollama (local LLM)', color: 'text-green-400', bg: 'bg-green-500/10' },
            { icon: Brain, text: 'ChromaDB vector search with citations', color: 'text-purple-400', bg: 'bg-purple-500/10' },
          ].map(item => {
            const Icon = item.icon;
            return (
              <div key={item.text} className={`flex items-center gap-3 px-4 py-3 rounded-xl ${item.bg} border border-white/5 text-left`}>
                <Icon size={14} className={item.color} />
                <span className="text-xs text-[var(--color-text-muted)]">{item.text}</span>
              </div>
            );
          })}
        </div>

        <div className="w-full px-4 py-3 rounded-xl glass-light border border-white/5 text-left">
          <p className="text-xs text-[var(--color-text-muted)] mb-2 uppercase tracking-wider font-medium">Roadmap</p>
          <div className="flex items-center gap-2 text-sm text-white">
            <div className="w-1.5 h-1.5 rounded-full bg-aether-400 animate-pulse" />
            Coming in Phase 5
          </div>
          <a
            href="/#roadmap"
            className="flex items-center gap-1.5 text-xs text-aether-400 hover:text-aether-300 mt-2 transition-colors"
          >
            View full roadmap <ArrowRight size={10} />
          </a>
        </div>
      </div>
    </aside>
  );
}
