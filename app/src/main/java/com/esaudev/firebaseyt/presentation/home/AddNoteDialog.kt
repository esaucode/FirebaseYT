package com.esaudev.firebaseyt.presentation.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.esaudev.firebaseyt.databinding.DialogAddNoteBinding

class AddNoteDialog(

): DialogFragment() {

    private lateinit var binding : DialogAddNoteBinding
    private var onSubmitClickListener: ((String) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddNoteBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.bAddNote.setOnClickListener {
            onSubmitClickListener?.invoke(binding.etNote.text.toString())
            dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    fun setOnAddNoteClickListener(action: (String) -> Unit) {
        onSubmitClickListener = action
    }

}