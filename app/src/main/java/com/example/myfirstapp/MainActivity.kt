package com.example.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signInButton:Button = findViewById(R.id.singInButton)
        val singUpButton: Button = findViewById(R.id.singUpButton)
        val email: EditText = findViewById(R.id.emailTextEdit)
        val password: EditText = findViewById(R.id.passwordTextEdit)


        auth = FirebaseAuth.getInstance()

        signInButton.setOnClickListener {

            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login successful, navigate to the next screen
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        // You can start a new activity or perform other actions here.
                    } else {
                        // Login failed, handle the error
                        val errorMessage = task.exception?.message
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                        // You can display an error message to the user.
                    }
                }
        }

        singUpButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration successful, you can navigate to the next screen.
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    } else {
                        // Registration failed, handle the error
                        val errorMessage = task.exception?.message
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                        // You can display an error message to the user.
                    }
                }
        }


    }


}