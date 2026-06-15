import Dexie, { Table } from 'dexie';

// ─── Types ───────────────────────────────────────────────────────────────────

export type AnnotationCategory = 'important' | 'definition' | 'question' | 'revision' | 'quote';

export type ComfortTheme =
  | 'dark-abyss'
  | 'book-paper'
  | 'sepia-sands'
  | 'focus-punch'
  | 'monochrome'
  | 'garden-sage';

export interface Document {
  id: string;
  title: string;
  author?: string;
  pageCount: number;
  fileSize: number;
  fileHash: string;
  fileData: ArrayBuffer;   // stored as blob in IndexedDB
  coverPage?: string;      // base64 thumbnail of page 1
  uploadedAt: string;      // ISO
  lastOpenedAt?: string;   // ISO
  totalPagesRead?: number;
}

export interface Annotation {
  id: string;
  documentId: string;
  pageNumber: number;
  category: AnnotationCategory;
  note: string;
  createdAt: string;  // ISO
  updatedAt: string;  // ISO
  isDeleted: boolean;
}

export interface ReadingState {
  id: string;              // `${documentId}`
  documentId: string;
  currentPage: number;
  activeTheme: ComfortTheme;
  zoomLevel: number;
  scrollPosition: number;
  updatedAt: string;       // ISO
}

export interface ReadingSession {
  id: string;
  documentId: string;
  startTime: string;   // ISO
  endTime?: string;    // ISO
  pagesRead: number;
  averageWpm?: number;
  deviceType: 'web' | 'android';
}

// ─── Dexie Database ──────────────────────────────────────────────────────────

class aetherReadDB extends Dexie {
  documents!: Table<Document>;
  annotations!: Table<Annotation>;
  readingStates!: Table<ReadingState>;
  readingSessions!: Table<ReadingSession>;

  constructor() {
    super('aetherread');

    this.version(1).stores({
      documents:      'id, title, pageCount, fileHash, uploadedAt, lastOpenedAt',
      annotations:    'id, documentId, pageNumber, category, createdAt, isDeleted',
      readingStates:  'id, documentId, updatedAt',
      readingSessions:'id, documentId, startTime, deviceType',
    });
  }
}

export const db = new aetherReadDB();

// ─── Document Helpers ────────────────────────────────────────────────────────

export async function addDocument(
  file: File,
  pageCount: number,
  coverPage?: string,
): Promise<Document> {
  const fileData = await file.arrayBuffer();
  const hashBuffer = await crypto.subtle.digest('SHA-256', fileData);
  const hashArray = Array.from(new Uint8Array(hashBuffer));
  const fileHash = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');

  // Check for duplicate
  const existing = await db.documents.where('fileHash').equals(fileHash).first();
  if (existing) return existing;

  const doc: Document = {
    id: crypto.randomUUID(),
    title: file.name.replace(/\.pdf$/i, ''),
    pageCount,
    fileSize: file.size,
    fileHash,
    fileData,
    coverPage,
    uploadedAt: new Date().toISOString(),
    totalPagesRead: 0,
  };

  await db.documents.add(doc);
  return doc;
}

export async function getAllDocuments(): Promise<Document[]> {
  return db.documents.orderBy('lastOpenedAt').reverse().toArray();
}

export async function updateLastOpened(documentId: string) {
  await db.documents.update(documentId, { lastOpenedAt: new Date().toISOString() });
}

// ─── Reading State Helpers ───────────────────────────────────────────────────

export async function getReadingState(documentId: string): Promise<ReadingState | undefined> {
  return db.readingStates.get(documentId);
}

export async function saveReadingState(
  documentId: string,
  updates: Partial<Omit<ReadingState, 'id' | 'documentId'>>,
) {
  const existing = await db.readingStates.get(documentId);
  if (existing) {
    await db.readingStates.update(documentId, { ...updates, updatedAt: new Date().toISOString() });
  } else {
    await db.readingStates.add({
      id: documentId,
      documentId,
      currentPage: 1,
      activeTheme: 'dark-abyss',
      zoomLevel: 1,
      scrollPosition: 0,
      ...updates,
      updatedAt: new Date().toISOString(),
    });
  }
}

// ─── Annotation Helpers ──────────────────────────────────────────────────────

export async function getAnnotationsForDocument(documentId: string): Promise<Annotation[]> {
  return db.annotations
    .where('documentId')
    .equals(documentId)
    .filter(a => !a.isDeleted)
    .sortBy('pageNumber');
}

export async function addAnnotation(
  documentId: string,
  pageNumber: number,
  category: AnnotationCategory,
  note: string,
): Promise<Annotation> {
  const annotation: Annotation = {
    id: crypto.randomUUID(),
    documentId,
    pageNumber,
    category,
    note,
    createdAt: new Date().toISOString(),
    updatedAt: new Date().toISOString(),
    isDeleted: false,
  };
  await db.annotations.add(annotation);
  return annotation;
}

export async function updateAnnotation(id: string, note: string, category: AnnotationCategory) {
  await db.annotations.update(id, {
    note,
    category,
    updatedAt: new Date().toISOString(),
  });
}

export async function softDeleteAnnotation(id: string) {
  await db.annotations.update(id, {
    isDeleted: true,
    updatedAt: new Date().toISOString(),
  });
}
