"""
AetherRead API — FastAPI application factory.

Phases:
  Phase 5 — Local AI Brain (Ollama + ChromaDB RAG)
  Phase 6 — Cloud Workspace features
"""
from contextlib import asynccontextmanager

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.core.config import settings
from app.api.v1.router import api_router


@asynccontextmanager
async def lifespan(app: FastAPI):
    """
    Application lifespan: startup → yield → shutdown.
    Initialise ChromaDB client and warm-up Ollama model on startup.
    """
    # TODO (Phase 5): Initialise ChromaDB persistent client
    # TODO (Phase 5): Warm-up Ollama model (pull if not present)
    print(f"🚀  AetherRead API starting — env: {settings.ENVIRONMENT}")
    yield
    # TODO: Graceful shutdown — flush any pending background tasks
    print("👋  AetherRead API shutting down")


def create_application() -> FastAPI:
    application = FastAPI(
        title="AetherRead API",
        description="AI-powered PDF extraction and RAG backend for AetherRead.",
        version="0.1.0",
        docs_url="/docs",
        redoc_url="/redoc",
        lifespan=lifespan,
    )

    # CORS — allow the Next.js web app (and Android) during development
    application.add_middleware(
        CORSMiddleware,
        allow_origins=settings.CORS_ORIGINS,
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"],
    )

    application.include_router(api_router, prefix="/api/v1")

    return application


app = create_application()
