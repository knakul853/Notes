package com.knakul853.mysecretdiary.ui.compose

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.knakul853.mysecretdiary.R
import com.knakul853.mysecretdiary.UpdateTitleListner
import com.knakul853.mysecretdiary.data.note.Note
import com.knakul853.mysecretdiary.databinding.ActivityMainBinding
import com.knakul853.mysecretdiary.databinding.FragmentComposeBinding
import com.knakul853.mysecretdiary.ui.home.NoteRepository
import com.knakul853.mysecretdiary.utils.ComposeMode
import java.text.SimpleDateFormat
import java.util.*

class ComposeFragments : Fragment(R.layout.fragment_compose) {

    lateinit var binding: FragmentComposeBinding
    private lateinit var calender:Calendar
    private lateinit var choosenDate:String
    private val args: ComposeFragmentsArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentComposeBinding.bind(view)
        choosenDate = SimpleDateFormat("dd / MM / yyyy").format(System.currentTimeMillis())
        binding.calenderTextView.setText(choosenDate)

        calender = Calendar.getInstance()

        val mode = args.composeModes
        if(mode == ComposeMode.COMPOSE_MODE){
            composeNote()
        }

        else if(mode == ComposeMode.VIEW_MODE){
            showNote()
        }





    }

    private fun showNote() {
        val note = args.note
        updateLabel(note?.title)

        binding.apply {
            calenderTextView.setText(note?.createAt)
            notesDescriptionTextView.setText(note?.description)
            notesTitleTextView.setText(note?.title)

            notesDescriptionTextView.isEnabled = false
            notesTitleTextView.isEnabled = false
            calenderTextView.isEnabled = false
            binding.saveNote.setImageResource(R.drawable.note_edit)

            binding.saveNote.setOnClickListener{
                editNote(note)
            }

        }

    }

    private fun editNote(note: Note?) {
        updateLabel(note?.title)
        binding.apply {
            notesDescriptionTextView.isEnabled = true
            notesTitleTextView.isEnabled = true
            binding.saveNote.setImageResource(R.drawable.save_note)

            binding.saveNote.setOnClickListener{

                val title = binding.notesTitleTextView.text.toString()
                val description = binding.notesDescriptionTextView.text.toString()
                note?.title = title
                note?.description = description

                if(valid(choosenDate, title, description)){
                    val repo = NoteRepository(requireActivity().application)
                    repo.update(note!!)
                    val action = ComposeFragmentsDirections.actionComposeFragments2ToNavHome2()
                    findNavController().navigate(action)

                }
                else{
                    Toast.makeText(requireContext(), "Your note not saved, Something went wrong", Toast.LENGTH_LONG).show()

                }
            }

        }
    }

    private fun composeNote() {

        updateLabel("Compose")

        val date = DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, monthOfYear)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val format = "dd / MM / yyyy"
            val sdf = SimpleDateFormat(format, Locale.US)
            choosenDate = sdf.format(calender.time)
            binding.calenderTextView.setText(choosenDate)

        }

        binding.calenderTextView.setOnClickListener{
            pickDate(date)
        }

        //Save this note
        binding.saveNote.setOnClickListener{

            val title = binding.notesTitleTextView.text.toString()
            val description = binding.notesDescriptionTextView.text.toString()
            if(valid(choosenDate, title, description)){
                val newNote = Note(title, description, choosenDate)
                val repo = NoteRepository(requireActivity().application)
                repo.insert(newNote)
                val action = ComposeFragmentsDirections.actionComposeFragments2ToNavHome2()
                findNavController().navigate(action)

            }
            else{
                Toast.makeText(requireContext(), "Your note not saved, Something went wrong", Toast.LENGTH_LONG).show()

            }


        }
    }

    private fun valid(choosenDate: String?, title: String?, description: String?): Boolean {

        if(choosenDate.isNullOrEmpty() || title.isNullOrEmpty() || description.isNullOrEmpty())return false
        return true;
    }

    private fun pickDate(date: DatePickerDialog.OnDateSetListener) {
        DatePickerDialog(requireActivity(), date, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateLabel(title: String?) {
        (activity as UpdateTitleListner).updateTitle(title!!)

    }

}