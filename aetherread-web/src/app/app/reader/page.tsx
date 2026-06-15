/**
 * Server component shell for the PDF reader page.
 *
 * It uses search params (?id=...) instead of dynamic route params ([id])
 * to support Next.js static exports without requiring server-side rewrite rules.
 */
import ReaderClient from './ReaderClient';
import { Suspense } from 'react';

export default function ReaderPage() {
  return (
    <Suspense fallback={<div className="min-h-screen bg-[var(--color-bg)]"></div>}>
      <ReaderClient />
    </Suspense>
  );
}
