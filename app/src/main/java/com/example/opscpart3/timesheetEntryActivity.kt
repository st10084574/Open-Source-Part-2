package com.example.opscpart3

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import java.text.SimpleDateFormat
import java.util.*

class timesheetEntryActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var selectedDate = ""
    private var selectedStartTime = ""
    private var selectedEndTime = ""

    lateinit var datePickerButton: Button
    lateinit var dateTextView: TextView
    lateinit var descriptionEditText: EditText
    lateinit var startTimeButton: Button
    lateinit var endTimeButton: Button
    lateinit var saveEntryButton: Button
    lateinit var categorySpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet_entry)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        datePickerButton = findViewById(R.id.datePickerButton)
        dateTextView = findViewById(R.id.dateTextView)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        startTimeButton = findViewById(R.id.startTimePickerButton)
        endTimeButton = findViewById(R.id.endTimePickerButton)
        saveEntryButton = findViewById(R.id.ButtonSaveEntry)
        categorySpinner = findViewById(R.id.categorySpinner)

        // Set up category spinner
        setupCategorySpinner()

        datePickerButton.setOnClickListener {
            showDatePicker()
        }

        startTimeButton.setOnClickListener {
            showTimePicker { selectedStartTime = it }
        }

        endTimeButton.setOnClickListener {
            showTimePicker { selectedEndTime = it }
        }

        saveEntryButton.setOnClickListener {
            saveEntry()
        }
    }

    private fun setupCategorySpinner() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val categoriesRef = database.child("categories").child(userId)

        val categoriesList = mutableListOf<String>()

        categoriesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                categoriesList.clear()
                for (categorySnapshot in snapshot.children) {
                    val categoryName = categorySnapshot.child("name").getValue(String::class.java)
                    categoryName?.let { categoriesList.add(it) }
                }
                // Update the adapter with the new categories list
                val adapter = ArrayAdapter(this@timesheetEntryActivity, android.R.layout.simple_spinner_item, categoriesList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                categorySpinner.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Failed to load categories")
            }
        })
    }

    private fun showDatePicker() {
        // Date picker logic
    }

    private fun showTimePicker(onTimeSet: (String) -> Unit) {
        // Time picker logic
    }

    private fun saveEntry() {
        // Save entry logic
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}