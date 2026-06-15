"""
Chat / RAG endpoint — query a document using local Ollama + ChromaDB.

Phase 5 implementation plan:
  POST /chat  → retrieve relevant chunks from ChromaDB → augment prompt →
                send to Ollama → stream response with citations
"""
from fastapi import APIRouter, HTTPException, status
from pydantic import BaseModel
from typing import List, Optional

router = APIRouter()


class Citation(BaseModel):
    page: int
    excerpt: str


class ChatRequest(BaseModel):
    document_id: str
    question: str
    top_k: int = 5         # number of chunks to retrieve
    stream: bool = False


class ChatResponse(BaseModel):
    answer: str
    citations: List[Citation]
    model: str
    tokens_used: Optional[int] = None


@router.post(
    "/",
    response_model=ChatResponse,
    summary="Ask a question about a document (RAG)",
)
async def chat(payload: ChatRequest) -> ChatResponse:
    """
    Retrieval-Augmented Generation (RAG) endpoint.

    1. Embed the user question using the embed model.
    2. Retrieve the top-k most relevant chunks from ChromaDB.
    3. Build an augmented prompt with retrieved context.
    4. Send to local Ollama LLM and return the response with citations.
    """
    # TODO (Phase 5): Full RAG pipeline implementation
    raise HTTPException(
        status_code=status.HTTP_501_NOT_IMPLEMENTED,
        detail="RAG chat will be available in Phase 5.",
    )
