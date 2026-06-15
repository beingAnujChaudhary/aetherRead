"""Application configuration using Pydantic Settings."""
from functools import lru_cache
from typing import List

from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    model_config = SettingsConfigDict(
        env_file=".env",
        env_file_encoding="utf-8",
        case_sensitive=False,
    )

    # ── Application ───────────────────────────────────────────────
    ENVIRONMENT: str = "development"
    DEBUG: bool = True
    SECRET_KEY: str = "change-me-in-production"

    # ── CORS ──────────────────────────────────────────────────────
    CORS_ORIGINS: List[str] = [
        "http://localhost:3000",   # Next.js dev server
        "http://localhost:3001",
    ]

    # ── Ollama ────────────────────────────────────────────────────
    OLLAMA_BASE_URL: str = "http://localhost:11434"
    OLLAMA_MODEL: str = "llama3.2:3b"
    OLLAMA_EMBED_MODEL: str = "nomic-embed-text"

    # ── ChromaDB ──────────────────────────────────────────────────
    CHROMA_PERSIST_DIR: str = "./data/chroma"
    CHROMA_COLLECTION: str = "aetherread_documents"

    # ── Supabase (optional — Phase 4 sync) ───────────────────────
    SUPABASE_URL: str = ""
    SUPABASE_SERVICE_KEY: str = ""

    # ── PDF Extraction ────────────────────────────────────────────
    PDF_UPLOAD_DIR: str = "./data/uploads"
    MAX_PDF_SIZE_MB: int = 100
    CHUNK_SIZE: int = 512           # tokens per chunk
    CHUNK_OVERLAP: int = 64


@lru_cache
def get_settings() -> Settings:
    return Settings()


settings = get_settings()
