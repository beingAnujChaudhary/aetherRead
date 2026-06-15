package com.beinganujchaudhary.aetherread.ui.reader

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beinganujchaudhary.aetherread.data.repository.AnnotationRepository
import com.beinganujchaudhary.aetherread.domain.model.Annotation
import com.beinganujchaudhary.aetherread.domain.model.AnnotationCategory
import com.beinganujchaudhary.aetherread.domain.model.AnnotationType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AnnotationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AnnotationRepository
) : ViewModel() {

    private val documentId: String = checkNotNull(savedStateHandle["documentId"])

    val annotations: StateFlow<List<Annotation>> = repository.getAnnotationsForDocument(documentId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addAnnotation(pageNumber: Int, category: AnnotationCategory, note: String, quote: String?, type: AnnotationType?) {
        viewModelScope.launch {
            val annotation = Annotation(
                id = UUID.randomUUID().toString(),
                documentId = documentId,
                pageNumber = pageNumber,
                category = category,
                note = note,
                quote = quote,
                annotationType = type,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                isDeleted = false
            )
            repository.saveAnnotation(annotation)
        }
    }

    fun deleteAnnotation(id: String) {
        viewModelScope.launch {
            repository.deleteAnnotation(id)
        }
    }
}
