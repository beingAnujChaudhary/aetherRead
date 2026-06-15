<div align="center">

# aetherRead

**AI-powered, offline-first PDF reader and research workspace**

[![Phase](https://img.shields.io/badge/Phase-3%20Native%20Android-4CAF50?style=flat-square)](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherread-android)
[![License Web](https://img.shields.io/badge/Web-MIT-green?style=flat-square)](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherread-web)
[![License Android](https://img.shields.io/badge/Android-AGPL--3.0-orange?style=flat-square)](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherread-android)
[![License API](https://img.shields.io/badge/API-Proprietary-red?style=flat-square)](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherread-api)
[![Portfolio](https://img.shields.io/badge/Portfolio-Anuj%20Chaudhary-FE320A?style=flat-square)](https://beinganujchaudhary.web.app)
[![IIT Madras](https://img.shields.io/badge/IIT%20Madras-BS%20Data%20Science-003087?style=flat-square)](https://study.iitm.ac.in)

*Read smarter. Think deeper. Stay offline.*

[**Live Demo**](https://beinganujchaudhary.web.app/projects/aetherRead.html) · [**Portfolio**](https://beinganujchaudhary.web.app) · [**Report Bug**](mailto:beinganujchaudhary@gmail.com) · [**LinkedIn**](https://www.linkedin.com/in/beinganujchaudhary/)

</div>

---

## About

aetherRead is a production-grade, offline-first PDF reader and AI research workspace built as a portfolio project by **Anuj Chaudhary** (BS in Data Science & Applications, IIT Madras).

The platform lets students, researchers, and academics read, annotate, and interact with documents entirely from their local machine — no internet, no cloud dependency, no data sent anywhere.

> This is a **three-repository open-core project**:
> | Repo | Stack | License |
> |------|-------|---------|
> | [`aetherread-web`](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherread-web) *(this repo)* | Next.js 14, TypeScript, Tailwind, Dexie.js | MIT |
> | [`aetherread-android`](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherread-android) | Kotlin, Jetpack Compose, Room DB | AGPL-3.0 |
> | [`aetherread-api`](https://github.com/beingAnujChaudhary/aetherRead/tree/main/aetherread-api) | FastAPI, Python 3.11, ChromaDB, Ollama | Proprietary |

---

## Features

### ✅ Available Now (Phase 2 — Web MVP)
- **True Offline-First** — All PDFs, annotations, and reading state stored locally via IndexedDB (Dexie.js). Zero backend required for reading.
- **6 Reading Themes (Comfort Engine)** — Dark Abyss, Book Paper, Sepia Sands, Focus Punch, Monochrome, Garden Sage — switch instantly without re-render.
- **Page-Level Annotations** — Attach rich notes to any page, categorized as Important, Definition, Question, Revision, or Quote.
- **Reading State Persistence** — Resumes exactly where you left off: page number, theme, scroll position.
- **Duplicate Detection** — SHA-256 file hashing prevents the same PDF being imported twice.
- **PDF Cover Thumbnails** — Auto-generated page-1 preview for the library grid.
- **Search & Sort** — Filter by title, sort by recent / title / progress.

### 🔜 Coming Soon
| Phase | Feature |
|-------|---------|
| 3 | Native Android app (Kotlin + Compose) |
| 4 | Decoupled sync — LWW reading position + two-way annotation merge |
| 5 | Local AI Brain — Ollama + ChromaDB RAG with citations |
| 6 | Cloud Workspace — revision packs, multi-doc projects, hybrid search |

---

## Tech Stack

### Web (`aetherread-web`)
| Layer | Technology |
|-------|-----------|
| Framework | Next.js 14 (App Router) |
| Language | TypeScript 5 |
| Styling | Tailwind CSS 3 + DM Sans font |
| Local DB | Dexie.js (IndexedDB) |
| State | Zustand 4 |
| PDF Engine | `@react-pdf-viewer/core` + `pdfjs-dist 3.11` |
| Animations | Framer Motion |
| Upload | react-dropzone |

### Android (`aetherread-android`)
Kotlin · Jetpack Compose · Room DB · Hilt DI · Retrofit · WindowSizeClass

### API (`aetherread-api`)
FastAPI · Python 3.11 · PyMuPDF · Pydantic v2 · Docker · Uvicorn · GitHub Actions CI/CD

### AI / ML Pipeline
Ollama (llama3.2:3b) · ChromaDB · LangChain · RAGAS evaluation · Local RAG with citations

### Database & Sync
Supabase (PostgreSQL) · pgvector · Realtime · Row Level Security · LWW conflict resolution

---

## Repository Structure

```
aetherRead/
├── aetherread-web/          # Next.js web app (this folder)
│   ├── src/
│   │   ├── app/             # Next.js App Router pages
│   │   │   ├── page.tsx     # Landing page
│   │   │   ├── app/         # Library (/app)
│   │   │   └── app/reader/  # PDF reader (/app/reader/[id])
│   │   ├── components/
│   │   │   ├── landing/     # Hero, Navbar, Features, ThemeShowcase, etc.
│   │   │   ├── library/     # DocumentCard, UploadZone, ReadingStats
│   │   │   └── reader/      # PDFViewer, ReaderToolbar, AnnotationPanel, AIChatPanel
│   │   ├── lib/
│   │   │   ├── db.ts        # Dexie schema + CRUD helpers
│   │   │   └── utils.ts     # Theme config, formatters, annotation metadata
│   │   └── stores/
│   │       ├── useReaderStore.ts     # PDF reader state (Zustand)
│   │       ├── useAnnotationStore.ts # Annotation state
│   │       └── useLibraryStore.ts    # Document library state
│   ├── public/
│   │   └── manifest.json    # PWA manifest
│   ├── .env.local.example   # Environment variable template
│   └── package.json
├── .gitignore               # Covers Android + Web + API
├── .gitattributes           # LF line-ending normalization
└── README.md
```

---

## Getting Started

### Prerequisites
- Node.js ≥ 18 (tested on v24)
- npm ≥ 9

### Run Locally

```bash
# 1. Clone the repo
git clone https://github.com/beingAnujChaudhary/aetherRead.git
cd aetherRead/aetherread-web

# 2. Install dependencies
npm install

# 3. Copy environment template (Supabase is optional — local reading works without it)
cp .env.local.example .env.local

# 4. Start the dev server
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) — the app runs fully offline. No Supabase keys required for local PDF reading.

### Environment Variables (optional)

```env
# .env.local

# Supabase — required only for cross-device sync (Phase 4)
NEXT_PUBLIC_SUPABASE_URL=your_supabase_project_url
NEXT_PUBLIC_SUPABASE_ANON_KEY=your_supabase_anon_key

# API backend — required only for AI features (Phase 5)
NEXT_PUBLIC_API_URL=http://localhost:8000
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

## Design

The landing page and UI follow the visual language of the [author's portfolio](https://beinganujchaudhary.web.app):
- **Font** — DM Sans (geometric grotesk, close to Neue Haas Display)
- **Background** — `#EFEAE3` warm cream
- **Accent** — `#FE320A` orange-red
- **Nav** — pill-shaped links with black fill-from-bottom animation

The in-app reader uses a separate **dark theme** optimized for prolonged PDF reading sessions.

---

## Development Roadmap

```
Phase 1 — Foundations       ✅ Done
Phase 2 — Web MVP           ✅ Done
Phase 3 — Native Android    ⚡ Active (you are here)
Phase 4 — Decoupled Sync    🔜 Upcoming
Phase 5 — Local AI Brain    🔜 Upcoming
Phase 6 — Cloud Workspace   🔜 Upcoming
```

---

## License

| Component | License |
|-----------|---------|
| Web (`aetherread-web`) | [MIT](https://github.com/beingAnujChaudhary/aetherRead/blob/main/aetherread-web/LICENSE) |
| Android (`aetherread-android`) | AGPL-3.0 |
| API (`aetherread-api`) | Proprietary |

---

## Author

**Anuj Chaudhary**
BS in Data Science & Applications · IIT Madras

[![Portfolio](https://img.shields.io/badge/Website-beinganujchaudhary.web.app-FE320A?style=flat-square)](https://beinganujchaudhary.web.app)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-beinganujchaudhary-0A66C2?style=flat-square&logo=linkedin)](https://www.linkedin.com/in/beinganujchaudhary/)
[![GitHub](https://img.shields.io/badge/GitHub-beinganujchaudhary-181717?style=flat-square&logo=github)](https://github.com/beinganujchaudhary)
[![Email](https://img.shields.io/badge/Email-beinganujchaudhary%40gmail.com-EA4335?style=flat-square&logo=gmail)](mailto:beinganujchaudhary@gmail.com)

---

<div align="center">
  <sub>Built with ❤️ for the open-source community · Exploring whether AI can surpass human imagination</sub>
</div>
