"""Health check endpoint."""
from fastapi import APIRouter
from pydantic import BaseModel

router = APIRouter()


class HealthResponse(BaseModel):
    status: str
    version: str
    ollama_reachable: bool


@router.get("/", response_model=HealthResponse, summary="Health check")
async def health_check() -> HealthResponse:
    """
    Returns API status and whether the local Ollama instance is reachable.
    Used by the Android app and web client to verify connectivity before
    enabling AI features.
    """
    # TODO (Phase 5): Actually ping Ollama /api/tags endpoint
    return HealthResponse(
        status="ok",
        version="0.1.0",
        ollama_reachable=False,  # placeholder
    )
