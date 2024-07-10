package com.example.internshalanotes.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.internshalanotes.R
import com.example.internshalanotes.ViewModel.NotesViewModel
import com.example.internshalanotes.databinding.FragmentMainScreenBinding
import com.example.internshalanotes.ui.Adapter.NotesAdapter

class MainScreen : Fragment() {
    lateinit var Binding: FragmentMainScreenBinding
    val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        Binding =
            FragmentMainScreenBinding.inflate(layoutInflater, container, false)   //apply Binding

        viewModel.getNotes().observe(viewLifecycleOwner, { noteslist ->
            Binding.recyclerView.layoutManager = GridLayoutManager(activity, 2
            )   //as a grid span counts for left or right content if we apply 1 then there is show one note and on secondline second note
            Binding.recyclerView.adapter = NotesAdapter(activity, noteslist)
        })

        Binding.btnaddnotes.setOnClickListener {    //to go from home to create
            Navigation.findNavController(it).navigate(R.id.action_mainScreen_to_createNotes)
        }

        return Binding.root
    }
}
