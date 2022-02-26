package com.example.lyrical
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class reset: AppCompatActivity() {
    private lateinit var e: EditText
    private lateinit var btnReset: Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot)
        e = findViewById(R.id.email)
        btnReset = findViewById(R.id.forgotp)
        auth = Firebase.auth
        btnReset.setOnClickListener {
            f()

        }
    }
    private fun f(){
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

                        }

                    } else {
                        Log.d(TAG, "Email sent")
                        finish()


                    }
                }

        }
    }


}
