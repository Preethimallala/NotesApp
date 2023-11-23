package com.example.notesapp.ui.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.R
//import android.view.*
import com.example.notesapp.Models.Notes
import com.example.notesapp.ui.Adapter.NotesAdapter
import com.example.notesapp.ViewModels.NotesViewModel
import com.example.notesapp.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val viewModel : NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        // Layout Manager for all
        val StaggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.allNotes.layoutManager = StaggeredGridLayoutManager

        // getting all notes
        viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            adapter = NotesAdapter(requireContext(), notesList)
            binding.allNotes.adapter = adapter
            val StaggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            binding.allNotes.layoutManager = StaggeredGridLayoutManager
        })
        //filter button
                binding.filterBtn.setOnClickListener {
                    viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
                        oldMyNotes =  notesList as ArrayList<Notes>
                        adapter = NotesAdapter(requireContext(), notesList)
                        binding.allNotes.adapter = adapter
                        val StaggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                        binding.allNotes.layoutManager = StaggeredGridLayoutManager
                    })
                }
        // filter high
        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner, { notesList ->
                oldMyNotes =  notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.allNotes.adapter = adapter
                val StaggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                binding.allNotes.layoutManager = StaggeredGridLayoutManager
            })
        }
        // filter medium
        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner, { notesList ->
                oldMyNotes =  notesList as ArrayList<Notes>
                adapter =  NotesAdapter(requireContext(), notesList)
                binding.allNotes.adapter = adapter
                val StaggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                binding.allNotes.layoutManager = StaggeredGridLayoutManager
            })
        }
        // filter low
        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner, { notesList ->
                oldMyNotes =  notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.allNotes.adapter = adapter
                val StaggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                binding.allNotes.layoutManager = StaggeredGridLayoutManager
            })
        }
        binding.btnAddNote.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNote)
        }
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val item = menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView

        searchView.queryHint = "Enter Notes here"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                NotesFiltering(p0)
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun NotesFiltering(p0: String?) {
        val newFilteredList  = arrayListOf<Notes>()
        for (i in oldMyNotes){
            if (i.title.contains(p0!!) ||i.subtitle.contains(p0!!)){
                newFilteredList.add(i)
            }
        }
        adapter.filtering(newFilteredList)
    }
}