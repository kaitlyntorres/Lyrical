package com.example.lyrical

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException


private lateinit var fbutton: Button
private lateinit var e: EditText

class forgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot)
        e = findViewById(R.id.email)
        fbutton = findViewById(R.id.submitbutton)
        lateinit var auth: FirebaseAuth
        fbutton.setOnClickListener {
            val eforgot = e.text.toString()
            if (eforgot.isEmpty()) {
                e.setError("Email can't be empty")
            } else {
                auth.sendPasswordResetEmail(eforgot)
                    .addOnCompleteListener { task ->
                        if (!task.isSuccessful) {

                            try {
                                throw task.exception!!
                            } // if user enters wrong email.
                            catch (invalidEmail: FirebaseAuthInvalidUserException) {
                                Log.d(TAG, "onComplete: invalid_email")

                                // TODO: take your actions!
                            }

                        } else {
                            Log.d(TAG, "Email sent")


                        }
                    }

            }
        }
    }
}


