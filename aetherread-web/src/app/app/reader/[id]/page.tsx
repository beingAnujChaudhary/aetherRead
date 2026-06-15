/**
 * Server component shell for the PDF reader page.
 *
 * generateStaticParams returns [] because document IDs are created
 * client-side in IndexedDB — there are no IDs known at build time.
 * dynamicParams = false signals Next.js to export this route as a
 * static shell; routing and data loading happen entirely in ReaderClient.
 */
import ReaderClient from './ReaderClient';

export function generateStaticParams() {
  return [{ id: '_placeholder' }];
}

interface ReaderPageProps {
  params: { id: string };
}

export default function ReaderPage({ params }: ReaderPageProps) {
  return <ReaderClient />;
}
