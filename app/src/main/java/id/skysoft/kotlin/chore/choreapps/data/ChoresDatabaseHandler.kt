package id.skysoft.kotlin.chore.choreapps.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import id.skysoft.kotlin.chore.choreapps.model.*
import java.text.DateFormat
import java.util.*

class ChoresDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        //SQL
        var CREATE_CHORE_TABLE = "CREATE TABLE "+ TABLE_NAME +"(" +
                KEY_ID +" INTEGER PRIMARY KEY," +
                KEY_CHORE_NAME +" TEXT," +
                KEY_CHORE_ASSIGNED_BY +" TEXT," +
                KEY_CHORE_ASSIGNED_TO +" TEXT," +
                KEY_CHORE_ASSIGNED_TIME +" LONG"+ ")"
        db?.execSQL(CREATE_CHORE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        //create table
        onCreate(db)
    }

    //CRUD = Create, Read, Update, Delete
    fun createChore(chore: Chore){
        var db: SQLiteDatabase = writableDatabase
        var values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignTo)
        values.put(KEY_CHORE_ASSIGNED_TIME,System.currentTimeMillis())
        db.insert(TABLE_NAME,null,values)
        Log.d("Data Insert","SUCCESS")
        db.close()
    }

    fun readAChore(id:Int): Chore {
        var db:SQLiteDatabase = writableDatabase
        var cursor: Cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_CHORE_NAME, KEY_CHORE_ASSIGNED_BY, KEY_CHORE_ASSIGNED_TO, KEY_CHORE_ASSIGNED_TIME),
                KEY_ID + "=?", arrayOf(id.toString()),null,null,null,null)
        if(cursor != null) cursor.moveToFirst()
        var chore = Chore()
        chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
        chore.assignBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
        chore.assignTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
        chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

        //format date
        var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
        var formatedDate = dateFormat.format(Date(cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))).time)

        return chore
    }

    fun updateChore(chore:Chore): Int{
        var db: SQLiteDatabase = writableDatabase
        var values:ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        //update a row
        return db.update(TABLE_NAME, values, KEY_ID +"=?", arrayOf(chore.Id.toString()))
    }

    fun deleteChore(chore: Chore){
        var db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID +"=?", arrayOf(chore.Id.toString()))
        db.close()
    }

    fun getChoresCount():Int{
        var db:SQLiteDatabase = readableDatabase
        var countQuery = "SELECT * FROM $TABLE_NAME"
        var curson:Cursor = db.rawQuery(countQuery,null)
        return curson.count
    }

}