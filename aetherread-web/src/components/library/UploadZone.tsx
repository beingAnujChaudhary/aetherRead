'use client';

import { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';
import { Upload, FileText, Loader2, AlertCircle } from 'lucide-react';
import { useLibraryStore } from '@/stores/useLibraryStore';
import { cn } from '@/lib/utils';
import { useRouter } from 'next/navigation';

export default function UploadZone() {
  const { uploadDocument, isUploading, uploadProgress } = useLibraryStore();
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  const onDrop = useCallback(async (acceptedFiles: File[]) => {
    setError(null);
    if (acceptedFiles.length === 0) return;
    const file = acceptedFiles[0];
    if (!file.name.toLowerCase().endsWith('.pdf')) {
      setError('Only PDF files are supported.');
      return;
    }
    const result = await uploadDocument(file);
    if (!result) {
      setError('Upload failed. Please try again.');
    } else {
      router.push(`/app/reader?id=${result.id}`);
    }
  }, [uploadDocument, router]);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: { 'application/pdf': ['.pdf'] },
    multiple: false,
    disabled: isUploading,
  });

  return (
    <div className="mb-8">
      <div
        id="upload-zone"
        {...getRootProps()}
        className={cn(
          'relative border-2 border-dashed rounded-2xl p-10 text-center cursor-pointer transition-all duration-300',
          isDragActive
            ? 'border-aether-400 bg-aether-500/10 scale-[1.01]'
            : 'border-[var(--color-border)] hover:border-aether-500/50 hover:bg-aether-500/5',
          isUploading && 'pointer-events-none',
        )}
      >
        <input {...getInputProps()} id="upload-file-input" />

        {isUploading ? (
          <div className="flex flex-col items-center gap-4">
            <Loader2 size={40} className="text-aether-400 animate-spin" />
            <div className="w-full max-w-xs">
              <div className="flex justify-between text-sm text-[var(--color-text-muted)] mb-2">
                <span>Importing PDF...</span>
                <span className="text-aether-400">{uploadProgress}%</span>
              </div>
              <div className="h-2 rounded-full bg-[var(--color-surface-muted)] overflow-hidden">
                <div
                  className="h-full rounded-full bg-gradient-to-r from-aether-500 to-purple-500 transition-all duration-300"
                  style={{ width: `${uploadProgress}%` }}
                />
              </div>
            </div>
          </div>
        ) : (
          <div className="flex flex-col items-center gap-3">
            <div className={cn(
              'w-16 h-16 rounded-2xl flex items-center justify-center transition-all duration-300',
              isDragActive ? 'bg-aether-500/20 scale-110' : 'bg-[var(--color-surface-muted)]',
            )}>
              {isDragActive
                ? <FileText size={28} className="text-aether-400" />
                : <Upload size={28} className="text-[var(--color-text-muted)]" />
              }
            </div>
            <div>
              <p className="text-white font-semibold mb-1">
                {isDragActive ? 'Drop your PDF here' : 'Upload a PDF'}
              </p>
              <p className="text-sm text-[var(--color-text-muted)]">
                Drag & drop or click to select · Stored offline in your browser
              </p>
            </div>
          </div>
        )}
      </div>

      {error && (
        <div className="mt-3 flex items-center gap-2 px-4 py-3 rounded-xl bg-red-500/10 border border-red-500/20 text-sm text-red-400">
          <AlertCircle size={14} />
          {error}
        </div>
      )}
    </div>
  );
}
