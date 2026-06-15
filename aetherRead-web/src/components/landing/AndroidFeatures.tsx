'use client';

import { Smartphone, PenTool, LayoutGrid, Layers, Download as DownloadIcon } from 'lucide-react';

const ANDROID_FEATURES = [
  {
    icon: PenTool,
    title: 'Modern Annotation Pill',
    description: 'Floating bottom pill for quick access to drawing tools: Smart Pen, Highlight, Strikethrough, and Callout.',
    color: 'emerald'
  },
  {
    icon: LayoutGrid,
    title: 'Toolbox Suite',
    description: 'A unified grid for document operations: Scan, Convert, Image to PDF, Merge, and Manage files natively.',
    color: 'emerald'
  },
  {
    icon: Layers,
    title: 'Comfort Themes',
    description: '6 carefully crafted color schemes applied directly to the PDF canvas, ensuring comfortable reading at any time.',
    color: 'emerald'
  }
];

export default function AndroidFeatures() {
  return (
    <section id="android" className="py-24 px-6 bg-[var(--land-bg-alt)] border-y border-[var(--land-border)]">
      <div className="max-w-6xl mx-auto">
        <div className="flex flex-col md:flex-row items-center gap-16">
          {/* Left: Content */}
          <div className="flex-1 space-y-8">
            <div>
              <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full border border-emerald-200 bg-emerald-50 text-sm text-emerald-700 mb-6 font-medium">
                <Smartphone size={14} />
                Native Android App
              </div>
              <h2 className="text-4xl font-bold text-[var(--land-text)] mb-4">
                Desktop-grade tools in your pocket.
              </h2>
              <p className="text-lg text-[var(--land-text-muted)] leading-relaxed">
                We rebuilt the reading experience for Android from the ground up using Kotlin and Jetpack Compose. It brings fluid, memory-safe PDF rendering and powerful annotation tools completely offline.
              </p>
            </div>

            <div className="grid gap-6">
              {ANDROID_FEATURES.map((feature) => (
                <div key={feature.title} className="flex items-start gap-4 p-4 rounded-2xl bg-white border border-[var(--land-border)] shadow-sm">
                  <div className={`w-12 h-12 rounded-xl bg-${feature.color}-50 flex items-center justify-center flex-shrink-0`}>
                    <feature.icon size={20} className={`text-${feature.color}-600`} />
                  </div>
                  <div>
                    <h3 className="font-bold text-[var(--land-text)] text-base mb-1">{feature.title}</h3>
                    <p className="text-sm text-[var(--land-text-muted)] leading-relaxed">{feature.description}</p>
                  </div>
                </div>
              ))}
            </div>

            <div className="pt-4">
               <a
                  href="/projects/aetherRead/downloads/aetherRead-android.zip"
                  className="inline-flex items-center gap-2 px-6 py-3 rounded-xl bg-emerald-600 text-white font-semibold text-sm hover:opacity-90 transition-opacity"
               >
                 <DownloadIcon size={16} />
                 Download App (ZIP)
               </a>
            </div>
          </div>

          {/* Right: Mockups / Visuals (Using CSS representation) */}
          <div className="flex-1 w-full max-w-sm mx-auto">
            <div className="relative w-full aspect-[9/19] rounded-[2.5rem] bg-black border-[8px] border-black shadow-2xl overflow-hidden ring-1 ring-white/10">
              {/* Android Screen Simulation */}
              <div className="absolute inset-0 bg-[#0A0A0A] flex flex-col">
                {/* PDF Header Area */}
                <div className="h-14 bg-[#141414] border-b border-white/10 flex items-center px-4 justify-between">
                   <div className="w-8 h-8 rounded-full bg-white/10" />
                   <div className="w-24 h-4 rounded-md bg-white/20" />
                   <div className="w-8 h-8 rounded-full bg-white/10" />
                </div>
                {/* PDF Content Area */}
                <div className="flex-1 p-6 flex flex-col gap-4 opacity-50">
                   <div className="w-full h-8 bg-white/10 rounded-md" />
                   <div className="w-3/4 h-4 bg-white/10 rounded-md" />
                   <div className="w-5/6 h-4 bg-white/10 rounded-md" />
                   <div className="w-full h-4 bg-white/10 rounded-md" />
                   <div className="w-2/3 h-4 bg-white/10 rounded-md" />
                   <div className="w-full h-32 bg-white/5 rounded-xl mt-4" />
                </div>
                {/* Floating Toolbar */}
                <div className="absolute bottom-20 inset-x-0 flex justify-center">
                   <div className="bg-[#1A1A1A] border border-white/10 shadow-2xl rounded-full px-6 py-3 flex items-center gap-6">
                      <div className="w-6 h-6 rounded-md bg-emerald-500/20 text-emerald-500 flex items-center justify-center">✎</div>
                      <div className="w-6 h-6 rounded-md bg-yellow-500/20 text-yellow-500 flex items-center justify-center">H</div>
                      <div className="w-6 h-6 rounded-md bg-red-500/20 text-red-500 flex items-center justify-center">S</div>
                      <div className="w-[1px] h-6 bg-white/10 mx-2" />
                      <div className="w-6 h-6 rounded-full bg-white/10 flex items-center justify-center text-white/50 text-xs">✕</div>
                   </div>
                </div>
                {/* Bottom Nav */}
                <div className="h-16 bg-[#141414] border-t border-white/10 flex items-center justify-around px-2">
                   <div className="w-8 h-8 rounded-md bg-emerald-500/20" />
                   <div className="w-8 h-8 rounded-md bg-white/10" />
                   <div className="w-8 h-8 rounded-md bg-white/10" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
