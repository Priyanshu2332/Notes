package com.example.internshalanotes.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.internshalanotes.Model.Notes
import com.example.internshalanotes.databinding.ItemLayoutBinding
import com.example.internshalanotes.ui.Fragments.MainScreenDirections

class NotesAdapter(val activity: FragmentActivity?, val noteslist: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.notesViewHolder>( ) {
    class notesViewHolder(val Binding: ItemLayoutBinding) : RecyclerView.ViewHolder(Binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        return notesViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false
        )
        )
    }

    override fun onBindViewHolder(holder: NotesAdapter.notesViewHolder, position: Int) {
        val data = noteslist[position]
        holder.Binding.NotesTitle.text = data.title.toString()
        holder.Binding.NotesSubtitle.text = data.subtitle.toString()



        holder.Binding.root.setOnClickListener{
            val action=MainScreenDirections.actionMainScreenToUpdateNotes(data)
            Navigation.findNavController(it).navigate(action)
        }
    }
    override fun getItemCount()=noteslist.size

}