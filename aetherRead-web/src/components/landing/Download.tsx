'use client';

import Link from 'next/link';
import { Smartphone, Monitor, Download as DownloadIcon, ExternalLink, Github } from 'lucide-react';

const ANDROID_REPO = 'https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-android';
const WINDOWS_REPO = 'https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-windows-native';
const ANDROID_APK = '/projects/aetherRead/downloads/aetherRead-android.zip';
const WINDOWS_EXE = '/projects/aetherRead/downloads/aetherRead-windows.zip';
const WEB_REPO = 'https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-web';

export default function Download() {
  return (
    <section id="download" className="py-20 px-6">
      <div className="max-w-5xl mx-auto">
        {/* Section Header */}
        <div className="text-center mb-14">
          <span className="inline-block text-xs font-semibold tracking-widest uppercase text-[var(--land-accent)] mb-4">
            Download
          </span>
          <h2 className="text-3xl md:text-4xl font-bold text-[var(--land-text)] mb-4">
            Available on All Platforms
          </h2>
          <p className="text-[var(--land-text-muted)] max-w-xl mx-auto text-sm leading-relaxed">
            Download aetherRead on your preferred platform. All versions share the same open-source codebase under the MIT license.
          </p>
        </div>

        {/* Download Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-10">
          {/* Android Card */}
          <div className="group relative rounded-2xl border border-[var(--land-border)] bg-[var(--land-bg-alt)] p-8 hover:border-[var(--land-accent-border)] transition-all duration-300 overflow-hidden">
            {/* Gradient accent background */}
            <div className="absolute inset-0 bg-gradient-to-br from-[var(--land-accent)]/5 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300 pointer-events-none" />

            <div className="flex items-start justify-between mb-6">
              <div className="flex items-center gap-3">
                <div className="w-12 h-12 rounded-xl bg-[var(--land-accent)]/10 flex items-center justify-center">
                  <Smartphone className="text-[var(--land-accent)]" size={24} />
                </div>
                <div>
                  <h3 className="text-lg font-bold text-[var(--land-text)]">Android</h3>
                  <span className="text-xs text-[var(--land-text-muted)]">Android 8.0+ (Oreo)</span>
                </div>
              </div>
              <a
                href={ANDROID_REPO}
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center gap-1 text-xs text-[var(--land-text-muted)] hover:text-[var(--land-accent)] transition-colors"
              >
                <Github size={14} />
                Source
              </a>
            </div>

            <ul className="space-y-2 mb-8 text-sm text-[var(--land-text-muted)]">
              {['Native Jetpack Compose UI', 'Offline-first PDF reader', 'Freehand, highlight & annotation tools', 'AI Brain integration', 'Dark mode & comfort themes'].map(f => (
                <li key={f} className="flex items-center gap-2">
                  <span className="w-1.5 h-1.5 rounded-full bg-[var(--land-accent)] flex-shrink-0" />
                  {f}
                </li>
              ))}
            </ul>

            <a
              href={ANDROID_APK}
              className="flex items-center justify-center gap-2 w-full py-3 px-6 rounded-xl bg-[var(--land-accent)] text-white font-semibold text-sm hover:opacity-90 transition-opacity"
            >
              <DownloadIcon size={16} />
              Download App (ZIP)
            </a>
          </div>

          {/* Windows Card */}
          <div className="group relative rounded-2xl border border-[var(--land-border)] bg-[var(--land-bg-alt)] p-8 hover:border-[var(--land-accent-border)] transition-all duration-300 overflow-hidden">
            <div className="absolute inset-0 bg-gradient-to-br from-purple-500/5 to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300 pointer-events-none" />

            <div className="flex items-start justify-between mb-6">
              <div className="flex items-center gap-3">
                <div className="w-12 h-12 rounded-xl bg-purple-500/10 flex items-center justify-center">
                  <Monitor className="text-purple-400" size={24} />
                </div>
                <div>
                  <h3 className="text-lg font-bold text-[var(--land-text)]">Windows</h3>
                  <span className="text-xs text-[var(--land-text-muted)]">Windows 10 / 11 (Native WPF)</span>
                </div>
              </div>
              <a
                href={WINDOWS_REPO}
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center gap-1 text-xs text-[var(--land-text-muted)] hover:text-purple-400 transition-colors"
              >
                <Github size={14} />
                Source
              </a>
            </div>

            <ul className="space-y-2 mb-8 text-sm text-[var(--land-text-muted)]">
              {['Truly native WPF / C# app (.exe)', 'No browser or Electron overhead', 'Pdfium C++ rendering engine', 'Left-nav rail — Xodo-style UI', 'Native Windows file picker & dialogs'].map(f => (
                <li key={f} className="flex items-center gap-2">
                  <span className="w-1.5 h-1.5 rounded-full bg-purple-400 flex-shrink-0" />
                  {f}
                </li>
              ))}
            </ul>

            <a
              href={WINDOWS_EXE}
              className="flex items-center justify-center gap-2 w-full py-3 px-6 rounded-xl bg-purple-600 text-white font-semibold text-sm hover:opacity-90 transition-opacity"
            >
              <DownloadIcon size={16} />
              Download for Windows (ZIP)
            </a>
          </div>
        </div>

        {/* Web App CTA */}
        <div className="rounded-2xl border border-[var(--land-border)] bg-[var(--land-bg-alt)] p-6 flex flex-col sm:flex-row items-center justify-between gap-6">
          <div>
            <h4 className="text-base font-semibold text-[var(--land-text)] mb-1">Just want to try it?</h4>
            <p className="text-sm text-[var(--land-text-muted)]">
              Open the progressive web app instantly in your browser — no install required.
            </p>
          </div>
          <div className="flex items-center gap-3 flex-shrink-0">
            <a
              href={WEB_REPO}
              target="_blank"
              rel="noopener noreferrer"
              className="flex items-center gap-2 px-4 py-2.5 rounded-lg border border-[var(--land-border)] text-sm text-[var(--land-text-muted)] hover:text-[var(--land-accent)] hover:border-[var(--land-accent-border)] transition-colors"
            >
              <Github size={14} />
              Web Repo
            </a>
            <Link
              href="/app"
              className="flex items-center gap-2 px-4 py-2.5 rounded-lg bg-[var(--land-accent)]/10 text-sm text-[var(--land-accent)] font-semibold hover:bg-[var(--land-accent)]/20 transition-colors"
            >
              <ExternalLink size={14} />
              Open Web App
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
}
