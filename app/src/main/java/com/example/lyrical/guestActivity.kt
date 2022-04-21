package com.example.lyrical

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class guestActivity: AppCompatActivity()    {


    private lateinit var voice: ImageButton
    private lateinit var textv: EditText
    private lateinit var logout: Button
    private lateinit var search: Button
    private lateinit var  history:Button
    private lateinit var wemail: TextView

    private val speechCode = 100
    private lateinit var searchString: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        getSupportActionBar()?.hide()
        setContentView(R.layout.guest)
        wemail=findViewById<TextView>(R.id.welcome)
        textv = findViewById<EditText>(R.id.textBox)
        logout = findViewById<Button>(R.id.logout)
        history = findViewById<Button>(R.id.history)
        val spinner = findViewById<Spinner>(R.id.lang)
        val lang = resources.getStringArray(R.array.Languages)
        //search = findViewById<Button>(R.id.logout)
        voice = findViewById<ImageButton>(R.id.voice)




        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, lang
            )
            spinner.adapter = adapter
        }




        val email_name=intent.getStringExtra("Email")
        val email_word= email_name?.substringBefore("@")
        if(email_name.isNullOrEmpty()) {
            Firebase.auth.signOut()
            wemail.setText("Welcome Guest")
            history.visibility = View.INVISIBLE
        }
        else{
            wemail.setText("Welcome "+ email_word)}

        voice.setOnClickListener {
            voiceToText()
        }



        //when user hits submit on keypad, calls fetch json function
        textv.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Call your code here
                var sendSpeech = textv.text.toString()
                searchString = sendSpeech
                //val ahh = ApiActivity.fetchJSON(sendSpeech)

                val it = Intent(this@guestActivity, ApiActivity::class.java)
                it.putExtra("keyOne", searchString)
                startActivity(it)

            }

            false
        }
        logout.setOnClickListener {
            logout()
        }
        //search.setOnClickListener {
        //var sendSpeech = textv.text.toString()
        // ApiActivity.fetchJSON(sendSpeech)
        //}

        history.setOnClickListener {
            val it = Intent(this@guestActivity, h::class.java)
            // it.putExtras(b);
            startActivity(it);
            //it.putExtra("arr", "period ");

        }


    }
    private fun voiceToText(){
        val spinner = findViewById<Spinner>(R.id.lang)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            //free form speech
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM

            )

            putExtra(RecognizerIntent.EXTRA_LANGUAGE, voiceLang(spinner));
            // putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr");
            //putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");



            //putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak here: ")
        }
        startActivityForResult(intent,speechCode)}



    // This is where you process the intent and extract the speech text from the intent.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == speechCode && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0)
                }
            // Do something with spokenText.
            textv.setText(spokenText)
            //send over to new kotlin for display


            var sendSpeech = spokenText.toString()
            searchString = sendSpeech
            //val ahh = ApiActivity.fetchJSON(sendSpeech)

            val it = Intent(this@guestActivity, ApiActivity::class.java)
            it.putExtra("keyOne", searchString)
            startActivity(it)


        }
        super.onActivityResult(requestCode, resultCode, data)

    }



    private fun logout()
    {
        Firebase.auth.signOut()
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
    // function that decides language based off of voice input
    private fun voiceLang(spinner:Spinner):String{
        val l:String

        val text: String = spinner.getSelectedItem().toString()
        if(text=="Spanish"){
            //changes language to spanish
            l= "es-co";
        }
        else if(text=="French"){
            l="fr"
        }
        else if(text=="Italian"){
            l="it"
        }


        else{
            //stays default (English)
            l="en_US"
        }

        return l
    }

}