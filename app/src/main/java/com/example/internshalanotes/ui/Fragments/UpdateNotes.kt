package com.example.internshalanotes.ui.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.internshalanotes.Model.Notes
import com.example.internshalanotes.R
import com.example.internshalanotes.ViewModel.NotesViewModel
import com.example.internshalanotes.databinding.FragmentUpdateNotesBinding
import java.util.Date

class UpdateNotes : Fragment() {

    private lateinit var binding: FragmentUpdateNotesBinding
    private val args: UpdateNotesArgs by navArgs()
    private val viewModel: NotesViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateNotesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(false) // Disable the menu bar

        // Retrieve the Notes object from navArgs
        val notes: Notes = args.data

        // Set data to UI elements
        binding.Edittitle.setText(notes.title)
        binding.Editsubtitle.setText(notes.subtitle)
        binding.EditNotes.setText(notes.notes)

        // Setup click listener for save button
        binding.EditbtnSaveNotes.setOnClickListener {
            updateNotes(it)
        }

        // Setup click listener for delete button/icon
        binding.delete.setOnClickListener {
            deleteNotes()
        }

        return binding.root
    }

    private fun updateNotes(view: View) {
        val title = binding.Edittitle.text.toString()
        val subtitle = binding.Editsubtitle.text.toString()
        val notes = binding.EditNotes.text.toString()
        val currentDate = DateFormat.format("MMMM d, yyyy", Date().time).toString()

        // Create updated Notes object
        val updatedNotes = Notes(
            id = args.data.id,
            title = title,
            subtitle = subtitle,
            notes = notes
        )

        // Update notes using ViewModel
        viewModel.updateNotes(updatedNotes)

        // Show toast and navigate back to home fragment
        Toast.makeText(requireContext(), "Notes Updated Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_updateNotes_to_mainScreen)
    }

    private fun deleteNotes() {
        val notesToDelete = args.data

        args.data.id?.let { viewModel.deleteNotes(it) }

        Toast.makeText(requireContext(), "Note Deleted Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_updateNotes_to_mainScreen)
    }
}
