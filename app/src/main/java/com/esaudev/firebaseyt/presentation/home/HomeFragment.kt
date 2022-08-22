package com.esaudev.firebaseyt.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.esaudev.firebaseyt.databinding.FragmentHomeBinding
import com.esaudev.firebaseyt.domain.model.Note
import com.esaudev.firebaseyt.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val args: HomeFragmentArgs by navArgs()
    private val noteListAdapter: NoteListAdapter = NoteListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListComponent()
        initListeners()
        initObservers()

        getNotes()
    }

    private fun initObservers() {
        viewModel.addNoteState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> Unit
                is Resource.Error -> Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                    .show()
                else -> Unit
            }
        }
        viewModel.deleteNoteState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> Unit
                is Resource.Error -> Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                    .show()
                else -> Unit
            }
        }
        viewModel.noteListState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Success -> noteListAdapter.submitList(state.data)
                is Resource.Error -> Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                    .show()
                else -> Unit
            }
        }
    }

    private fun getNotes() {
        viewModel.getAllNotes()
    }

    private fun initListComponent() {
        binding.rvNotes.apply {
            adapter = noteListAdapter
        }
    }

    private fun initListeners() {
        with(binding) {
            bAddNote.setOnClickListener { showAddNoteDialog() }
            noteListAdapter.setNoteClickListener { showDeleteNoteDialog(it) }
        }
    }

    private fun showAddNoteDialog() {
        val addNoteDialog = AddNoteDialog()
        addNoteDialog.setOnAddNoteClickListener {
            viewModel.saveNote(Note(
                note = it,
                author = args.uid
            ))
        }
        addNoteDialog.show(parentFragmentManager, "add_note_dialog")
    }

    private fun showDeleteNoteDialog(note: Note) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Eliminar nota")
        alertDialog.setMessage("Se eliminará  esta nota definitivamente")

        alertDialog.setPositiveButton(android.R.string.yes) { dialog, which ->
            viewModel.deleteNote(note)
        }

        alertDialog.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(requireContext(),
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}