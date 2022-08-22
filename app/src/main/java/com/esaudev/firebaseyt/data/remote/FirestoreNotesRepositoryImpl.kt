package com.esaudev.firebaseyt.data.remote

import com.esaudev.firebaseyt.data.util.FirebaseConstants.NOTES_COLLECTION
import com.esaudev.firebaseyt.domain.model.Note
import com.esaudev.firebaseyt.domain.repository.NotesRepository
import com.esaudev.firebaseyt.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreNotesRepositoryImpl @Inject constructor(

): NotesRepository {

    override suspend fun saveNote(note: Note): Resource<Unit> {
        return try {
            var isSuccessful = false
            FirebaseFirestore.getInstance().collection(NOTES_COLLECTION)
                .document(note.id)
                .set(note, SetOptions.merge())
                .addOnCompleteListener { isSuccessful = it.isSuccessful }
                .await()

            if (isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Network error")
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getAllNotes(): Resource<List<Note>> {
        return try {
            val notesList = FirebaseFirestore.getInstance().collection(NOTES_COLLECTION)
                .get()
                .await()
                .toObjects(Note::class.java)

            Resource.Success(notesList)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun deleteNote(note: Note): Resource<Unit> {
        return try {
            var isSuccessful = false
            FirebaseFirestore.getInstance().collection(NOTES_COLLECTION)
                .document(note.id)
                .delete()
                .addOnCompleteListener { isSuccessful = it.isSuccessful }
                .await()

            if (isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Network error")
            }
        } catch (e :Exception) {
            Resource.Error(e.message.toString())
        }
    }

}