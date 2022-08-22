package com.esaudev.firebaseyt.domain.usecase

import com.esaudev.firebaseyt.data.util.FirebaseConstants.NOTES_COLLECTION
import com.esaudev.firebaseyt.domain.model.Note
import com.esaudev.firebaseyt.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseGetAllNotesRealTimeUseCase @Inject constructor(

) {

    suspend operator fun invoke(): Flow<Resource<List<Note>>> = callbackFlow {

        val event = FirebaseFirestore.getInstance().collection(NOTES_COLLECTION)

        val subscription = event.addSnapshotListener { snapshot, error ->

            if (error != null) {
                this.trySend(Resource.Error(error.message.toString())).isSuccess
                return@addSnapshotListener
            }

            if (snapshot != null) {

                val noteList = snapshot.toObjects(Note::class.java)

                this.trySend(Resource.Success(noteList)).isSuccess

            }
        }

        awaitClose { subscription.remove() }

    }

}