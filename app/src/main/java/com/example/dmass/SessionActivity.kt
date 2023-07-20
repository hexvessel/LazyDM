package com.example.dmass

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class SessionActivity : AppCompatActivity() {

    private var sessionID = ""
    private lateinit var charList: ArrayList<com.example.dmass.Char>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


        val intent = intent
        sessionID = intent.getStringExtra("session_name").toString()
        val title = findViewById<TextView>(R.id.session_title)
        title.text = sessionID



        val charListBTN: Button = findViewById(R.id.session_list_button)
        val charMenuBTN: Button = findViewById(R.id.session_charmenu_button)

        charListBTN.setOnClickListener {
            replaceFragment(CharListFragment(), sessionID)
        }
        charMenuBTN.setOnClickListener {
            replaceFragment(CharMenuFragment(), sessionID)
        }

    }
    private fun replaceFragment(fragment: Fragment, sessionID: String) {
        // Get a reference to the FragmentManager
        val bundle = Bundle()
        bundle.putString("Session", sessionID)
        fragment.arguments = bundle

        val fragmentManager = supportFragmentManager

        // Start a new FragmentTransaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the current fragment with the new fragment
        fragmentTransaction.replace(R.id.session_fragment, fragment)

        // Commit the FragmentTransaction
        fragmentTransaction.commit()
    }

}