'use client';

import Link from 'next/link';
import { ArrowRight, BookOpen, Brain, Wifi, Shield } from 'lucide-react';

const STATS = [
  { value: '100%', label: 'Offline Capable' },
  { value: '5', label: 'Reading Themes' },
  { value: 'RAG', label: 'Local AI Engine' },
  { value: '0', label: 'Data Sent to Cloud' },
];

const BADGES = [
  { icon: BookOpen, text: 'PDF Reader' },
  { icon: Brain, text: 'Local AI Brain' },
  { icon: Wifi, text: 'Offline First' },
  { icon: Shield, text: 'Privacy First' },
];

export default function Hero() {
  return (
    <section className="relative min-h-screen flex flex-col items-center justify-center overflow-hidden px-6 pt-20">
      {/* Background glow spheres */}
      <div
        className="hero-sphere w-[600px] h-[600px] -top-40 left-1/2 -translate-x-1/2 opacity-25"
        style={{ background: 'radial-gradient(circle, #6C63FF 0%, transparent 70%)' }}
      />
      <div
        className="hero-sphere w-[300px] h-[300px] top-1/3 -left-20 opacity-15"
        style={{ background: 'radial-gradient(circle, #a78bfa 0%, transparent 70%)' }}
      />
      <div
        className="hero-sphere w-[250px] h-[250px] bottom-1/4 -right-10 opacity-10"
        style={{ background: 'radial-gradient(circle, #f472b6 0%, transparent 70%)' }}
      />

      <div className="relative z-10 max-w-5xl mx-auto text-center">
        {/* Announcement pill */}
        <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full glass border border-aether-500/30 text-sm text-aether-300 mb-8 animate-slide-up">
          <span className="w-2 h-2 rounded-full bg-green-400 animate-pulse" />
          Portfolio project by{' '}
          <a
            href="https://beinganujchaudhary.web.app"
            target="_blank"
            rel="noopener noreferrer"
            className="font-semibold text-white hover:text-aether-300 transition-colors underline underline-offset-2"
          >
            Anuj Chaudhary
          </a>
          &nbsp;· Phase 2: Web MVP
        </div>

        {/* Headline */}
        <h1 className="text-5xl md:text-7xl font-black tracking-tight leading-none mb-6 animate-slide-up delay-100">
          <span className="text-white">Read smarter.</span>
          <br />
          <span className="gradient-text text-glow">Think deeper.</span>
          <br />
          <span className="text-white">Stay offline.</span>
        </h1>

        {/* Sub-headline */}
        <p className="text-lg md:text-xl text-[var(--color-text-muted)] max-w-2xl mx-auto mb-10 leading-relaxed animate-slide-up delay-200">
          AetherRead is a production-grade PDF reader with a local AI brain, distraction-free
          reading themes, and real-time cross-platform sync — built for students, researchers,
          and academics who demand privacy.
        </p>

        {/* CTA buttons */}
        <div className="flex flex-col sm:flex-row items-center justify-center gap-4 mb-16 animate-slide-up delay-300">
          <Link
            id="hero-open-app"
            href="/app"
            className="group flex items-center gap-2 px-8 py-4 rounded-xl font-bold text-white bg-aether-500 hover:bg-aether-400 transition-all duration-300 shadow-2xl shadow-aether-500/30 hover:shadow-aether-500/50 glow-aether"
          >
            Try It Free
            <ArrowRight size={18} className="group-hover:translate-x-1 transition-transform" />
          </Link>
          <a
            id="hero-portfolio"
            href="https://beinganujchaudhary.web.app/projects/AetherRead.html"
            target="_blank"
            rel="noopener noreferrer"
            className="flex items-center gap-2 px-8 py-4 rounded-xl font-semibold text-[var(--color-text-muted)] hover:text-white border border-[var(--color-border)] hover:border-aether-500/50 transition-all duration-300 hover:bg-aether-500/5"
          >
            View Project Page
          </a>
        </div>

        {/* Feature badges */}
        <div className="flex flex-wrap items-center justify-center gap-3 mb-16 animate-slide-up delay-400">
          {BADGES.map(({ icon: Icon, text }) => (
            <div
              key={text}
              className="flex items-center gap-2 px-4 py-2 rounded-full glass-light text-sm text-[var(--color-text-muted)]"
            >
              <Icon size={14} className="text-aether-400" />
              {text}
            </div>
          ))}
        </div>

        {/* Stats */}
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4 max-w-3xl mx-auto animate-slide-up delay-500">
          {STATS.map(stat => (
            <div
              key={stat.label}
              className="glass rounded-2xl p-6 border border-white/5 hover:border-aether-500/30 transition-all duration-300"
            >
              <div className="text-3xl font-black gradient-text mb-1">{stat.value}</div>
              <div className="text-xs text-[var(--color-text-muted)] uppercase tracking-wider">
                {stat.label}
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Scroll indicator */}
      <div className="absolute bottom-10 left-1/2 -translate-x-1/2 flex flex-col items-center gap-2 text-[var(--color-text-muted)] animate-float">
        <span className="text-xs uppercase tracking-widest">Scroll</span>
        <div className="w-px h-8 bg-gradient-to-b from-[var(--color-text-muted)] to-transparent" />
      </div>
    </section>
  );
}
