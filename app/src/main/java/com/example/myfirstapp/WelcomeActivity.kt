package com.example.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val signInButton: Button = findViewById(R.id.sign_in_button)
        val signUpButton: Button = findViewById(R.id.sign_up_button)

        signInButton.setOnClickListener {
            val intent = Intent(this, SingInActivity::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}