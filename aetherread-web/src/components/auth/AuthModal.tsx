'use client';

import { useState } from 'react';
import { useAuth } from '@/contexts/AuthContext';
import { BookOpen, X, Mail, Lock, User, AlertCircle, Loader2 } from 'lucide-react';

type Mode = 'signin' | 'signup';

interface AuthModalProps {
  onClose: () => void;
}

export default function AuthModal({ onClose }: AuthModalProps) {
  const { signInWithGoogle, signInWithEmail, signUpWithEmail, error, clearError, loading } = useAuth();
  const [mode, setMode] = useState<Mode>('signin');
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [localLoading, setLocalLoading] = useState(false);

  const isLoading = loading || localLoading;

  const handleGoogle = async () => {
    setLocalLoading(true);
    await signInWithGoogle();
    setLocalLoading(false);
    if (!error) onClose();
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    clearError();
    setLocalLoading(true);
    if (mode === 'signin') {
      await signInWithEmail(email, password);
    } else {
      await signUpWithEmail(name, email, password);
    }
    setLocalLoading(false);
    // Close only if no error was set
    // (error state checked after async — parent will re-render if user is set)
  };

  const switchMode = () => {
    clearError();
    setMode(m => m === 'signin' ? 'signup' : 'signin');
  };

  return (
    <div className="fixed inset-0 z-[200] flex items-center justify-center p-4">
      {/* Backdrop */}
      <div
        className="absolute inset-0 bg-black/60 backdrop-blur-sm"
        onClick={onClose}
      />

      {/* Modal */}
      <div className="relative w-full max-w-md bg-[var(--color-surface)] border border-[var(--color-border)] rounded-2xl shadow-2xl shadow-black/40 p-8 animate-slide-up">
        {/* Close */}
        <button
          onClick={onClose}
          className="absolute top-4 right-4 p-2 rounded-lg text-[var(--color-text-muted)] hover:text-white hover:bg-white/5 transition-colors"
          aria-label="Close"
        >
          <X size={18} />
        </button>

        {/* Logo */}
        <div className="flex items-center gap-2.5 mb-6">
          <div className="w-9 h-9 rounded-xl bg-aether-500 flex items-center justify-center shadow-lg shadow-aether-500/30">
            <BookOpen size={18} className="text-white" />
          </div>
          <span className="font-bold text-lg">
            <span className="gradient-text">Aether</span>
            <span className="text-white">Read</span>
          </span>
        </div>

        <h2 className="text-xl font-black text-white mb-1">
          {mode === 'signin' ? 'Welcome back' : 'Create an account'}
        </h2>
        <p className="text-sm text-[var(--color-text-muted)] mb-6">
          {mode === 'signin'
            ? 'Sign in to sync your library across devices.'
            : 'Sign up to save your library and reading progress.'}
        </p>

        {/* Google Sign In */}
        <button
          id="auth-google-btn"
          onClick={handleGoogle}
          disabled={isLoading}
          className="w-full flex items-center justify-center gap-3 px-4 py-3 rounded-xl border border-[var(--color-border)] bg-white/5 hover:bg-white/10 text-white text-sm font-medium transition-colors mb-4 disabled:opacity-50"
        >
          {/* Google logo SVG */}
          <svg width="18" height="18" viewBox="0 0 48 48" fill="none">
            <path d="M47.5 24.5c0-1.6-.1-3.2-.4-4.7H24v8.9h13.1c-.6 3-2.3 5.5-4.9 7.2v6h7.9c4.6-4.3 7.4-10.6 7.4-17.4z" fill="#4285F4"/>
            <path d="M24 48c6.5 0 11.9-2.1 15.9-5.8l-7.9-6c-2.2 1.5-5 2.3-8 2.3-6.1 0-11.3-4.1-13.2-9.7H2.7v6.2C6.7 42.7 14.8 48 24 48z" fill="#34A853"/>
            <path d="M10.8 28.8A14.4 14.4 0 0 1 9.5 24c0-1.7.3-3.3.8-4.8v-6.2H2.7A24 24 0 0 0 0 24c0 3.9.9 7.5 2.7 10.8l8.1-6z" fill="#FBBC05"/>
            <path d="M24 9.5c3.5 0 6.6 1.2 9 3.5l6.8-6.8C35.9 2.1 30.5 0 24 0 14.8 0 6.7 5.3 2.7 13.2l8.1 6.2c1.9-5.6 7.1-9.9 13.2-9.9z" fill="#EA4335"/>
          </svg>
          Continue with Google
        </button>

        {/* Divider */}
        <div className="flex items-center gap-3 mb-4">
          <div className="flex-1 h-px bg-[var(--color-border)]" />
          <span className="text-xs text-[var(--color-text-muted)]">or</span>
          <div className="flex-1 h-px bg-[var(--color-border)]" />
        </div>

        {/* Error */}
        {error && (
          <div className="flex items-center gap-2 px-4 py-3 rounded-xl bg-red-500/10 border border-red-500/20 text-sm text-red-400 mb-4">
            <AlertCircle size={14} className="flex-shrink-0" />
            {error}
          </div>
        )}

        {/* Form */}
        <form onSubmit={handleSubmit} className="space-y-3">
          {mode === 'signup' && (
            <div className="relative">
              <User size={15} className="absolute left-3.5 top-1/2 -translate-y-1/2 text-[var(--color-text-muted)]" />
              <input
                id="auth-name"
                type="text"
                placeholder="Full name"
                value={name}
                onChange={e => setName(e.target.value)}
                required
                className="w-full pl-10 pr-4 py-3 rounded-xl glass-light border border-[var(--color-border)] text-sm text-white placeholder:text-[var(--color-text-muted)] focus:border-aether-500/50 outline-none transition-colors"
              />
            </div>
          )}
          <div className="relative">
            <Mail size={15} className="absolute left-3.5 top-1/2 -translate-y-1/2 text-[var(--color-text-muted)]" />
            <input
              id="auth-email"
              type="email"
              placeholder="Email address"
              value={email}
              onChange={e => setEmail(e.target.value)}
              required
              className="w-full pl-10 pr-4 py-3 rounded-xl glass-light border border-[var(--color-border)] text-sm text-white placeholder:text-[var(--color-text-muted)] focus:border-aether-500/50 outline-none transition-colors"
            />
          </div>
          <div className="relative">
            <Lock size={15} className="absolute left-3.5 top-1/2 -translate-y-1/2 text-[var(--color-text-muted)]" />
            <input
              id="auth-password"
              type="password"
              placeholder={mode === 'signup' ? 'Password (min. 6 characters)' : 'Password'}
              value={password}
              onChange={e => setPassword(e.target.value)}
              required
              minLength={mode === 'signup' ? 6 : 1}
              className="w-full pl-10 pr-4 py-3 rounded-xl glass-light border border-[var(--color-border)] text-sm text-white placeholder:text-[var(--color-text-muted)] focus:border-aether-500/50 outline-none transition-colors"
            />
          </div>

          <button
            id="auth-submit-btn"
            type="submit"
            disabled={isLoading}
            className="w-full py-3 rounded-xl bg-aether-500 hover:bg-aether-400 text-white font-bold text-sm transition-colors shadow-lg shadow-aether-500/25 disabled:opacity-50 flex items-center justify-center gap-2"
          >
            {isLoading && <Loader2 size={15} className="animate-spin" />}
            {mode === 'signin' ? 'Sign In' : 'Create Account'}
          </button>
        </form>

        {/* Switch mode */}
        <p className="text-sm text-center text-[var(--color-text-muted)] mt-5">
          {mode === 'signin' ? "Don't have an account?" : 'Already have an account?'}
          {' '}
          <button
            id="auth-switch-mode"
            onClick={switchMode}
            className="text-aether-400 hover:text-aether-300 font-medium transition-colors"
          >
            {mode === 'signin' ? 'Sign up' : 'Sign in'}
          </button>
        </p>
      </div>
    </div>
  );
}
