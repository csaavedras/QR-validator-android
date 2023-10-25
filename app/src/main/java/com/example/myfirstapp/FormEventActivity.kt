package com.example.myfirstapp

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale


class FormEventActivity : AppCompatActivity() {

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private lateinit var dateEditText: EditText

    private lateinit var name: EditText
    private lateinit var address: EditText
    private lateinit var guestsNumber: EditText
    private lateinit var description: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_event)

        val db = Firebase.firestore

        val submitButton: Button = findViewById(R.id.submitButton)
        name = findViewById(R.id.nameEventEditText)
        address = findViewById(R.id.editTextTextPostalAddress)
        guestsNumber = findViewById(R.id.editTextNumberGuests)
        description = findViewById(R.id.editTextDescription)

        dateEditText = findViewById(R.id.editTextDate)
        dateEditText.setOnClickListener {
            showDatePickerDialog()
        }



        submitButton.setOnClickListener {

            if (validateForm()) {

                val eventName = name.text.toString()
                val eventDate = dateEditText.text.toString()
                val eventAddress = address.text.toString()
                val eventGuests = guestsNumber.text.toString()
                val eventDescription = description.text.toString()

                // Create a new user with a first, middle, and last name
                val event = hashMapOf(
                    "Name" to eventName,
                    "Date" to eventDate,
                    "Address" to eventAddress,
                    "NumberGuests" to eventGuests,
                    "Description" to eventDescription
                )

                // Add a new document with a generated ID
                db.collection("events")
                    .add(event)
                    .addOnSuccessListener { documentReference ->
                        Log.d("SEND SUCCESS", "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("SEND FAIL", "Error adding document", e)
                    }

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }



        }

    }


    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, day   ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                val selectedDate = dateFormat.format(calendar.time)
                dateEditText.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Select the current date
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (name.text.isNullOrEmpty()) {
            name.error = "El nombre del evento es requerido"
            isValid = false
        }

        if (address.text.isNullOrEmpty()) {
            address.error = "La dirección del evento es requerido"
            isValid = false
        }

        if (dateEditText.text.isNullOrEmpty()) {
            dateEditText.error = "La fecha del evento es requerido"
            isValid = false
        }

        if (guestsNumber.text.isNullOrEmpty()) {
            guestsNumber.error = "El numero de invitados es requerido"
            isValid = false
        }

        return isValid
    }


}