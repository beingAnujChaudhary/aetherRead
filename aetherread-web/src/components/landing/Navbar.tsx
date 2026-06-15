'use client';

import Link from 'next/link';
import { useState, useEffect } from 'react';
import { BookOpen, Menu, X, Zap, ArrowLeft } from 'lucide-react';

const NAV_LINKS = [
  { label: 'Features', href: '#features' },
  { label: 'Themes', href: '#themes' },
  { label: 'Tech Stack', href: '#tech' },
  { label: 'Roadmap', href: '#roadmap' },
];

export default function Navbar() {
  const [scrolled, setScrolled] = useState(false);
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => {
    const handleScroll = () => setScrolled(window.scrollY > 20);
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  // Lock body scroll when mobile menu open
  useEffect(() => {
    document.body.style.overflow = menuOpen ? 'hidden' : '';
    return () => { document.body.style.overflow = ''; };
  }, [menuOpen]);

  return (
    <nav
      className={`fixed top-0 left-0 right-0 z-50 transition-all duration-300 ${
        scrolled ? 'land-glass shadow-sm shadow-[var(--land-shadow)]' : 'bg-transparent'
      }`}
    >
      <div className="max-w-7xl mx-auto px-6 h-16 flex items-center justify-between">
        {/* Logo */}
        <Link href="/" className="flex items-center gap-2.5 group">
          <div className="w-8 h-8 rounded-lg bg-[var(--land-accent)] flex items-center justify-center shadow-md shadow-[var(--land-shadow-accent)] group-hover:shadow-lg transition-shadow">
            <BookOpen size={16} className="text-white" />
          </div>
          <span className="font-bold text-lg tracking-tight text-[var(--land-text)]">
            <span className="gradient-text-land">aether</span>
            <span>Read</span>
          </span>
          <span className="hidden sm:flex items-center gap-1 px-2 py-0.5 rounded-full bg-[var(--land-accent-light)] border border-[var(--land-accent-border)] text-[var(--land-accent)] text-xs font-medium">
            <Zap size={10} />
            Beta
          </span>
        </Link>

        {/* Desktop nav — portfolio pill style */}
        <div className="hidden md:flex items-center gap-2">
          {NAV_LINKS.map(link => (
            <a key={link.label} href={link.href} className="nav-pill">
              {link.label}
            </a>
          ))}
        </div>

        {/* CTA */}
        <div className="hidden md:flex items-center gap-3">
          <a
            href="https://beinganujchaudhary.web.app/"
            target="_blank"
            rel="noopener noreferrer"
            className="flex items-center gap-1.5 px-4 py-2 rounded-full text-sm font-medium text-[var(--land-text-muted)] hover:text-[var(--land-text)] border border-[var(--land-border)] hover:border-[var(--land-accent-border)] transition-colors"
          >
            <ArrowLeft size={13} />
            Portfolio
          </a>
          <Link href="/app" className="btn-accent text-sm px-5 py-2.5">
            Try It Free →
          </Link>
        </div>

        {/* Mobile menu toggle */}
        <button
          id="mobile-menu-toggle"
          onClick={() => setMenuOpen(v => !v)}
          className="md:hidden px-4 py-2 border border-[var(--land-border)] rounded-full text-sm font-medium text-[var(--land-text-muted)] hover:text-[var(--land-text)] transition-colors"
          aria-label="Toggle menu"
          aria-expanded={menuOpen}
        >
          {menuOpen ? <X size={18} /> : <Menu size={18} />}
        </button>
      </div>

      {/* Mobile menu — portfolio style slide-down sheet */}
      {menuOpen && (
        <div className="md:hidden border-t border-[var(--land-border)] bg-[var(--land-bg)] px-6 py-6 space-y-3">
          {NAV_LINKS.map(link => (
            <a
              key={link.label}
              href={link.href}
              onClick={() => setMenuOpen(false)}
              className="block py-2.5 text-base font-medium text-[var(--land-text-muted)] hover:text-[var(--land-accent)] transition-colors border-b border-[var(--land-border-soft)] last:border-0"
            >
              {link.label}
            </a>
          ))}
          <div className="pt-3 flex flex-col gap-3">
            <a
              href="https://beinganujchaudhary.web.app/"
              target="_blank"
              rel="noopener noreferrer"
              className="block py-2 text-sm text-center text-[var(--land-text-muted)] border border-[var(--land-border)] rounded-full hover:border-[var(--land-accent-border)] transition-colors"
            >
              ← Portfolio
            </a>
            <Link
              href="/app"
              className="btn-accent text-sm justify-center"
              onClick={() => setMenuOpen(false)}
            >
              Try It Free →
            </Link>
          </div>
        </div>
      )}
    </nav>
  );
}
