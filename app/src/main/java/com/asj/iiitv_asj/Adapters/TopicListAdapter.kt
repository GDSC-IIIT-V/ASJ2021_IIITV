package com.asj.iiitv_asj.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asj.iiitv_asj.R
import com.asj.iiitv_asj.TopicListActivity
import com.asj.iiitv_asj.TopivViewActivity
import java.util.ArrayList


internal class TopicListAdapter(
    var recyclerView: RecyclerView,
    context: Context,
    coursrName: ArrayList<String>,
    coursrUrl: ArrayList<String>
) :
    RecyclerView.Adapter<TopicListAdapter.ViewHolder>() {
    var courseName = ArrayList<String>()
    var courseUrl = ArrayList<String>()
    var context: Context
    var cName: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameofFile.text = courseName[position]
    }

    override fun getItemCount(): Int {
        return courseName.size
    }

    fun update(name: String,url: String) {
        courseName.add(name)
        courseUrl.add(url)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameofFile: TextView
        var weburl: TextView? = null

        init {
            nameofFile = itemView.findViewById(R.id.webName)
            itemView.setOnClickListener { view ->
                val position = recyclerView.getChildLayoutPosition(view)
                val intent = Intent(context, TopivViewActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("tName", courseName[position])
                intent.putExtra("videoUrl", courseUrl[position])
                context.startActivity(intent)
            }
        }
    }

    init {
        courseName = coursrName
        courseUrl = coursrUrl
        this.context = context
    }
}