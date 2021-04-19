package com.example.quoteshareapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.quoteshareapp.databinding.ActivityMainBinding

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var currentquote: String? = null
    var currentauthor:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_main)
      binding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        LoadQuote()
    }

    private fun LoadQuote() {

        progressBar.visibility= View.VISIBLE

// Instantiate the RequestQueue.

        val url = "https://api.forismatic.com/api/1.0/?method=getQuote&lang=en&format=json"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->



                    currentquote = response.getString("quoteText")
                     currentauthor=response.getString("quoteAuthor")
                    var op1 = response.getString("senderName")
                    var op2 = response.getString("quoteLink")
                      progressBar.visibility=View.GONE

                     binding.quoteText.setText(currentquote)
                  //    binding.quoteAuthort.setText("Author : $currentauthor")
                        binding.quoteText.setText(currentauthor)
                       binding.quoteText.setText(op1)
                      binding.quoteText.setText(op2)
                },
                Response.ErrorListener {
                    Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show()
                })

// Add the request to the RequestQueue.
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    fun ShowNext(view: View) {
        LoadQuote()
    }
    fun shareQuote(view: View) {
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Sharing this quote using Quote share app\n $currentquote\n Author $currentauthor")
        val chooser = Intent.createChooser(intent,"sharing this quote using....")
        startActivity(chooser)
    }


}




