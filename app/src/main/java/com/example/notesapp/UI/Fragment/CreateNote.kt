package com.example.notesapp.UI.Fragment

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.ViewModels
import com.example.notesapp.Models.notes
import androidx.navigation.Navigation
import com.example.notesapp.R

import java.util.Date

class CreateNote : Fragment() {
    lateinit var binding: FragmentCreateNoteBinding
    var  priority: String = "1"
    val viewModel : NotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNoteBinding.inflate(inflater,container,false)
        binding.greendot.setImageResource(R.drawable.save)

        binding.greendot.setOnClickListener {
            priority="1"
            binding.greendot.setImageResource(R.drawable.save)
            binding.yellowdot.setImageResource(0)
            binding.reddot.setImageResource(0)

        }

        binding.yellowdot.setOnClickListener {
            priority="2"
            binding.yellowdot.setImageResource(R.drawable.save)
            binding.greendot.setImageResource(0)
            binding.reddot.setImageResource(0)
        }

        binding.reddot.setOnClickListener {
            priority="3"
            binding.reddot.setImageResource(R.drawable.save)
            binding.yellowdot.setImageResource(0)
            binding.greendot.setImageResource(0)
        }

        binding.btnCreateNote.setOnClickListener {
            createNotes(it)
        }

        return binding.root


    }

    private fun createNotes(it: View?) {
        val title =  binding.CreateTitle.text.toString()
        val subTitle =  binding.CreateSubTitle.text.toString()
        val notes = binding.createNotes.text.toString()
        val d = Date()
        val notesDate: CharSequence = DateFormat.format("dd-MM-yyyy", d.getTime())
//        Log.e("@@@@", "createNotes : $notesDate" )  // For checking it's working or not
        val data = notes(null, title = title,
            subtitle = subTitle,
            notes = notes,
            date = notesDate.toString(),
            priority)

        viewModel.addNotes(data)

        Toast.makeText(requireContext(), "Notes created successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_createNote_to_homeFragement)

    }

}