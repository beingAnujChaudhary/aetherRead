"""
Document endpoints — PDF upload, extraction, and embedding.

Phase 5 implementation plan:
  POST /documents/upload  → receive PDF, extract text with PyMuPDF/Marker
  POST /documents/{id}/embed  → chunk + embed extracted text into ChromaDB
  GET  /documents/{id}/status → embedding progress
  DELETE /documents/{id}      → remove from ChromaDB
"""
from fastapi import APIRouter, HTTPException, UploadFile, File, status
from pydantic import BaseModel

router = APIRouter()


class DocumentUploadResponse(BaseModel):
    document_id: str
    title: str
    page_count: int
    file_size_bytes: int
    status: str


@router.post(
    "/upload",
    response_model=DocumentUploadResponse,
    status_code=status.HTTP_202_ACCEPTED,
    summary="Upload and extract a PDF",
)
async def upload_document(file: UploadFile = File(...)) -> DocumentUploadResponse:
    """
    Accept a PDF file, extract its text content with PyMuPDF, and queue
    it for embedding into ChromaDB.

    Returns a document_id that can be used for subsequent chat queries.
    """
    if file.content_type != "application/pdf":
        raise HTTPException(
            status_code=status.HTTP_415_UNSUPPORTED_MEDIA_TYPE,
            detail="Only PDF files are accepted.",
        )
    # TODO (Phase 5): Save file → extract with PyMuPDF → queue embedding
    raise HTTPException(
        status_code=status.HTTP_501_NOT_IMPLEMENTED,
        detail="Document upload will be available in Phase 5.",
    )
