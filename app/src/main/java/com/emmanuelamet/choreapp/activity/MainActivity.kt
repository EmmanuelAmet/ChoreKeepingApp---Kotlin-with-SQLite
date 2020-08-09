package com.emmanuelamet.choreapp.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emmanuelamet.choreapp.R
import com.emmanuelamet.choreapp.data.ChoreListAdapter
import com.emmanuelamet.choreapp.data.ChoresDatabaseHandler

import com.emmanuelamet.choreapp.model.*
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var dbHandler: ChoresDatabaseHandler? = null

    //var progressDialog: ProgressDialog? = null
    /*
    var enterChore = txtChoreName
    var assignedBy = txtAssignedBy
    var assignedTo = txtAssignedTo
    var saveChore = btnSave

     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDatabaseHandler(this)
        CheckDB()



        btnSave.setOnClickListener{
            //!!.setMessage("Saving...")
            // progressDialog!!.show()

            if(!TextUtils.isEmpty(txtChoreName.text.toString())
                && !TextUtils.isEmpty(txtAssignedBy.text.toString())
                && !TextUtils.isEmpty(txtAssignedTo.text.toString())){
                //Save Data
                var chore = Chore()
                chore.choreName = txtChoreName.text.toString()
                chore.assignedBy = txtAssignedBy.text.toString()
                chore.assignedTo = txtAssignedTo.text.toString()
                saveToDB(chore)
                //progressDialog!!.cancel()
                Toast.makeText(this, "Data save successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ChoreListActivity::class.java))
            }else{
                Toast.makeText(this, "All fields requied", Toast.LENGTH_SHORT).show()
            }
        }




/*
        var chore = Chore()
        chore.choreName = "Wash my Car"
        chore.assignedTo = "Henry"
        chore.assignedBy = "Emmanuel"

        //dbHandler!!.createChore(chore)

        //Read from database
            //var chores: Chore = dbHandler!!.readAChore(2)
        var chores: ArrayList<Chore> = dbHandler!!.readChores()

        for(c in chores){
            Log.i("Item: ", c.assignedBy + " Time" + c.showHumanDate(c.timeAssigned!!))
        }

 */

    }

    fun CheckDB(){

        if(dbHandler!!.getChoresCount() > 0){
            startActivity(Intent(this, ChoreListActivity::class.java))
        }
    }
    fun saveToDB(chore: Chore){
        dbHandler!!.createChore(chore)
    }


}
