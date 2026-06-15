# aetherRead API

> **Phase 5 — Local AI Brain** · Status: 🔜 Upcoming  
> **License: Proprietary** — source visible for portfolio review only

FastAPI backend powering the AI research features of aetherRead: PDF extraction, vector embedding, and local RAG via Ollama + ChromaDB.

## Stack

| Layer | Technology |
|-------|-----------|
| Framework | FastAPI + Python 3.11 |
| PDF Parsing | PyMuPDF + Marker-pdf |
| Validation | Pydantic v2 |
| Vector Store | ChromaDB |
| LLM | Ollama (llama3.2:3b — local) |
| Orchestration | LangChain |
| Evaluation | RAGAS |
| Server | Uvicorn |
| Container | Docker + docker-compose |
| CI/CD | GitHub Actions |

## Planned Features (Phase 5)

- [ ] PDF text extraction endpoint (PyMuPDF / Marker-pdf)
- [ ] Chunk + embed extracted text into ChromaDB
- [ ] RAG `/chat` endpoint — query documents with citations
- [ ] RAGAS evaluation pipeline for answer quality
- [ ] Local Ollama inference (llama3.2:3b) — no external API calls
- [ ] Async background tasks via FastAPI BackgroundTasks

## Project Structure

```
aetherRead-api/
├── app/
│   ├── __init__.py
│   ├── main.py                 # FastAPI app factory + lifespan
│   ├── core/
│   │   ├── config.py           # Pydantic Settings (env vars)
│   │   └── logging.py          # Structured logging setup
│   ├── api/
│   │   └── v1/
│   │       ├── router.py       # Include all v1 routers
│   │       ├── endpoints/
│   │       │   ├── documents.py  # Upload + extract PDF
│   │       │   ├── chat.py       # RAG chat endpoint
│   │       │   └── health.py     # Health check
│   ├── services/
│   │   ├── pdf_extractor.py    # PyMuPDF / Marker extraction
│   │   ├── embedder.py         # Chunk + embed text → ChromaDB
│   │   ├── rag_pipeline.py     # LangChain RAG chain
│   │   └── ollama_client.py    # Ollama LLM client
│   ├── schemas/
│   │   ├── document.py         # Document request/response models
│   │   └── chat.py             # Chat request/response models
│   └── db/
│       └── chroma.py           # ChromaDB client singleton
├── tests/
│   ├── __init__.py
│   ├── test_documents.py
│   └── test_chat.py
├── .env.example
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── pyproject.toml
├── requirements.txt
└── README.md
```

## Getting Started (when implemented)

```bash
# 1. Install dependencies
pip install -r requirements.txt

# 2. Copy environment template
cp .env.example .env

# 3. Start Ollama (must be running locally)
ollama pull llama3.2:3b

# 4. Run with Docker Compose
docker compose up --build

# 5. Or run directly
uvicorn app.main:app --reload --port 8000
```

API docs available at `http://localhost:8000/docs` (Swagger UI).

## License

Proprietary — source visible for portfolio review only. Not for redistribution or commercial use.

## Author

[**beingAnujChaudhary**](https://beinganujchaudhary.web.app/) · [beinganujchaudhary.web.app](https://beinganujchaudhary.web.app) · IIT Madras BS Data Science
