package com.example.dmass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class CharListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_char_list, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val sessionID = bundle!!.getString("Session")!!
        val dbHelper: DBHelper = DBHelper(requireActivity())
        val characters: ArrayList<Char> = dbHelper.getSessionChars(sessionID)


        val charAdapter = CharAdapter(characters, requireContext())
        val recyclerView: RecyclerView = view.findViewById(R.id.char_list_recycler)
        recyclerView.adapter = charAdapter

        val nextBTN: Button = view.findViewById(R.id.char_list_next)
        val sortBTN: Button = view.findViewById(R.id.char_list_sort)


        nextBTN.setOnClickListener {
            Collections.rotate(characters, -1)
            charAdapter.notifyDataSetChanged()
        }
        sortBTN.setOnClickListener {
            characters.sortByDescending {
                it.charINIT
            }
            charAdapter.notifyDataSetChanged()
        }
    }

}