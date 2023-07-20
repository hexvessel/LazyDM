package com.example.dmass

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text


class CharAdapter (private val dataSet: ArrayList<Char>, private val context: Context) :
    RecyclerView.Adapter<CharAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val hpPicker: NumberPicker
        val initPicker: NumberPicker
        val acView: TextView
        val delBTN: Button
        val HPText: TextView
        val INITText: TextView
        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.char_item_name)
            acView = view.findViewById(R.id.char_item_AC)
            hpPicker = view.findViewById(R.id.char_item_hp_NP)
            initPicker = view.findViewById(R.id.char_item_init_NP)
            delBTN = view.findViewById(R.id.char_item_delete_btn)
            HPText = view.findViewById(R.id.char_item_hp_text)
            INITText = view.findViewById(R.id.char_item_init_text)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.char_list_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val dbHelper: DBHelper = DBHelper(context)

        viewHolder.textView.text = dataSet[position].charName
        viewHolder.acView.text = "HP " + dataSet[position].charHPMax.toString() + "\n" +
                "DEX " + dataSet[position].charDex.toString() + "\n" +
                "AC " + dataSet[position].charAC.toString()

        viewHolder.hpPicker.value = dataSet[position].charHP
        viewHolder.initPicker.value = dataSet[position].charINIT
        viewHolder.HPText.text = "HP " + dataSet[position].charHP.toString()
        viewHolder.INITText.text = "INIT " + dataSet[position].charINIT.toString()
        viewHolder.hpPicker.maxValue = 999
        viewHolder.hpPicker.minValue = 0
        viewHolder.initPicker.maxValue = 50
        viewHolder.initPicker.minValue = 0

        viewHolder.initPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            dataSet[position].charINIT = i2
            dbHelper.changeINITValue(dataSet[position].charName, dataSet[position].session,i2.toString())
        }
        viewHolder.hpPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            dataSet[position].charHP = i2
            dbHelper.changeHPValue(dataSet[position].charName, dataSet[position].session,i2.toString())
        }
        viewHolder.delBTN.setOnClickListener {
            dbHelper.deleteChar(dataSet[position].charName, dataSet[position].session)
            dataSet.remove(dataSet[position])
            notifyDataSetChanged()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}

