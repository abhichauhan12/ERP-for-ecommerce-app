package com.example.techbazaar.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.techbazaar.R
import com.example.techbazaar.databinding.ActivityRegisterBinding
import com.example.techbazaar.model.UserModel
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewLogin.setOnClickListener {
            openLogin()
        }

        binding.buttonSignup.setOnClickListener {
            validateUser()
        }

    }

    private fun validateUser() {
        if (binding.editTextNameSignup.text!!.isEmpty() || binding.editTextNameSignup.text!!.isEmpty()){
            Toast.makeText(this, "Please Fill the data", Toast.LENGTH_SHORT).show()
        }else{
            storeData()
        }
    }

    private fun storeData() {
        val builder =AlertDialog.Builder(this)
            .setTitle("Loading")
            .setMessage("Please wait")
            .setCancelable(false)
            .create()
        builder.show()

        //using hash map to store user data
/*        val data = hashMapOf<String,Any>()
        data["name"] =binding.editTextNameSignup.text.toString()
        data["number"] =binding.editTextPhoneNumberSignup.text.toString()*/

        // Added usermodel class to sava data
        val data= UserModel(userName = binding.editTextNameSignup.text.toString(), userPhoneNumber = binding.editTextPhoneNumberSignup.text.toString())

        //Storing user data locally
        val preferences=this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("userName",binding.editTextNameSignup.text.toString())
        editor.putString("userNumber",binding.editTextPhoneNumberSignup.text.toString())
        editor.apply()


        Firebase.firestore.collection("users").document(binding.editTextPhoneNumberSignup.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()

            }
            .addOnFailureListener {
                builder.dismiss()

                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()

            }
    }

    private fun openLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}