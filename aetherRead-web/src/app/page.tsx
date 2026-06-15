import type { Metadata } from 'next';
import Navbar from '@/components/landing/Navbar';
import Hero from '@/components/landing/Hero';
import Features from '@/components/landing/Features';
import AndroidFeatures from '@/components/landing/AndroidFeatures';
import Download from '@/components/landing/Download';
import ThemeShowcase from '@/components/landing/ThemeShowcase';
import TechStack from '@/components/landing/TechStack';
import Roadmap from '@/components/landing/Roadmap';
import Footer from '@/components/landing/Footer';

export const metadata: Metadata = {
  title: 'aetherRead — Smart Reading & Research Platform',
  description:
    'An AI-powered, offline-first PDF reader with local AI brain, 5 reading themes, cross-platform sync, and page-level annotations. Built for students, researchers and academics.',
};

export default function LandingPage() {
  return (
    <main>
      <Navbar />
      <Hero />
      <Features />
      <AndroidFeatures />
      <Download />
      <ThemeShowcase />
      <TechStack />
      <Roadmap />
      <Footer />
    </main>
  );
}
