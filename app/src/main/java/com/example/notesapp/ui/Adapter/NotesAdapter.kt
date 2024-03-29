package com.example.notesapp.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Models.Notes
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemNotesBinding
import com.example.notesapp.ui.Fragment.HomeFragmentDirections

class NotesAdapter(val requireContext: Context, var notesList: List<Notes>) : RecyclerView.Adapter<NotesAdapter.notesViewHolder>() {

    fun filtering(newFilteredList: ArrayList<Notes>) {
        notesList = newFilteredList
        notifyDataSetChanged()
    }
    class notesViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        return notesViewHolder((ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent,false)))
    }

    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {
        val data = notesList[position]
        holder.binding.notesTitle.text = data.title.toString()
        holder.binding.notesSubTitle.text = data.subtitle.toString()
        holder.binding.notesDate.text = data.date.toString()

        when(data.priority){
            "1" ->{
                holder.binding.viewPriority.setBackgroundResource(R.drawable.green)
            }
            "2" ->{
                holder.binding.viewPriority.setBackgroundResource(R.drawable.yellow)
            }
            "3" ->{holder.binding.viewPriority.setBackgroundResource(R.drawable.orange)}
        }
        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNote(data)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount()= notesList.size

}