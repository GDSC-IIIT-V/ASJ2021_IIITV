package com.asj.iiitv_asj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asj.iiitv_asj.Adapters.CategoryAdapter
import com.asj.iiitv_asj.Adapters.TopicListAdapter
import com.google.firebase.database.*
import java.util.ArrayList

class TopicListActivity : AppCompatActivity() {
    private lateinit var topicListRecyclerView: RecyclerView
    private lateinit var courseName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_list)

        val intent = intent
        courseName = intent.getStringExtra("tName").toString()

        val actionBar = supportActionBar
        actionBar!!.title = courseName

        topicListRecyclerView = findViewById(R.id.recycler_topic_list)
        topicListRecyclerView.layoutManager = LinearLayoutManager(applicationContext)

        Toast.makeText(this, courseName, Toast.LENGTH_SHORT).show()
        databaseActivities(courseName)

    }

    private fun databaseActivities(type:String) {
        val topicListAdapter = TopicListAdapter(topicListRecyclerView, applicationContext, ArrayList<String>(),ArrayList<String>())
        topicListRecyclerView.adapter = topicListAdapter
        var databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Courses").child(type).child("Topic")
        Toast.makeText(this, "Loading Topics", Toast.LENGTH_SHORT).show()
        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val courseName = snapshot.key
                var videoUrl = ""
                if (snapshot.hasChild("P")){
                    videoUrl = snapshot.child("P").value as String
                }else if (snapshot.hasChild("P1")){
                    videoUrl = snapshot.child("P1").value as String
                }

                if (courseName != null) {
                    (topicListRecyclerView.adapter as TopicListAdapter).update(courseName,
                        videoUrl as String
                    )
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

}