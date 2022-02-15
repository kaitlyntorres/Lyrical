package com.example.lyrical
//methods called when text changes
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import java.util.regex.Matcher
import java.util.regex.Pattern

class passwordChecker:TextWatcher {
    //var strengthColor: MutableLiveData<Int> = MutableLiveData()
    //var strengthLevel: MutableLiveData<String> = MutableLiveData()

    var lc: MutableLiveData<Int> = MutableLiveData(0)
    var uc: MutableLiveData<Int> = MutableLiveData(0)
    var d: MutableLiveData<Int> = MutableLiveData(0)
    var spc: MutableLiveData<Int> = MutableLiveData(0)


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {}

    // called whenever we type
    override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (char != null) {
            if (char.lowerCase()) {
                lc.value = 1
            } else {
                lc.value = 0
            }

            if (char.upperCase()) {
                uc.value = 1
            } else {
                uc.value = 0
            }

            if (char.digit()) {
                d.value = 1
            } else {
                d.value = 0
            }

            if (char.specChar()) {
                spc.value = 1
            } else {
                spc.value = 0
            }


            // LEFT OFF



        }
    }

    private fun CharSequence.lowerCase():Boolean{
        // checks if it matches in charsequence
        val pattern: Pattern = Pattern.compile("[a-z]")
        val haslc: Matcher = pattern.matcher(this)
        return haslc.find()

    }
    private fun CharSequence.upperCase():Boolean{
        // checks if it matches in charsequence
        val pattern: Pattern = Pattern.compile("[A-Z]")
        val hasuc: Matcher = pattern.matcher(this)
        return hasuc.find()

    }

    private fun CharSequence.digit():Boolean{
        // checks if it matches in charsequence
        val pattern: Pattern = Pattern.compile("[0-9]")
        val hasd: Matcher = pattern.matcher(this)
        return hasd.find()

    }

    private fun CharSequence.specChar():Boolean{
        // checks if it matches in charsequence
        val pattern: Pattern = Pattern.compile("[@$!%?&]")
        val hassc: Matcher = pattern.matcher(this)
        return hassc.find()

    }
}