'use client';

import Link from 'next/link';
import { BookOpen, Github, ExternalLink, Heart, Linkedin, Mail } from 'lucide-react';

export default function Footer() {
  return (
    <footer className="border-t border-[var(--land-border)] py-16 px-6 bg-[var(--land-bg-alt)]">
      <div className="max-w-7xl mx-auto">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-10 mb-12">
          {/* Brand */}
          <div className="md:col-span-2">
            <div className="flex items-center gap-2.5 mb-4">
              <div className="w-8 h-8 rounded-lg bg-[var(--land-accent)] flex items-center justify-center">
                <BookOpen size={16} className="text-white" />
              </div>
              <span className="font-bold text-lg text-[var(--land-text)]">
                <span className="gradient-text-land">Aether</span>
                Read
              </span>
            </div>
            <p className="text-sm text-[var(--land-text-muted)] leading-relaxed max-w-xs">
              A production-grade, offline-first PDF reader and AI research workspace. Built by{' '}
              <a
                href="https://beinganujchaudhary.web.app"
                target="_blank"
                rel="noopener noreferrer"
                className="text-[var(--land-accent)] hover:underline transition-colors font-medium"
              >
                beingAnujChaudhary
              </a>
              , BS in Data Science &amp; Applications at IIT Madras.
            </p>
            <div className="flex flex-wrap items-center gap-2 mt-6">
              {[
                { href: 'https://github.com/beinganujchaudhary', icon: Github, label: 'GitHub' },
                { href: 'https://www.linkedin.com/in/beinganujchaudhary/', icon: Linkedin, label: 'LinkedIn' },
                { href: 'mailto:beinganujchaudhary@gmail.com', icon: Mail, label: 'Email' },
                { href: 'https://beinganujchaudhary.web.app', icon: ExternalLink, label: 'Portfolio' },
              ].map(({ href, icon: Icon, label }) => (
                <a
                  key={label}
                  href={href}
                  target={href.startsWith('mailto') ? undefined : '_blank'}
                  rel={href.startsWith('mailto') ? undefined : 'noopener noreferrer'}
                  className="flex items-center gap-2 px-3 py-2 rounded-lg border border-[var(--land-border)] bg-white text-sm text-[var(--land-text-muted)] hover:text-[var(--land-accent)] hover:border-[var(--land-accent-border)] transition-colors"
                >
                  <Icon size={13} />
                  {label}
                </a>
              ))}
            </div>
          </div>

          {/* Product links */}
          <div>
            <h4 className="text-sm font-semibold text-[var(--land-text)] mb-4 uppercase tracking-wider">Product</h4>
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
                    className="text-sm text-[var(--land-text-muted)] hover:text-[var(--land-accent)] transition-colors"
                  >
                    {item.label}
                  </a>
                </li>
              ))}
            </ul>
          </div>

          {/* Tech links */}
          <div>
            <h4 className="text-sm font-semibold text-[var(--land-text)] mb-4 uppercase tracking-wider">Tech</h4>
            <ul className="space-y-3">
              {[
                { label: 'Web Repo (MIT)', href: 'https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-web' },
                { label: 'Android Repo (MIT)', href: 'https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-android' },
                { label: 'Project Page', href: 'https://beinganujchaudhary.web.app/projects/aetherRead.html' },
              ].map(item => (
                <li key={item.label}>
                  <a
                    href={item.href}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="text-sm text-[var(--land-text-muted)] hover:text-[var(--land-accent)] transition-colors flex items-center gap-1.5"
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
        <div className="pt-8 border-t border-[var(--land-border)] flex flex-col sm:flex-row items-center justify-between gap-4">
          <p className="text-xs text-[var(--land-text-soft)]">
            © 2026 aetherRead. Web &amp; Android: MIT / AGPL-3.0. API: Proprietary.
          </p>
          <p className="text-xs text-[var(--land-text-soft)] flex items-center gap-1.5">
            Built with <Heart size={10} className="text-[var(--land-accent)]" /> by{' '}
            <a
              href="https://beinganujchaudhary.web.app"
              target="_blank"
              rel="noopener noreferrer"
              className="text-[var(--land-accent)] hover:underline font-medium"
            >
              beingAnujChaudhary
            </a>
          </p>
        </div>
      </div>
    </footer>
  );
}
