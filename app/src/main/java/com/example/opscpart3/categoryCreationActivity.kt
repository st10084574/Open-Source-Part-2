package com.example.opscpart3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class categoryCreationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    lateinit var createCategoryBtn : Button
    lateinit var categoryEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_creation)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        categoryEditText = findViewById(R.id.editTextCategoryName)
        createCategoryBtn = findViewById(R.id.buttonCreateCategory)

        createCategoryBtn.setOnClickListener {
            val categoryName = categoryEditText.text.toString().trim()
            if (categoryName.isNotEmpty()) {
                saveCategory(categoryName)
            } else {
                // Show an error message if category name is empty
                Toast.makeText(this, "Please enter category name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveCategory(categoryName: String) {
        // Get the current user's ID
        val userId = auth.currentUser?.uid ?: ""

        // Generate a unique key for the category
        val categoryId = database.child("categories").push().key ?: ""

        // Create a map to represent the category data
        val categoryData = hashMapOf(
            "name" to categoryName
        )

        database.child("categories").child(userId).child(categoryId).setValue(categoryData)
            .addOnSuccessListener {
                // Category saved successfully
                Toast.makeText(this, "Category created successfully", Toast.LENGTH_SHORT).show()
                // Start the Home activity after category creation
                startActivity(Intent(this, HomeActivity::class.java))
                finish() // Close the current activity
            }
            .addOnFailureListener {
                // Error occurred while saving category
                Toast.makeText(this, "Failed to create category", Toast.LENGTH_SHORT).show()
            }
    }
}