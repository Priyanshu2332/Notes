package com.example.internshalanotes.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.internshalanotes.Model.Notes
import com.example.internshalanotes.R
import com.example.internshalanotes.ViewModel.NotesViewModel
import com.example.internshalanotes.databinding.FragmentCreateNotesBinding


class CreateNotes : Fragment() {
    lateinit var Binding: FragmentCreateNotesBinding
    val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{

        // Hide the support action bar
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()


        Binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)


        Binding.btnSaveNotes.setOnClickListener{
            CreateNotes(it)
        }
        return Binding.root
    }

    @SuppressLint("NotConstructor")
    private fun CreateNotes(it: View?) {
        val title= Binding.title.text.toString()
        val Subtitle= Binding.subtitle.text.toString()
        val notes=Binding.Notes.text.toString()

        val data= Notes(null,
            title=title,
            subtitle = Subtitle,
            notes = notes,
        )

        viewModel.addNotes(data)

        Toast.makeText(activity,"Notes Created Successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_createNotes_to_mainScreen)
    }


}