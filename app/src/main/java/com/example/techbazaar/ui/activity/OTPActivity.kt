package com.example.techbazaar.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.techbazaar.MainActivity
import com.example.techbazaar.databinding.ActivityOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOtpactivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonVerify.setOnClickListener {
            if (binding.editTextUserOTP.text!!.isEmpty()){
                Toast.makeText(this, "Enter otp", Toast.LENGTH_SHORT).show()
            }else{
                verifyUser(binding.editTextUserOTP.text.toString())
            }
        }
    }

    private fun verifyUser(otp: String) {

        val verificationId =intent.getStringExtra("verificationId")
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)

        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {


                    //Storing user data locally
                    val preferences=this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putString("userNumber",intent.getStringExtra("userNumber")!!)
                    editor.apply()


                    startActivity(Intent(this,MainActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }



