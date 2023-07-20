package com.example.dmass

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "LazyDM.db"
        private const val TBL_CHARS = "tbl_chars"
        private const val TBL_SESSIONS = "tbl_sessions"
        private const val ID = "id"
        private const val NAME = "name"
        private const val SESSION = "session"
        private const val HP = "hp"
        private const val INIT = "init"
        private const val AC = "ac"
        private const val DEX = "dex"
        private const val HPMAX = "hpmax"
        private const val SESSION_NAME = "session_name"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableChars = ("CREATE TABLE " + TBL_CHARS
                + "(" + ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT, " + SESSION + " TEXT,"
                + HP + " INTEGER," + INIT + " INTEGER, " + AC + " INTEGER, " + DEX + " INTEGER, " + HPMAX + " INTEGER " + ")")
        val createTableSessions = (" CREATE TABLE " + TBL_SESSIONS
                + "(" + ID + " INTEGER PRIMARY KEY, " + SESSION_NAME + " TEXT UNIQUE" + ")")
        p0?.execSQL(createTableChars)
        p0?.execSQL(createTableSessions)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_CHARS")
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_SESSIONS")
    }

    fun insertChar(char: Char): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(HP, char.charHP)
        contentValues.put(INIT, char.charINIT)
        contentValues.put(DEX, char.charDex)
        contentValues.put(AC, char.charAC)
        contentValues.put(NAME, char.charName)
        contentValues.put(HPMAX, char.charHPMax)
        contentValues.put(SESSION, char.session)

        val success = db.insert(TBL_CHARS, null, contentValues)
        db.close()
        return success
    }

    fun createSession(session: Session): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(SESSION_NAME, session.sessionName)

        val success = db.insert(TBL_SESSIONS, null, contentValues)

        db.close()

        return success
    }

    @SuppressLint("Range")
    fun getSessionChars(session: String): ArrayList<Char> {
        val charList: ArrayList<Char> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_CHARS WHERE $SESSION = '${session}'"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var charName: String
        var charSession: String
        var charHP: Int
        var charINIT: Int
        var charAC: Int
        var charDex: Int
        var charHPMax: Int

        if(cursor.moveToFirst()){
            do {
                charName = cursor.getString(cursor.getColumnIndex("name"))
                charSession = cursor.getString(cursor.getColumnIndex("session"))
                charHP = cursor.getInt(cursor.getColumnIndex("hp"))
                charINIT = cursor.getInt(cursor.getColumnIndex("init"))
                charAC = cursor.getInt(cursor.getColumnIndex("ac"))
                charDex = cursor.getInt(cursor.getColumnIndex("dex"))
                charHPMax = cursor.getInt(cursor.getColumnIndex("hpmax"))

                val c = com.example.dmass.Char(charName = charName, session = charSession,
                    charINIT = charINIT, charHP = charHP, charAC = charAC, charDex = charDex, charHPMax = charHPMax)
                charList.add(c)
            }while (cursor.moveToNext())
        }
        return charList
    }

    @SuppressLint("Range")
    fun getSessions(): ArrayList<Session>{
        val sessionList: ArrayList<Session> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_SESSIONS"

        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch(e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var session_name: String

        if(cursor.moveToFirst()){
            do{
                session_name = cursor.getString(cursor.getColumnIndex("session_name"))

                val s = Session(sessionName = session_name)

                sessionList.add(s)
            }while (cursor.moveToNext())
        }
        return sessionList
    }

    fun deleteChar(char: String, session: String) {
         val db = this.writableDatabase
        db.delete(TBL_CHARS,
            NAME + "=?" + "AND " + SESSION + "=?",
            arrayOf(char, session))
        db.close()
    }

    fun deleteSession(session: String) {
        val db = writableDatabase

        db.delete(TBL_CHARS, SESSION + "=?", arrayOf(session))
        db.delete(TBL_SESSIONS, SESSION_NAME + "=?", arrayOf(session))

        db.close()
    }


    fun changeINITValue(char: String, session: String, initValue: String){
        val db = writableDatabase
        val query = "UPDATE $TBL_CHARS SET $INIT = ($initValue) WHERE $NAME = '$char' AND $SESSION = '$session'"

        db.execSQL(query)

        db.close()

    }

    fun changeHPValue(char: String, session: String, hpValue: String){
        val db = writableDatabase
        val query = "UPDATE $TBL_CHARS SET $HP = ($hpValue) WHERE $NAME = '$char' AND $SESSION = '$session'"

        db.execSQL(query)

        db.close()
    }
}