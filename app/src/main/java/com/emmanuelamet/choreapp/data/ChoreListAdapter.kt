package com.emmanuelamet.choreapp.data

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.emmanuelamet.choreapp.R
import com.emmanuelamet.choreapp.activity.ChoreListActivity
import com.emmanuelamet.choreapp.model.Chore
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListAdapter(private  val list: ArrayList<Chore>, private val context: Context): RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ChoreListAdapter.ViewHolder {
        //Create view from xml
        var view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return  ViewHolder(view, context, list)
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: ChoreListAdapter.ViewHolder, position: Int) {
           holder.bindView(list[position])
    }

    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<Chore>):RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var mContext = context
        var mList = list
        var choreName = itemView.findViewById(R.id.listChoreName) as TextView
        var assignedBy = itemView.findViewById(R.id.listAssignedBy) as TextView
        var assignedDate = itemView.findViewById(R.id.listDate) as TextView
        var assignedTo = itemView.findViewById(R.id.listAssignedTo) as TextView
        var deleteButton = itemView.findViewById(R.id.btnDelete) as Button
        var editButton = itemView.findViewById(R.id.btnEdit) as Button




        fun bindView(chore: Chore){
            choreName.text = chore.choreName
            assignedBy.text = chore.assignedBy
            assignedTo.text = chore.assignedTo
            assignedDate.text = chore.showHumanDate(System.currentTimeMillis())

            deleteButton.setOnClickListener(this)

            editButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var mPosition: Int = adapterPosition
            var chore = mList[mPosition]
            when(v!!.id){
                deleteButton.id -> {
                    deleteChore(chore.id!!)
                    mList.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    Toast.makeText( mContext, "Data deleted successful", Toast.LENGTH_SHORT).show()
                }
                editButton.id -> {
                    editChore(chore)
                    Toast.makeText(mContext, "Chore edited successfully.", Toast.LENGTH_SHORT).show()}
            }

        }
        fun deleteChore(id:Int){
            var db: ChoresDatabaseHandler = ChoresDatabaseHandler(mContext)
            db.deleteChore(id)
        }

    }

    fun editChore(chore: Chore){

        var dialogBuilder: AlertDialog.Builder? = null
        var dialog:AlertDialog? = null
        var dbHandler: ChoresDatabaseHandler = ChoresDatabaseHandler(context)
        var view = LayoutInflater.from(context).inflate(R.layout.popup, null)
        var choreName = view.popEnterChoreName
        var assignedBy = view.popEnterAssignedBy
        var assigendTo = view.popEnterAssignedTo
        var saveButton = view.btnPopSave

        dialogBuilder = AlertDialog.Builder(context).setView(view)
        dialog = dialogBuilder!!.create()
        dialog?.show()

        saveButton.setOnClickListener {
            var name = choreName.text.toString().trim()
            var aBy =  assignedBy.text.toString().trim()
            var aTo = assigendTo.text.toString().trim()

            if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(aBy)
                && !TextUtils.isEmpty(aTo)) {
                //var chore = Chore()

                chore.choreName = name
                chore.assignedTo = aTo
                chore.assignedBy = aBy

                dbHandler!!.updateChore(chore)
                notifyDataSetChanged()

                dialog!!.dismiss()

//                startActivity(Intent(this, ChoreListActivity::class.java))
//                finish()




            } else {
                //Toast.makeText(this, "Oops, something went wrong, try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }



}