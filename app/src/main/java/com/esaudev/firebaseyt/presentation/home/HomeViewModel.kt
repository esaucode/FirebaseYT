package com.esaudev.firebaseyt.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.firebaseyt.domain.model.Note
import com.esaudev.firebaseyt.domain.model.User
import com.esaudev.firebaseyt.domain.usecase.FirebaseAddNoteUseCase
import com.esaudev.firebaseyt.domain.usecase.FirebaseDeleteNoteUseCase
import com.esaudev.firebaseyt.domain.usecase.FirebaseGetAllNotesRealTimeUseCase
import com.esaudev.firebaseyt.domain.usecase.FirebaseGetAllNotesUseCase
import com.esaudev.firebaseyt.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addNoteUseCase: FirebaseAddNoteUseCase,
    private val deleteNoteUseCase: FirebaseDeleteNoteUseCase,
    private val getAllNotesUseCase: FirebaseGetAllNotesUseCase,
    private val getAllNotesRealTimeUseCase: FirebaseGetAllNotesRealTimeUseCase
): ViewModel() {

    private val _noteListState: MutableLiveData<Resource<List<Note>>> = MutableLiveData()
    val noteListState: LiveData<Resource<List<Note>>>
        get() = _noteListState

    private val _addNoteState: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val addNoteState: LiveData<Resource<Unit>>
        get() = _addNoteState

    private val _deleteNoteState: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val deleteNoteState: LiveData<Resource<Unit>>
        get() = _deleteNoteState

    fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesRealTimeUseCase().onEach {
                _noteListState.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {
            addNoteUseCase(note).onEach {
                _addNoteState.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note).onEach {
                _deleteNoteState.value = it
            }.launchIn(viewModelScope)
        }
    }

}