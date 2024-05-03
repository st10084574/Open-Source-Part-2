package com.example.opscpart3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.opscpart3.R.id.buttonLogout

class HomeActivity : AppCompatActivity() {

    lateinit var buttonViewTimesheet : Button
    lateinit var buttonAddEntry : Button
    lateinit var buttonCreateCategory : Button
    lateinit var buttonLogout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        buttonViewTimesheet = findViewById(R.id.buttonViewTimesheet)
        buttonCreateCategory = findViewById(R.id.buttonCreateCategory)
        buttonAddEntry = findViewById(R.id.buttonAddEntry)
        buttonLogout = findViewById(R.id.buttonLogout)
        buttonViewTimesheet.setOnClickListener {
            startActivity(Intent(this, timesheetListActivity::class.java))
        }

        buttonAddEntry.setOnClickListener {
            startActivity(Intent(this, timesheetEntryActivity::class.java))
        }

        buttonCreateCategory.setOnClickListener {
            startActivity(Intent(this, categoryCreationActivity::class.java))
        }

        buttonLogout.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Finish the current activity
        }
    }

}