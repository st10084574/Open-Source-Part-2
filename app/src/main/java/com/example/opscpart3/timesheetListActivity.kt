package com.example.opscpart3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.content.Intent
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class timesheetListActivity : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var entryList: MutableList<String>
    private lateinit var entryAdapter: ArrayAdapter<String>

    lateinit var entryListView : ListView
    lateinit var returnHomeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet_list)

        entryListView = findViewById(R.id.listViewTimesheet)
        returnHomeButton = findViewById(R.id.returnHomeButton)

        // Initialize entry list and adapter
        entryList = mutableListOf()
        entryAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, entryList)
        entryListView.adapter = entryAdapter

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Get a reference to the Firebase database
        databaseRef = FirebaseDatabase.getInstance().reference.child("timesheet_entries").child(userId)

        // Fetch timesheet entries from Firebase
        fetchTimesheetEntries()

        // List item click listener
        entryListView.setOnItemClickListener { _, _, position, _ ->
            val entry = entryList[position]
            showToast("Clicked on entry: $entry")
            // Handle clicking on an entry (e.g., show details or edit)
        }

        // Return home button click listener
        returnHomeButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish() // Close the current activity
        }
    }

    private fun fetchTimesheetEntries() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                entryList.clear()
                for (entrySnapshot in snapshot.children) {
                    val date = entrySnapshot.child("date").getValue(String::class.java) ?: ""
                    val startTime = entrySnapshot.child("startTime").getValue(String::class.java) ?: ""
                    val endTime = entrySnapshot.child("endTime").getValue(String::class.java) ?: ""
                    val description = entrySnapshot.child("description").getValue(String::class.java) ?: ""
                    val entry = "Date: $date\nTime: $startTime - $endTime\nDescription: $description"
                    entryList.add(entry)
                }
                entryAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Failed to fetch timesheet entries")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}