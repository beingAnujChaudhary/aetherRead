'use client';

import { useState } from 'react';
import { THEMES } from '@/lib/utils';
import type { ComfortTheme } from '@/lib/db';
import { Palette } from 'lucide-react';

const THEME_ORDER: ComfortTheme[] = [
  'dark-abyss',
  'book-paper',
  'sepia-sands',
  'focus-punch',
  'monochrome',
  'garden-sage',
];

// Mock PDF text for the preview
const MOCK_TEXT = [
  { page: 1, lines: ['Chapter 4: Attention Mechanisms', '', 'The transformer architecture introduced in "Attention is All You Need" (Vaswani et al., 2017) revolutionized natural language processing by replacing recurrent layers with self-attention mechanisms.'] },
  { page: 2, lines: ['Self-attention computes:', '', 'Attention(Q, K, V) = softmax(QK^T / √d_k) × V', '', 'where Q, K, V are query, key, and value matrices derived from the input sequence.'] },
];

export default function ThemeShowcase() {
  const [activeTheme, setActiveTheme] = useState<ComfortTheme>('dark-abyss');
  const theme = THEMES[activeTheme];

  return (
    <section id="themes" className="py-32 px-6 relative overflow-hidden">
      {/* Background */}
      <div
        className="hero-sphere w-[500px] h-[500px] top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 opacity-10"
        style={{ background: 'radial-gradient(circle, #6C63FF 0%, transparent 70%)' }}
      />

      <div className="relative z-10 max-w-7xl mx-auto">
        {/* Header */}
        <div className="text-center mb-16">
          <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full glass border border-purple-500/30 text-sm text-purple-300 mb-6">
            <Palette size={12} />
            Comfort Engine
          </div>
          <h2 className="text-4xl md:text-5xl font-black text-white mb-4">
            6 reading themes. <br />
            <span className="gradient-text">Zero eye strain.</span>
          </h2>
          <p className="text-lg text-[var(--color-text-muted)] max-w-2xl mx-auto">
            Each theme is scientifically crafted for different lighting conditions and reading
            sessions. Switch instantly — your eyes will thank you.
          </p>
        </div>

        <div className="grid lg:grid-cols-[1fr_auto] gap-8 items-start">
          {/* Theme picker */}
          <div className="flex flex-wrap lg:flex-col gap-3 lg:order-last">
            {THEME_ORDER.map(key => {
              const t = THEMES[key];
              const isActive = key === activeTheme;
              return (
                <button
                  key={key}
                  id={`theme-btn-${key}`}
                  onClick={() => setActiveTheme(key)}
                  className={`flex items-center gap-3 px-4 py-3 rounded-xl border text-left transition-all duration-200 ${
                    isActive
                      ? 'border-aether-500/60 bg-aether-500/10 shadow-lg shadow-aether-500/20'
                      : 'border-white/5 hover:border-white/15 hover:bg-white/3'
                  }`}
                >
                  {/* Color swatch */}
                  <div
                    className="w-8 h-8 rounded-lg flex-shrink-0 border border-white/10"
                    style={{ background: t.bg }}
                  />
                  <div className="min-w-0">
                    <div className="text-sm font-semibold text-white">
                      {t.emoji} {t.label}
                    </div>
                    <div className="text-xs text-[var(--color-text-muted)] truncate">
                      {t.description}
                    </div>
                  </div>
                </button>
              );
            })}
          </div>

          {/* Live preview */}
          <div
            className="flex-1 rounded-2xl overflow-hidden shadow-2xl border transition-all duration-500"
            style={{
              background: theme.bg,
              borderColor: `${theme.accent}30`,
              boxShadow: `0 24px 80px ${theme.accent}20`,
            }}
          >
            {/* Fake reader toolbar */}
            <div
              className="flex items-center justify-between px-4 py-3 border-b"
              style={{
                background: `${theme.bg}ee`,
                borderColor: `${theme.accent}20`,
              }}
            >
              <div className="flex items-center gap-2">
                <div className="w-3 h-3 rounded-full" style={{ background: '#ff5f57' }} />
                <div className="w-3 h-3 rounded-full" style={{ background: '#febc2e' }} />
                <div className="w-3 h-3 rounded-full" style={{ background: '#28c840' }} />
              </div>
              <span className="text-xs font-mono opacity-60" style={{ color: theme.text }}>
                research_paper.pdf — Page 4 of 42
              </span>
              <div
                className="text-xs px-2 py-1 rounded-md font-medium"
                style={{ background: `${theme.accent}20`, color: theme.accent }}
              >
                {THEMES[activeTheme].emoji} {THEMES[activeTheme].label}
              </div>
            </div>

            {/* Fake PDF content */}
            <div className="p-8 md:p-12 min-h-[360px]" style={{ position: 'relative' }}>
              {/* Focus punch vignette */}
              {activeTheme === 'focus-punch' && (
                <div
                  className="absolute inset-0 pointer-events-none"
                  style={{ background: 'radial-gradient(ellipse at center, transparent 50%, rgba(0,0,0,0.65) 100%)' }}
                />
              )}

              {MOCK_TEXT.map((section, si) => (
                <div key={si} className="mb-6">
                  {section.lines.map((line, li) => (
                    <div key={li} className={line === '' ? 'h-3' : ''}>
                      {line && (
                        <p
                          className={`transition-colors duration-500 ${
                            li === 0 ? 'font-bold text-xl mb-3' : 'text-sm leading-relaxed opacity-85'
                          } ${line.startsWith('Attention') ? 'font-mono text-base px-4 py-3 rounded-lg my-2' : ''}`}
                          style={{
                            color: theme.text,
                            background: line.startsWith('Attention')
                              ? `${theme.accent}15`
                              : 'transparent',
                            borderLeft: line.startsWith('Attention')
                              ? `3px solid ${theme.accent}`
                              : 'none',
                          }}
                        >
                          {line}
                        </p>
                      )}
                    </div>
                  ))}
                </div>
              ))}

              {/* Annotation badge */}
              <div
                className="inline-flex items-center gap-2 px-3 py-1.5 rounded-full text-xs font-medium mt-4"
                style={{
                  background: `${theme.accent}15`,
                  color: theme.accent,
                  border: `1px solid ${theme.accent}30`,
                }}
              >
                📌 Important — Page 4
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
