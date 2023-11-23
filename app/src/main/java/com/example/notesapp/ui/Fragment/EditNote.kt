package com.example.notesapp.ui.Fragment

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.notesapp.Models.Notes
import com.example.notesapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Date
import com.example.notesapp.databinding.FragmentEditNoteBinding
import com.example.notesapp.ViewModels.NotesViewModel

class EditNote : Fragment() {
    val oldNotes by navArgs<EditNoteArgs>()
    lateinit var binding: FragmentEditNoteBinding
    val viewModel : NotesViewModel by viewModels()
    var  priority: String = "1"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentEditNoteBinding.inflate(layoutInflater, container,false)
        setHasOptionsMenu(true)

        binding.EditTitle.setText(oldNotes.data.title)
        binding.EditSubTitle.setText(oldNotes.data.subtitle)
        binding.EditTextarea.setText(oldNotes.data.notes)

        when(oldNotes.data.priority){
            "1" ->{
                priority="1"
                binding.greendot.setImageResource(R.drawable.save)
                binding.yellowdot.setImageResource(0)
                binding.reddot.setImageResource(0)
            }
            "2" ->{
                priority="2"
                binding.yellowdot.setImageResource(R.drawable.save)
                binding.greendot.setImageResource(0)
                binding.reddot.setImageResource(0)
            }
            "3" ->{
                priority="3"
                binding.reddot.setImageResource(R.drawable.save)
                binding.yellowdot.setImageResource(0)
                binding.greendot.setImageResource(0)
            }
        }

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

        binding.btnEditNote.setOnClickListener {
            updateNotes(it)
        }
        return binding.root
    }

    private fun updateNotes(it: View?) {

        val title =  binding.EditTitle.text.toString()
        val subTitle =  binding.EditSubTitle.text.toString()
        val notes = binding.EditTextarea.text.toString()
        val d = Date()
        val notesDate: CharSequence = DateFormat.format("dd-MM-yyyy", d.getTime())
//        Log.e("@@@@", "createNotes : $notesDate" )  // For checking it's working or not
        val data = Notes(
            oldNotes.data.id ,
            title = title,
            subtitle = subTitle,
            notes = notes,
            date = notesDate.toString(),
            priority)

        viewModel.updateNotes(data)

        Toast.makeText(requireContext(), "Notes updated successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editNote_to_homeFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId== R.id.menuDelete){
            val bottomSheet : BottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.delete_alert)

            val textViewYes = bottomSheet.findViewById<TextView>(R.id.yesBtn)
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.noBtn)

            textViewYes?.setOnClickListener {
                viewModel.deleteNotes(oldNotes.data.id!!)
                bottomSheet.dismiss()

//                Navigation.findNavController(it!!).navigate(R.id.action_editNote_to_homeFragment)
            }

            textViewNo?.setOnClickListener {
                bottomSheet.dismiss()

            }

            bottomSheet.show()
        }

        return super.onOptionsItemSelected(item)
    }

}