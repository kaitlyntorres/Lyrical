package com.example.lyrical

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog

class guestActivity: AppCompatActivity()  {

    private lateinit var voice: ImageButton
    private lateinit var textv: TextView
    private lateinit var logout: Button

    private val speechCode = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guest)
         textv = findViewById<TextView>(R.id.textBox)
        logout = findViewById<Button>(R.id.logout)
        voice = findViewById<ImageButton>(R.id.voice)
        voice.setOnClickListener {
            voiceToText()
        }
        logout.setOnClickListener {
            logout()
        }
    }
   private fun voiceToText(){
           val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
               //free form speech
               putExtra(
                   RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                   RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
               )
               intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak here: ")
           }
       startActivityForResult(intent,speechCode)





    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == speechCode && resultCode == Activity.RESULT_OK) {
            val result=data?.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)
            textv.text=result?.get(0).toString()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun logout()
    {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Are you sure you would like to logout?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                dialog, id -> val i = Intent(this, MainActivity::class.java)
                startActivity(i)
        })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alertdialog = dialog.create()
        // set title for alert dialog box
        alertdialog.setTitle("Logout")
        // show alert dialog
        alertdialog.show()
    }

}