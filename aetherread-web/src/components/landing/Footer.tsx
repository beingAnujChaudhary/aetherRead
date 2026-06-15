'use client';

import Link from 'next/link';
import { BookOpen, Github, ExternalLink, Heart, Linkedin, Mail } from 'lucide-react';

export default function Footer() {
  return (
    <footer className="border-t border-[var(--color-border)] py-16 px-6">
      <div className="max-w-7xl mx-auto">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-10 mb-12">
          {/* Brand */}
          <div className="md:col-span-2">
            <div className="flex items-center gap-2.5 mb-4">
              <div className="w-8 h-8 rounded-lg bg-aether-500 flex items-center justify-center">
                <BookOpen size={16} className="text-white" />
              </div>
              <span className="font-bold text-lg">
                <span className="gradient-text">Aether</span>
                <span className="text-white">Read</span>
              </span>
            </div>
            <p className="text-sm text-[var(--color-text-muted)] leading-relaxed max-w-xs">
              A production-grade, offline-first PDF reader and AI research workspace. Built by{' '}
              <a
                href="https://beinganujchaudhary.web.app"
                target="_blank"
                rel="noopener noreferrer"
                className="text-aether-400 hover:text-aether-300 transition-colors"
              >
                Anuj Chaudhary
              </a>
              , BS in Data Science &amp; Applications at IIT Madras.
            </p>
            <div className="flex items-center gap-3 mt-6">
              <a
                href="https://github.com/beinganujchaudhary"
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center gap-2 px-4 py-2 rounded-lg glass-light text-sm text-[var(--color-text-muted)] hover:text-white transition-colors"
              >
                <Github size={14} />
                GitHub
              </a>
              <a
                href="https://www.linkedin.com/in/beinganujchaudhary/"
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center gap-2 px-4 py-2 rounded-lg glass-light text-sm text-[var(--color-text-muted)] hover:text-white transition-colors"
              >
                <Linkedin size={14} />
                LinkedIn
              </a>
              <a
                href="mailto:beinganujchaudhary@gmail.com"
                className="flex items-center gap-2 px-4 py-2 rounded-lg glass-light text-sm text-[var(--color-text-muted)] hover:text-white transition-colors"
              >
                <Mail size={14} />
                Email
              </a>
              <a
                href="https://beinganujchaudhary.web.app"
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center gap-2 px-4 py-2 rounded-lg glass-light text-sm text-[var(--color-text-muted)] hover:text-white transition-colors"
              >
                <ExternalLink size={14} />
                Portfolio
              </a>
            </div>
          </div>

          {/* Product links */}
          <div>
            <h4 className="text-sm font-semibold text-white mb-4 uppercase tracking-wider">Product</h4>
            <ul className="space-y-3">
              {[
                { label: 'Features', href: '#features' },
                { label: 'Reading Themes', href: '#themes' },
                { label: 'Roadmap', href: '#roadmap' },
                { label: 'Open App', href: '/app' },
              ].map(item => (
                <li key={item.label}>
                  <a
                    href={item.href}
                    className="text-sm text-[var(--color-text-muted)] hover:text-white transition-colors"
                  >
                    {item.label}
                  </a>
                </li>
              ))}
            </ul>
          </div>

          {/* Tech links */}
          <div>
            <h4 className="text-sm font-semibold text-white mb-4 uppercase tracking-wider">Tech</h4>
            <ul className="space-y-3">
              {[
                { label: 'Web Repo (MIT)', href: 'https://github.com/beinganujchaudhary/aetherread-web' },
                { label: 'Android Repo (MIT)', href: 'https://github.com/beinganujchaudhary/aetherread-android' },
                { label: 'Project Page', href: 'https://beinganujchaudhary.web.app/projects/AetherRead.html' },
              ].map(item => (
                <li key={item.label}>
                  <a
                    href={item.href}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="text-sm text-[var(--color-text-muted)] hover:text-white transition-colors flex items-center gap-1.5"
                  >
                    {item.label}
                    <ExternalLink size={10} />
                  </a>
                </li>
              ))}
            </ul>
          </div>
        </div>

        {/* Bottom bar */}
        <div className="pt-8 border-t border-[var(--color-border)] flex flex-col sm:flex-row items-center justify-between gap-4">
          <p className="text-xs text-[var(--color-text-muted)]">
            © 2026 AetherRead. Web & Android: MIT / AGPL-3.0. API: Proprietary.
          </p>
          <p className="text-xs text-[var(--color-text-muted)] flex items-center gap-1.5">
            Built with <Heart size={10} className="text-red-400" /> by{' '}
            <a
              href="https://beinganujchaudhary.web.app"
              target="_blank"
              rel="noopener noreferrer"
              className="text-aether-400 hover:text-aether-300 transition-colors"
            >
              Anuj Chaudhary
            </a>
          </p>
        </div>
      </div>
    </footer>
  );
}
