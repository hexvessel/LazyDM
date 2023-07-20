package com.example.dmass

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dmass.Functions


class MainActivity : AppCompatActivity() {

    private lateinit var sessionsList: ArrayList<Session>
    private lateinit var recyclerView: RecyclerView
    private lateinit var editText: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val dbHelper: DBHelper = DBHelper(this)
        sessionsList = dbHelper.getSessions()
        val sessionsAdapter = SessionsAdapter(sessionsList)
        val recyclerView: RecyclerView = findViewById(R.id.Sessions_Recycler)
        recyclerView.adapter = sessionsAdapter

        val editText: EditText = findViewById(R.id.Sessions_edittext)
        val button: Button = findViewById<Button>(R.id.Sessions_Button)

        button.setOnClickListener {
            val newSession = editText.text
            if (newSession.isNotEmpty() && !sessionsList.contains(Session(newSession.toString())))
            {

                addSession(Session(newSession.toString()), sessionsAdapter, dbHelper)
                editText.text.clear()
                Functions.HideKeyboardObject.hideKeyboard(this)
                editText.clearFocus()

            }else{
                Toast.makeText(this, "Name Taken or No Name Given", Toast.LENGTH_SHORT).show()
            }
        }

        val remButton: Button = findViewById(R.id.sessions_rem_button)

        remButton.setOnClickListener {
            val sessionToRem = editText.text.toString()
            if (sessionToRem.isNotEmpty() && sessionsList.contains(Session(sessionToRem)))
            {
                remSession(Session(sessionToRem.toString()), sessionsAdapter, dbHelper)
                Toast.makeText(this, "Session \"$sessionToRem\" is kill", Toast.LENGTH_SHORT).show()
                editText.text.clear()
                Functions.HideKeyboardObject.hideKeyboard(this)
                editText.clearFocus()
            }else{
                Toast.makeText(this, "Session \"$sessionToRem\" does not exist", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun addSession(session: Session, adapter: SessionsAdapter, dbHelper: DBHelper){
        dbHelper.createSession(session)
        sessionsList.add(session)
        adapter.notifyDataSetChanged()

    }

    private fun remSession(session: Session, adapter: SessionsAdapter, dbHelper: DBHelper){
        dbHelper.deleteSession(session.sessionName)
        sessionsList.remove(session)
        adapter.notifyDataSetChanged()
    }


}