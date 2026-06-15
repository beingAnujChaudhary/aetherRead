'use client';

import { BookOpen, Flame, Target, Clock } from 'lucide-react';

interface ReadingStatsProps {
  totalDocuments: number;
  totalPagesRead: number;
  streakDays?: number;
}

export default function ReadingStats({ totalDocuments, totalPagesRead, streakDays = 0 }: ReadingStatsProps) {
  const avgWpm = 250;
  const estimatedHours = Math.round((totalPagesRead * 300) / avgWpm / 60);

  const STATS = [
    {
      icon: BookOpen,
      value: totalDocuments,
      label: 'Documents',
      color: 'text-aether-400',
      bg: 'bg-aether-500/10',
    },
    {
      icon: Target,
      value: totalPagesRead,
      label: 'Pages Read',
      color: 'text-green-400',
      bg: 'bg-green-500/10',
    },
    {
      icon: Flame,
      value: streakDays,
      label: 'Day Streak',
      color: 'text-orange-400',
      bg: 'bg-orange-500/10',
    },
    {
      icon: Clock,
      value: `${estimatedHours}h`,
      label: 'Reading Time',
      color: 'text-purple-400',
      bg: 'bg-purple-500/10',
    },
  ];

  return (
    <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
      {STATS.map(stat => {
        const Icon = stat.icon;
        return (
          <div key={stat.label} className="glass rounded-xl p-4 border border-white/5">
            <div className="flex items-center gap-3 mb-2">
              <div className={`w-8 h-8 rounded-lg ${stat.bg} flex items-center justify-center`}>
                <Icon size={15} className={stat.color} />
              </div>
            </div>
            <div className="text-2xl font-black text-white">{stat.value}</div>
            <div className="text-xs text-[var(--color-text-muted)] mt-0.5">{stat.label}</div>
          </div>
        );
      })}
    </div>
  );
}
