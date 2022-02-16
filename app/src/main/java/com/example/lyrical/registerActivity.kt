package com.example.lyrical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import com.google.android.material.snackbar.Snackbar
import android.content.Intent
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer




//A toast is a view containing a quick little message for the user


class registerActivity: AppCompatActivity() {
    // intialize variables
    private lateinit var email: EditText
    private lateinit var passwd: EditText
    private lateinit var confirmpasswd: EditText
    private lateinit var btnRegister: Button
    private lateinit var lccm: ImageView
    private lateinit var uccm: ImageView
    private lateinit var digitcm: ImageView
    private lateinit var sccm: ImageView
    private lateinit var lctv: TextView
    private lateinit var uctv: TextView
    private lateinit var digittv: TextView
    private lateinit var sctv: TextView

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        // find variable by id from xml
        email = findViewById(R.id.email)
        passwd = findViewById(R.id.password)
        confirmpasswd = findViewById(R.id.confirmpassword)
        lccm = findViewById(R.id.lowercase_checkmark)
        uccm = findViewById(R.id.uppercase_checkmark)
        digitcm = findViewById(R.id.digit_checkmark)
        sccm = findViewById(R.id.sc_checkmark)
        lctv = findViewById(R.id.lowercaseText)
        uctv = findViewById(R.id.uppercaseText)
        digittv = findViewById(R.id.digitText)
        sctv = findViewById(R.id.scText)

        var lcTrue = 0
        var ucTrue = 0
        var digitTrue = 0
        var scTrue = 0


        btnRegister = findViewById(R.id.sumbitregister)

        // connect to firebase
        auth = Firebase.auth
        // on click call function

        val passwordTest = passwordChecker()
        passwd.addTextChangedListener(passwordTest)


        passwordTest.lc.observe(this, Observer { value ->
            lcTrue = displayStatusofPassword(value, lccm, lctv)
            tester(lcTrue, ucTrue, digitTrue, scTrue)

        })
        passwordTest.uc.observe(this, Observer { value ->
            ucTrue = displayStatusofPassword(value, uccm, uctv)
            tester(lcTrue, ucTrue, digitTrue, scTrue)


        })
        passwordTest.d.observe(this, Observer { value ->
            digitTrue = displayStatusofPassword(value, digitcm, digittv)
            tester(lcTrue, ucTrue, digitTrue, scTrue)


        })
        passwordTest.spc.observe(this, Observer { value ->
            scTrue = displayStatusofPassword(value, sccm, sctv)
            tester(lcTrue, ucTrue, digitTrue, scTrue)


        })




        btnRegister.setOnClickListener {
            registerUser()
            //tester(lcTrue,ucTrue,digitTrue,scTrue)
        }

    }

    private fun tester(num: Int, num2: Int, num3: Int, num4: Int) {



        btnRegister.isEnabled = num == 1 && num2 == 1 && num3 == 1 && num4 == 1


    }

    private fun displayStatusofPassword(value: Int, imageView: ImageView, textView: TextView): Int {
        var c = 0


        if (value == 1) {
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.Green))
            textView.setTextColor(ContextCompat.getColor(this, R.color.Green))
            c = 1


        } else {
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.darkGray))
            textView.setTextColor(ContextCompat.getColor(this, R.color.darkGray))
            c = 0

        }

        return c


    }

    private fun registerUser() {
        // converts user input to string
        val e = email.text.toString()
        val password = passwd.text.toString()
        val confirmpassword = confirmpasswd.text.toString()
        // must have at least 1 uppercase, lowercase, number, special char , and be at least 8 char long


        if (e.isEmpty()) {
            email.setError("Email can't be empty")
            return

            //Toast.makeText(this, "Email is blank. Try again", Toast.LENGTH_SHORT).show()

        } else if (password.isEmpty()) {
            passwd.setError("Password can't be blank")
            return


            //var builder= AlertDialog.Builder(activity)
            //Toast.makeText(this, "Password is  blank. Try again", Toast.LENGTH_SHORT).show()

        } else if (confirmpassword.isEmpty()) {
            confirmpasswd.setError("Must confirm a password")
            return


            //var builder= AlertDialog.Builder(activity)
            //Toast.makeText(this, "Password is  blank. Try again", Toast.LENGTH_SHORT).show()

        } else if (password != confirmpassword) {
            passwd.setError("passwords don't match!")
            confirmpasswd.setError("passwords don't match!")
            return

        }

        else if(password.length<8 || password.length>20){
            passwd.setError("password not in range")
        }

        auth.createUserWithEmailAndPassword(e, password).addOnCompleteListener(this) {

            if (it.isSuccessful) {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                // main menu
                val i = Intent(this, guestActivity::class.java)
                startActivity(i)

            }
            else{

                Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
            }


        }


    }
    }





