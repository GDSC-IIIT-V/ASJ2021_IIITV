package com.asj.iiitv_asj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Entity
import com.asj.iiitv_asj.Adapters.CategoryAdapter
import com.google.firebase.database.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var categoryRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryRecyclerView = findViewById(R.id.recycler_main)
        categoryRecyclerView.layoutManager = LinearLayoutManager(applicationContext)

        databaseActivities()

    }
    private fun databaseActivities() {
        val categoryAdapter = CategoryAdapter(categoryRecyclerView, applicationContext, ArrayList<String>())
        categoryRecyclerView.adapter = categoryAdapter
        var databaseReference:DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Courses")
        Toast.makeText(this, "Loading Categories", Toast.LENGTH_SHORT).show()
        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val courseName = snapshot.key
                if (courseName != null) {
                    (categoryRecyclerView.adapter as CategoryAdapter).update(courseName)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }


}