<div align="center">

# aetherRead

**AI-powered, offline-first PDF reader and research workspace**

[![Phase](https://img.shields.io/badge/Phase-4%20Native%20Windows-0078D7?style=flat-square)](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-windows-native)
[![Android](https://img.shields.io/badge/Android-Live-4CAF50?style=flat-square&logo=android)](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-android)
[![Web](https://img.shields.io/badge/Web-Live-FE320A?style=flat-square&logo=nextdotjs)](https://beinganujchaudhary.web.app/projects/aetherRead.html)
[![License Web](https://img.shields.io/badge/License-MIT-green?style=flat-square)](https://github.com/beingAnujChaudhary/aetherRead/blob/main/LICENSE)
[![Portfolio](https://img.shields.io/badge/Portfolio-beingAnujChaudhary-FE320A?style=flat-square)](https://beinganujchaudhary.web.app/)
[![IIT Madras](https://img.shields.io/badge/IIT%20Madras-BS%20Data%20Science-003087?style=flat-square)](https://study.iitm.ac.in)

*Read smarter. Annotate deeply. Stay offline.*

[**Live Demo**](https://beinganujchaudhary.web.app/projects/aetherRead.html) · [**Portfolio**](https://beinganujchaudhary.web.app) · [**Download Android**](https://beinganujchaudhary.web.app/projects/aetherRead/downloads/aetherRead-android.zip) · [**Download Windows**](https://beinganujchaudhary.web.app/projects/aetherRead/downloads/aetherRead-windows.zip)
</div>

---

## About

aetherRead is a production-grade, cross-platform PDF reader and AI research workspace built by [Anuj Chaudhary](https://beinganujchaudhary.web.app/), a student at IIT Madras (BS Data Science & Applications).

The platform lets students, researchers, and academics read, annotate, and interact with documents entirely from their local machine — no internet required, no cloud dependency, no data ever sent anywhere.

> This is a **multi-platform open-core project**:
> | Repo | Stack | Status | License |
> |------|-------|--------|---------|
> | [`aetherRead-web`](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-web) | Next.js 14, TypeScript, Tailwind, Dexie.js | ✅ Live | MIT |
> | [`aetherRead-android`](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-android) | Kotlin, Jetpack Compose, Room DB, Hilt | ✅ Live | AGPL-3.0 |
> | [`aetherRead-windows-native`](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-windows-native) | C#, WPF (.NET 8), PdfiumViewer | ✅ Live | MIT |
> | [`aetherRead-api`](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherRead-api) | FastAPI, Python 3.11, ChromaDB, Ollama | ⚡ In Progress | Proprietary |

---

## Features

### ✅ Web App (`aetherRead-web`)
- **True Offline-First** — All PDFs, annotations, and reading state stored locally via IndexedDB (Dexie.js). Zero backend required for reading.
- **6 Reading Themes (Comfort Engine)** — Dark Abyss, Book Paper, Sepia Sands, Focus Punch, Monochrome, Garden Sage — switch instantly.
- **Page-Level Annotations** — Attach rich notes to any page, categorized as Important, Definition, Question, Revision, or Quote.
- **Reading State Persistence** — Resumes exactly where you left off: page number, theme, scroll position.
- **Duplicate Detection** — SHA-256 file hashing prevents the same PDF being imported twice.
- **PDF Cover Thumbnails** — Auto-generated page-1 preview for the library grid.
- **Search & Sort** — Filter by title, sort by recent / title / progress.
- **Firebase Auth** — Sign in with email/password with full read/write sync.

### ✅ Android App (`aetherRead-android`)
Built from scratch in Kotlin with Jetpack Compose. Mirrors the Xodo design language.

| Screen | Features |
|--------|----------|
| **Files (Library)** | Picked-For-You quick actions (View & Annotate, eSign, Convert to PDF, Merge Pages), tabbed file list (Recent / Favorites / All Files / Processed), FAB import, document cards with metadata |
| **Toolbox** | 7 tools with icon + description: Scan Document, Image to PDF, eSign PDF, Text Recognition (OCR), Convert from PDF, Convert to PDF, Manage PDF; Picked-For-You quick tile grid |
| **My AetherRead (Profile)** | User profile with email, Aether Drive usage, Subscribed badge; team invite banner with dismiss; menu items: Settings, Recommend AetherRead, Help & Feedback, About, Tip Center, Manage, Privacy Consent, Sign Out |
| **PDF Reader** | Native PDF rendering via Android PdfRenderer, pinch-to-zoom, page-by-page scrolling, reading state persistence |
| **Reader Toolbar (View mode)** | Mode switcher dropdown: View, Annotate, Draw, Fill & Sign, Convert, Prepare Form, Insert, Measure, Pens, Redact, Favorites |
| **Floating Edit Toolbar** | Xodo-style pill toolbar with Smart Pen, Highlight, Callout, Strikethrough, Sticky Note, Undo, Redo |
| **Color Picker** | Inline color palette revealed when a drawing/highlight tool is active |
| **Comfort Themes** | 6 reading themes applied to the PDF canvas |
| **Bottom Navigation** | Files, Toolbox, My AetherRead, Aether Sign (4 tabs) |

### ✅ Windows Native App (`aetherRead-windows-native`)
Built from scratch in C# with WPF (.NET 8) — **no Electron, no browser overhead**.

- Native WPF window with left navigation rail (Files, Toolbox tabs)
- Google Pdfium C++ rendering engine via `PdfiumViewer` NuGet package
- Native Windows file picker (OpenFileDialog)
- Reader toolbar: View, Annotate, Draw, Fill & Sign mode buttons
- Page navigation, Close/Back to library
- Dark-mode aware system colors

---

## Tech Stack

### Web (`aetherRead-web`)
| Layer | Technology |
|-------|-----------|
| Framework | Next.js 14 (App Router) |
| Language | TypeScript 5 |
| Styling | Tailwind CSS 3 + DM Sans |
| Local DB | Dexie.js (IndexedDB) |
| State | Zustand 4 |
| PDF Engine | `@react-pdf-viewer/core` + `pdfjs-dist 3.11` |
| Auth | Firebase Auth |
| Hosting | Firebase Hosting |

### Android (`aetherRead-android`)
| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose (Material3) |
| DI | Hilt |
| Local DB | Room DB |
| PDF Engine | Android PdfRenderer |
| Navigation | Jetpack Navigation Compose |
| Architecture | MVVM + StateFlow |

### Windows Native (`aetherRead-windows-native`)
| Layer | Technology |
|-------|-----------|
| Language | C# 12 |
| Framework | WPF (.NET 8) |
| PDF Engine | PdfiumViewer (Google Pdfium) |
| Target | Windows 10 / 11 (x64) |

### API (`aetherRead-api`) — *Upcoming*
FastAPI · Python 3.11 · PyMuPDF · Pydantic v2 · Docker · Uvicorn

### AI / ML Pipeline — *Upcoming*
Ollama (llama3.2:3b) · ChromaDB · LangChain · RAGAS evaluation · Local RAG with citations

---

## Repository Structure

```
aetherRead/
├── aetherRead-android/          # Native Android app (Kotlin + Jetpack Compose)
│   └── app/src/main/java/
│       └── com/beinganujchaudhary/aetherread/
│           ├── ui/
│           │   ├── auth/            # Login / Signup screens
│           │   ├── library/         # LibraryScreen, ToolboxScreen, ProfileScreen, DocumentCard
│           │   ├── reader/          # ReaderScreen, AnnotationToolbar, PdfPageRenderer, ThemePicker
│           │   └── navigation/      # NavGraph, MainScreen (BottomNav)
│           └── domain/model/        # Document, ReadingState, ComfortTheme
│
├── aetherRead-windows-native/   # Native Windows app (C# + WPF + .NET 8)
│   ├── MainWindow.xaml          # UI layout (Nav rail, Library, Reader views)
│   ├── MainWindow.xaml.cs       # Code-behind (PDF loading, navigation)
│   ├── App.xaml / App.xaml.cs   # App entry point
│   └── aetherRead-windows-native.csproj
│
├── aetherRead-web/              # Next.js web app
│   ├── src/
│   │   ├── app/                 # Next.js App Router pages
│   │   │   ├── page.tsx         # Landing page
│   │   │   ├── app/             # Library (/app)
│   │   │   └── app/reader/      # PDF reader
│   │   ├── components/
│   │   │   ├── landing/         # Navbar, Hero, Features, Download, ThemeShowcase, Footer
│   │   │   ├── library/         # DocumentCard, UploadZone
│   │   │   └── reader/          # PDFViewer, AnnotationPanel
│   │   └── lib/
│   │       └── db.ts            # Dexie schema + CRUD helpers
│   └── package.json
│
├── .gitignore
├── .gitattributes
└── README.md
```

---

## Getting Started

### Web App

```bash
git clone https://github.com/beingAnujChaudhary/aetherRead.git
cd aetherRead/aetherRead-web
npm install
npm run dev
```
Open [http://localhost:3000](http://localhost:3000) — runs fully offline. No backend keys required for local PDF reading.

### Android App

1. Open `aetherRead-android/` in **Android Studio Hedgehog** or later.
2. Let Gradle sync.
3. Connect a device or start an emulator (Android 8.0+).
4. Click **Run ▶**.

Or download the App directly: [**aetherRead-android.zip**](https://beinganujchaudhary.web.app/projects/aetherRead/downloads/aetherRead-android.zip)

### Windows Native App

**Requirements:** .NET 8 SDK · Windows 10 / 11 x64

```bash
cd aetherRead/aetherRead-windows-native
dotnet run
```

Or build a standalone executable:
```bash
dotnet publish -c Release -r win-x64 --self-contained true
```

---

## Reading Themes

| Theme | Background | Text | Use Case |
|-------|-----------|------|----------|
| 🌑 Dark Abyss | `#0D0D0D` | `#E8E8E8` | Night reading |
| 📄 Book Paper | `#F5F0E8` | `#2C2416` | Standard reading |
| 🏜️ Sepia Sands | `#EDE0C8` | `#2C1810` | Eye-strain reduction |
| 🎯 Focus Punch | `#1A1A2E` | `#E8E8FF` | Deep concentration |
| ⬛ Monochrome | `#F0F0F0` | `#111111` | Distraction-free |
| 🌿 Garden Sage | `#CCE8CC` | `#0A0A0A` | Nature / daytime |

---

## Development Roadmap

```
Phase 1 — Foundations            ✅ Done
Phase 2 — Web MVP                ✅ Done
Phase 3 — Native Android         ✅ Done
Phase 4 — Windows Native WPF     ✅ Done
Phase 5 — Decoupled Sync         ⚡ Active (you are here)
Phase 6 — Local AI Brain         🔜 Upcoming
Phase 7 — Cloud Workspace        🔜 Upcoming
```

---

## License

| Component | License |
|-----------|---------|
| Web (`aetherRead-web`) | [MIT](https://github.com/beingAnujChaudhary/aetherRead/blob/main/LICENSE) |
| Android (`aetherRead-android`) | AGPL-3.0 |
| Windows (`aetherRead-windows-native`) | MIT |
| API (`aetherRead-api`) | Proprietary |

---

## Author

**Anuj Chaudhary**  
BS in Data Science & Applications · IIT Madras

[![Portfolio](https://img.shields.io/badge/Website-beinganujchaudhary.web.app-FE320A?style=flat-square)](https://beinganujchaudhary.web.app)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-beinganujchaudhary-0A66C2?style=flat-square&logo=linkedin)](https://www.linkedin.com/in/beinganujchaudhary/)
[![GitHub](https://img.shields.io/badge/GitHub-beingAnujChaudhary-181717?style=flat-square&logo=github)](https://github.com/beingAnujChaudhary)
[![Email](https://img.shields.io/badge/Email-beinganujchaudhary%40gmail.com-EA4335?style=flat-square&logo=gmail)](mailto:beinganujchaudhary@gmail.com)

---

<div align="center">
  <sub>Built with ❤️ for the open-source community · Exploring whether AI can surpass human imagination</sub>
</div>
