'use client';

import { THEMES } from '@/lib/utils';
import type { ComfortTheme } from '@/lib/db';
import { useReaderStore } from '@/stores/useReaderStore';
import { Check } from 'lucide-react';

export default function ThemeSelector() {
  const { activeTheme, setTheme } = useReaderStore();

  return (
    <div className="absolute right-0 top-full mt-2 z-50 glass rounded-2xl border border-[var(--color-border)] p-3 w-72 shadow-2xl shadow-black/50 animate-scale-in">
      <p className="text-xs font-semibold text-[var(--color-text-muted)] uppercase tracking-wider px-2 mb-3">
        Comfort Engine
      </p>
      <div className="space-y-1">
        {(Object.keys(THEMES) as ComfortTheme[]).map(key => {
          const theme = THEMES[key];
          const isActive = key === activeTheme;
          return (
            <button
              key={key}
              id={`theme-select-${key}`}
              onClick={() => setTheme(key)}
              className={`w-full flex items-center gap-3 px-3 py-2.5 rounded-xl text-left transition-all duration-150 ${
                isActive
                  ? 'bg-aether-500/15 border border-aether-500/30'
                  : 'hover:bg-white/5 border border-transparent'
              }`}
            >
              {/* Swatch */}
              <div
                className="w-7 h-7 rounded-lg flex-shrink-0 border border-white/10"
                style={{ background: theme.bg }}
              />
              <div className="flex-1 min-w-0">
                <div className="text-sm font-medium text-white">
                  {theme.emoji} {theme.label}
                </div>
                <div className="text-xs text-[var(--color-text-muted)] truncate">
                  {theme.description}
                </div>
              </div>
              {isActive && <Check size={14} className="text-aether-400 flex-shrink-0" />}
            </button>
          );
        })}
      </div>
    </div>
  );
}
