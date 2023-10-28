package com.example.myfirstapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EventsActivity : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var currentEventDate: TextView
    private lateinit var nameCurrentEvent: TextView
    private lateinit var descriptionCurrentEvent: TextView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val currentDay = LocalDate.now()
        setDayBackground(currentDay)
        getDaysOfCurrenWeek()
        getDataOfEvents()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDayBackground(currentDay: LocalDate) {
        val daysOfWeek = mutableListOf<TextView>(
            findViewById(R.id.monday),
            findViewById(R.id.tuesday),
            findViewById(R.id.wednesday),
            findViewById(R.id.thursday),
            findViewById(R.id.friday),
            findViewById(R.id.saturday),
            findViewById(R.id.sunday)
        )

        val today = currentDay.dayOfWeek
        val dayOfWeekTexts = listOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do")

        for (i in 0 until 7) {
            if (today.name.take(2).equals(dayOfWeekTexts[i], ignoreCase = true)) {
                daysOfWeek[i].setBackgroundResource(R.drawable.mini_card_icon)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDaysOfCurrenWeek() {
        val currentDay = LocalDate.now()

        val firstDayOfWeek = currentDay.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

        val daysOfWeek = mutableListOf<LocalDate>()

        for (i in 0 until 7) {
            val day = firstDayOfWeek.plusDays(i.toLong())
            daysOfWeek.add(day)
        }

        val mondayNumberText: TextView = findViewById(R.id.mondayNumber)
        val tuesdayNumberText: TextView = findViewById(R.id.tuesdayNumber)
        val wednesdayNumberText: TextView = findViewById(R.id.wednesdayNumber)
        val thursdayNumberText: TextView = findViewById(R.id.thursdayNumber)
        val fridayNumberText: TextView = findViewById(R.id.fridayNumber)
        val saturdayNumberText: TextView = findViewById(R.id.saturdayNumber)
        val sundayNumberText: TextView = findViewById(R.id.sundayNumber)

        for ((i, textView) in listOf(mondayNumberText, tuesdayNumberText, wednesdayNumberText, thursdayNumberText, fridayNumberText, saturdayNumberText, sundayNumberText).withIndex()) {
            // Establece el número del día en el TextView correspondiente
            textView.text = daysOfWeek[i].dayOfMonth.toString()
        }
    }

    private fun getDataOfEvents() {

        val db = Firebase.firestore

        currentEventDate = findViewById(R.id.dateEventText)
        nameCurrentEvent = findViewById(R.id.nameEventText)
        descriptionCurrentEvent = findViewById(R.id.descriptionEventText)

        db.collection("events")
            .get()
            .addOnSuccessListener { result ->

                if (!result.isEmpty) {

                    val document = result.documents[0]
                    val eventName = document.getString("Name")
                    val eventDate = document.getString("Date")
                    val numberGuests = document.getString("NumberGuests")
                    val eventDescription = document.getString("Description")

                    nameCurrentEvent = findViewById(R.id.nameEventText)
                    currentEventDate = findViewById(R.id.dateEventText)
                    descriptionCurrentEvent = findViewById(R.id.descriptionEventText)

                    nameCurrentEvent.text = eventName
                    currentEventDate.text = eventDate
                    descriptionCurrentEvent.text = eventDescription
                }
            }
            .addOnFailureListener { exception ->
                Log.w("READ FAIL", "Error getting documents.", exception)
            }

    }
}