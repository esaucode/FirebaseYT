package com.esaudev.firebaseyt.domain.repository

import com.esaudev.firebaseyt.domain.model.Note
import com.esaudev.firebaseyt.util.Resource

interface NotesRepository {

    suspend fun saveNote(note: Note): Resource<Unit>

    suspend fun getAllNotes(): Resource<List<Note>>

    suspend fun deleteNote(note: Note): Resource<Unit>

}