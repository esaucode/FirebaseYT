package com.esaudev.firebaseyt.domain.usecase

import com.esaudev.firebaseyt.domain.model.Note
import com.esaudev.firebaseyt.domain.repository.NotesRepository
import com.esaudev.firebaseyt.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseGetAllNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<Note>>> = flow {
        emit(Resource.Loading)

        val networkRequest = notesRepository.getAllNotes()

        when(networkRequest) {
            is Resource.Success -> emit(Resource.Success(networkRequest.data))
            is Resource.Error -> emit(Resource.Error(networkRequest.message))
            else -> Resource.Finished
        }
    }
}