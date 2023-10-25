package com.example.myfirstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val userNameSignUp: EditText = findViewById(R.id.nameEditTextSingUp)
        val userEmailSignUp: EditText = findViewById(R.id.emailEditTextSingUp)
        val userPasswordSignUp: EditText = findViewById(R.id.passwordEditTextSingUp)

        val signUpButton: Button = findViewById(R.id.signUpButton)

        auth = FirebaseAuth.getInstance()

        signUpButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(userEmailSignUp.text.toString(), userPasswordSignUp.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(userNameSignUp.text.toString())
                            .build()

                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Registro exitoso, puedes navegar a la siguiente pantalla.
                                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                }
                            }


                        val intent = Intent(this, WelcomeActivity::class.java)
                        startActivity(intent)

                    } else {
                        // Registro fallido, maneja el error
                        val errorMessage = task.exception?.message
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}