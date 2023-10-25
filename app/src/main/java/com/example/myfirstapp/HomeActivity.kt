package com.example.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class HomeActivity : AppCompatActivity() {

    private lateinit var nameEventText: TextView
    private lateinit var dateEventText: TextView
    private lateinit var numberGuestsText: TextView
    private lateinit var descriptionEventText: TextView

    private lateinit var countEventText:TextView
    private lateinit var totalGuestsText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val db = Firebase.firestore

        val subtitle: TextView = findViewById(R.id.subtitleTextView)
        val createEventButton: Button = findViewById(R.id.addEvent)



        val user = Firebase.auth.currentUser
        user?.let {
            val name = it.displayName
            val email = it.email

            subtitle.text = name
        }

        db.collection("events")
            .get()
            .addOnSuccessListener { result ->

                if (!result.isEmpty) {

                    // Get count of events
                    val eventCount = result.size()
                    val countText = "$eventCount Eventos"
                    countEventText = findViewById(R.id.countEventText)
                    countEventText.text = countText

                    // Get total of number of guests
                    var totalGuests = 0

                    /*
                    for (document in result) {
                        val eventNumberGuests = document.getLong("NumberGuests")
                        if (eventNumberGuests != null) {
                            totalGuests += eventNumberGuests.toInt()
                        }
                    }*/

                    totalGuestsText = findViewById(R.id.totalGuestsText)
                    totalGuestsText.text = "$totalGuests Personas"


                    val document = result.documents[0]
                    val eventName = document.getString("Name")
                    val eventDate = document.getString("Date")
                    val numberGuests = document.getString("NumberGuests")
                    val eventDescription = document.getString("Description")

                    nameEventText = findViewById(R.id.nameEventText)
                    dateEventText = findViewById(R.id.dateEventText)
                    descriptionEventText = findViewById(R.id.descriptionEventText)
                    numberGuestsText = findViewById(R.id.numberGuestsEventText)

                    numberGuestsText.text = numberGuests
                    nameEventText.text = eventName
                    dateEventText.text = eventDate
                    descriptionEventText.text = eventDescription
                }
            }
            .addOnFailureListener { exception ->
                Log.w("READ FAIL", "Error getting documents.", exception)
            }


        createEventButton.setOnClickListener {
            val intent = Intent(this, FormEventActivity::class.java)
            startActivity(intent)
        }


    }
}