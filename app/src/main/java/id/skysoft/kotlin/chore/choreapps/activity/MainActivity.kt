package id.skysoft.kotlin.chore.choreapps.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ProgressBar
import android.widget.Toast
import id.skysoft.kotlin.chore.choreapps.R
import id.skysoft.kotlin.chore.choreapps.data.ChoresDatabaseHandler
import id.skysoft.kotlin.chore.choreapps.model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbHandler: ChoresDatabaseHandler? = null
    var progressdialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressdialog = ProgressDialog(this)
        dbHandler = ChoresDatabaseHandler(this)

        btnSave.setOnClickListener {
            if (!TextUtils.isEmpty(enterChoreId.text.toString()) && !TextUtils.isEmpty(assignToId.text.toString())
                    && !TextUtils.isEmpty(assignById.text.toString())){
                progressdialog!!.setMessage("Saving...")
                progressdialog!!.show()

                var chore = Chore()
                chore.choreName = enterChoreId.text.toString()
                chore.assignTo = assignToId.text.toString()
                chore.assignBy = assignById.text.toString()
                saveToDB(chore)
                progressdialog!!.cancel()
                val intent = Intent(this, ChoreListActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"Please enter the data", Toast.LENGTH_LONG).show()
            }
        }

        //Read from database
        //var chores: Chore = dbHandler!!.readAChore(1)
        //println(chores.choreName)
    }

    fun saveToDB(chore: Chore){
        dbHandler!!.createChore(chore)
    }
}
