package com.example.lyrical

/*
* Authors: Omer Basar, Kaitlyn Torres, Charles Howard
* File: spotifySong
* Purpose: allows the web player for the song to be shown to the user
*
* */

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class songPlayer: AppCompatActivity() {

    private lateinit var webView: WebView;
    private lateinit var yes: Button;
    private lateinit var no: Button;
    var k=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        getSupportActionBar()?.hide()
        setContentView(R.layout.testspotify)
        webView = findViewById(R.id.webView)
        yes = findViewById(R.id.checkyes)
        no = findViewById(R.id.checkno)

        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        // Retrieve information from ApiActivity
        val intent = intent
        val hashMap = intent.getStringArrayExtra("keyTwo")
        println("hashmap goes here: $hashMap")
        println(hashMap.contentToString())
        var trackID:String?=""


        if (hashMap != null) {

                trackID=hashMap.elementAt(k).substringAfter("TrackID: ").substringBefore(",")
                html(trackID)

                val user = Firebase.auth.currentUser
                val db = Firebase.firestore


                yes.setOnClickListener {

                    println("USERRR: " + user)

                    yes.setBackground(getResources().getDrawable(R.drawable.green))

                    Handler().postDelayed(
                        Runnable { yes.setBackgroundColor(yes.getContext().getResources().getColor(R.color.white))
                            yes.setBackground(getResources().getDrawable(R.drawable.roundbutton))},
                        500
                    )

                    clearHTML()

                    var songName = hashMap.elementAt(k).substringAfter("Full Title: ").substringBefore(",")


                    if (user != null) {
                        db.collection("users").document(user.uid).update("Song Info", FieldValue.arrayUnion(songName))
                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!")}
                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                    }


                    val user = FirebaseAuth.getInstance().currentUser


                    var userEmail:String?=""

                    if (user != null) {
                        //println(user)
                        userEmail = user.email
                    } else {
                        // No user is signed in
                    }


                    val it = Intent(this@songPlayer, homeActivity::class.java)

                    //TODO(MAKE EMAIL )
                    it.putExtra("Email",userEmail)
                    // it.putExtras(b);
                    startActivity(it);
                    //it.putExtra("arr", "period ");

                }


                no.setOnClickListener{
                    no.setBackground(getResources().getDrawable(R.drawable.red))

                    Handler().postDelayed(
                        Runnable { no.setBackgroundColor(no.getContext().getResources().getColor(R.color.white))
                                 no.setBackground(getResources().getDrawable(R.drawable.roundbutton))},
                        500
                    )

                    iterate(hashMap)

                }

            }
        }
        //var trackID = hashMap?.first()?.substringAfter("TrackID: ")?.substringBefore(",")




private fun iterate(hashMap: Array<String>?){

    k=k+1
    if (hashMap != null) {
        if(k>=hashMap.size){

            clearHTML()

            val toast = Toast.makeText(applicationContext, "Please try a new search", Toast.LENGTH_LONG)
            toast.show()

            val user = FirebaseAuth.getInstance().currentUser
            var userEmail:String?=""

            if (user != null) {
                userEmail = user.email
            } else {
                // No user is signed in
            }


            val it = Intent(this@songPlayer, homeActivity::class.java)

            //TODO(MAKE EMAIL )
            it.putExtra("Email",userEmail)
            // it.putExtras(b);
            startActivity(it);
            //it.putExtra("arr", "period ");


        }
        else{
            var trackID= hashMap?.elementAt(k)?.substringAfter("TrackID: ")?.substringBefore(",")

            html(trackID)

        }
    }
}


private fun html(trackID:String?){
    val data = "<html>\n" +
            "    <head></head>\n" +
            "    \n" +
            "    <body style=\"background-color:black;\">\n" +
            "    \n" +
            "      <iframe style=\"border-radius:12px\" src=\"https://open.spotify.com/embed/track/" +
            trackID +
            "?utm_source=generator\"width=\"95%\" height=\"450\" frameBorder=\"0\" allowfullscreen=\"\"allow=\"autoplay; clipboard-write; encrypted-media; fullscreen; picture-in-picture\"></iframe>"
    "    </body>\n" +
            "    \n" +
            "    </html>\n"

    val encodedHtml: String = Base64.encodeToString(data.toByteArray(), Base64.NO_PADDING)
    webView.loadData(encodedHtml, "text/html", "base64");
    webView.settings.javaScriptEnabled = true
}
    private fun clearHTML(){
        val data = "<html>\n" +
                "    <head></head>\n" +
                "    \n" +
                "    <body>\n" +
                "    </body>\n" +
                "    \n" +
                "    </html>\n"

        val encodedHtml: String = Base64.encodeToString(data.toByteArray(), Base64.NO_PADDING)
        webView.loadData(encodedHtml, "text/html", "base64");
        webView.settings.javaScriptEnabled = true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val it = Intent(this@songPlayer, homeActivity::class.java)
        startActivity(it)
    }

}









