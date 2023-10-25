package com.example.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SingInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)

        val userEmailSignIn: EditText = findViewById(R.id.emailEditTextSignIn)
        val userPasswordSignIn:EditText = findViewById(R.id.passwordEditTextSignIn)
        val signInButton: Button = findViewById(R.id.signIn_button)

        auth = FirebaseAuth.getInstance()

        signInButton.setOnClickListener {

            auth.signInWithEmailAndPassword(userEmailSignIn.text.toString(), userPasswordSignIn.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Login failed, handle the error
                        val errorMessage = task.exception?.message
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                        // You can display an error message to the user.
                    }
                }
        }

    }
}