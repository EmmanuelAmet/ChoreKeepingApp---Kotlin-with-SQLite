package com.emmanuelamet.choreapp.activity

import android.app.AlertDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emmanuelamet.choreapp.R
import com.emmanuelamet.choreapp.data.ChoreListAdapter
import com.emmanuelamet.choreapp.data.ChoresDatabaseHandler
import com.emmanuelamet.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.popup.*
import kotlinx.android.synthetic.main.popup.view.*
import kotlinx.android.synthetic.main.popup.view.popEnterAssignedBy

class ChoreListActivity : AppCompatActivity() {
    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    private var choreListItems: ArrayList<Chore>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbHandler: ChoresDatabaseHandler? = null
    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog:AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        dbHandler = ChoresDatabaseHandler(this)


        choreList = ArrayList<Chore>()
        choreListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = ChoreListAdapter(choreListItems!!, this)

        //Setup List in Recycler view
        recycleViewId.layoutManager = layoutManager
        recycleViewId.adapter = adapter

        //Load chore data
        choreList = dbHandler!!.readChores()
        choreList!!.reverse()


        for (c in choreList!!.iterator()) {


//            Log.d("====ID=====", c.id.toString())
//            Log.d("====Name=====", c.choreName)
//            Log.d("====Date=====", chore.showHumanDate(c.timeAssigned!!))
//            Log.d("====aTo=====", c.assignedBy)
//            Log.d("====aBy=====", c.assignedTo)
            val chore = Chore()
            chore.choreName = "Chore Name: ${c.choreName}"
            chore.assignedTo = "Assigned To: ${c.assignedTo}"
            chore.assignedBy = "Assigned By: ${c.assignedBy}"
            chore.showHumanDate(c.timeAssigned!!)
            chore.id = c.id

            choreListItems!!.add(chore)
            //Log.d("List", c.choreName)
        }


        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item!!.itemId == R.id.add_menu_button) {
            createPopupDialog()
            //Toast.makeText(this, "Add tapped", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun createPopupDialog() {

        var view = layoutInflater.inflate(R.layout.popup, null)
        var choreName = view.popEnterChoreName
        var assignedBy = view.popEnterAssignedBy
        var assigendTo = view.popEnterAssignedTo
        var saveButton = view.btnPopSave

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog?.show()

        saveButton.setOnClickListener {
            val name = choreName.text.toString().trim()
            val aBy =  assignedBy.text.toString().trim()
            val aTo = assigendTo.text.toString().trim()

            if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(aBy)
                && !TextUtils.isEmpty(aTo)) {
                val chore = Chore()

                chore.choreName = name
                chore.assignedTo = aTo
                chore.assignedBy = aBy

                dbHandler!!.createChore(chore)

                dialog!!.dismiss()

                startActivity(Intent(this, ChoreListActivity::class.java))
                finish()




            } else {

            }
        }


    }
}
