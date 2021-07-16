package com.knakul853.mysecretdiary.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.knakul853.mysecretdiary.App
import com.knakul853.mysecretdiary.R
import com.knakul853.mysecretdiary.UpdateTitleListner
import com.knakul853.mysecretdiary.data.note.Note
import com.knakul853.mysecretdiary.databinding.ActivityMainBinding
import com.knakul853.mysecretdiary.databinding.FragmentHomeBinding
import com.knakul853.mysecretdiary.ui.compose.ComposeFragments
import com.knakul853.mysecretdiary.utils.ComposeMode

class HomeFragment() : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: NoteAdapter
    private var _binding: FragmentHomeBinding? = null
    lateinit var deleteNote: Note

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = NoteAdapter{ note ->
            val action = HomeFragmentDirections.actionNavHomeToComposeFragments2(note, ComposeMode.VIEW_MODE)
            findNavController().navigate(action)

        }
        homeViewModel.getAllNote().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

        })

        //delete on swipe
        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.RIGHT) {
                    deleteNote = adapter.getNote(viewHolder.adapterPosition)
                    homeViewModel.delete(deleteNote)
                    Snackbar.make(
                        binding.rvNotes,
                        "this will be deleted permanently",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("undo", View.OnClickListener {
                            homeViewModel.insert(deleteNote)
                        })
                        .show()

                }
            }

        }



        binding.rvNotes.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvNotes)

        binding.addNote.setOnClickListener { view ->
            //todo:make user go to create notes.
            //val action = HomeFragmentD
            val action = HomeFragmentDirections.actionNavHomeToComposeFragments2(null, ComposeMode.COMPOSE_MODE)
            findNavController().navigate(action)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as UpdateTitleListner).updateTitle("Notes")

    }

}