package com.example.dmass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CharMenuFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_char_menu, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val sessionID = bundle!!.getString("Session")!!
        val dbHelper: DBHelper = DBHelper(requireContext())


        val editText: EditText = view.findViewById(R.id.char_menu_name)
        val hpNP: NumberPicker = view.findViewById(R.id.char_menu_HP_picker)
        val initNP: NumberPicker = view.findViewById(R.id.char_menu_INIT_picker)
        val acNP: NumberPicker = view.findViewById(R.id.char_menu_AC_picker)
        val saveBTN: Button = view.findViewById(R.id.char_menu_btn)

        hpNP.minValue = 0
        hpNP.maxValue = 999
        initNP.minValue = 0
        initNP.maxValue = 41
        acNP.minValue = 0
        acNP.maxValue = 50


        saveBTN.setOnClickListener {
            if (editText.text.isNotEmpty()){
                val newChar = Char(charName = editText.text.toString(),
                    session = sessionID,
                    charINIT = initNP.value,
                    charHP = hpNP.value,
                    charAC = acNP.value,
                    charHPMax = hpNP.value,
                    charDex = initNP.value)
                dbHelper.insertChar(newChar)
                Toast.makeText(requireContext(), "${editText.text.toString()} added", Toast.LENGTH_SHORT).show()
                editText.text.clear()
                hpNP.value = 0
                initNP.value = 0
                acNP.value = 0
            }
        }


    }

}