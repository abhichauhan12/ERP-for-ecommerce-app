package com.example.techbazaar.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.techbazaar.R
import com.example.techbazaar.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding :ActivityAddressBinding
    private lateinit var preferences : SharedPreferences
    private lateinit var totalCost :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences=this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost=intent.getStringExtra("totalCost")!!

        loadUserInfo()

        binding.buttonProceedtoCheckout.setOnClickListener {
            validateData(
                binding.userNameAddressActivity.text.toString(),
                binding.userNumberAddressActivity.text.toString(),
                binding.villageAddressActivity.text.toString(),
                binding.cityAddressActivity.text.toString(),
                binding.stateAddressActivity.text.toString(),
                binding.pinCodeAddressActivity.text.toString(),
            )
        }
    }

    private fun validateData(name: String, number: String, village: String, city: String, state: String, pinCode: String) {
        if (name.isEmpty() || number.isEmpty() || village.isEmpty() || city.isEmpty() || state.isEmpty() || pinCode.isEmpty()){
            Toast.makeText(this, "Fill the field", Toast.LENGTH_SHORT).show()
        }else{
            storeData(village,city,state,pinCode)
        }

    }

/*
    private fun storeData( village: String, city: String, state: String, pinCode: String) {
        val map= hashMapOf<String,Any>()
        map["village"]=village
        map["city"]=city
        map["state"]= state
        map["pinCode"]=pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("phoneNumber","")!!)
            .update(map).addOnSuccessListener {

                val bundle = Bundle()
                bundle.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                bundle.putString("totalCost",totalCost)
                intent.putExtras(bundle)

                val intent = Intent(this, CheckOutActivity::class.java)


//                intent.putStringArrayListExtra("productIds",intent.getStringArrayListExtra("productIds"))
//                intent.putExtra("totalCost",totalCost)

                startActivity(intent)


            }.addOnFailureListener {
                Toast.makeText(this, "Something Went wrong", Toast.LENGTH_SHORT).show()
            }

    }
*/

    private fun storeData(village: String, city: String, state: String, pinCode: String) {
        val phoneNumber = preferences.getString("userNumber", "")

        if (phoneNumber.isNullOrEmpty()) {
            Toast.makeText(this, "Phone number is not available.", Toast.LENGTH_SHORT).show()
            return
        }

        val map = hashMapOf<String, Any>()
        map["village"] = village
        map["state"] = state
        map["city"] = city
        map["pinCode"] = pinCode

        val userDocumentRef = Firebase.firestore.collection("users").document(phoneNumber)

        userDocumentRef
            .update(map)
            .addOnSuccessListener {
                val newIntent = Intent(this, CheckOutActivity::class.java)
                val bundle = Bundle()
                bundle.putStringArrayList("productIds", intent.getStringArrayListExtra("productIds"))
                bundle.putString("totalCost", totalCost)
                newIntent.putExtras(bundle)

                startActivity(newIntent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Something Went wrong", Toast.LENGTH_SHORT).show()
            }
    }

/*
    private fun loadUserInfo() {

        Firebase.firestore.collection("users")
            .document(preferences.getString("phoneNumber", "")!!)
            .get().addOnSuccessListener {
                binding.userNameAddressActivity.setText(it.getString("userName"))
                binding.userNumberAddressActivity.setText(it.getString("userPhoneNumber"))
                binding.villageAddressActivity.setText(it.getString("village"))
                binding.cityAddressActivity.setText(it.getString("state"))
                binding.stateAddressActivity.setText(it.getString("city"))
                binding.pinCodeAddressActivity.setText(it.getString("pinCode"))

            }.addOnFailureListener {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()

            }
    }*/

    private fun loadUserInfo() {
        val phoneNumber = preferences.getString("userNumber", "")

        if (phoneNumber.isNullOrEmpty()) {
            Toast.makeText(this, "Phone number is not available.", Toast.LENGTH_SHORT).show()
            return
        }

        val userDocumentRef = Firebase.firestore.collection("users").document(phoneNumber)

        userDocumentRef
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userName = documentSnapshot.getString("userName")
                    val userPhoneNumber = documentSnapshot.getString("userPhoneNumber")
                    val village = documentSnapshot.getString("village")
                    val city = documentSnapshot.getString("city")
                    val state = documentSnapshot.getString("state")
                    val pinCode = documentSnapshot.getString("pinCode")

                    binding.userNameAddressActivity.setText(userName)
                    binding.userNumberAddressActivity.setText(userPhoneNumber)
                    binding.villageAddressActivity.setText(village)
                    binding.cityAddressActivity.setText(city)
                    binding.stateAddressActivity.setText(state)
                    binding.pinCodeAddressActivity.setText(pinCode)
                } else {
                    Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error loading user data.", Toast.LENGTH_SHORT).show()
            }
    }

}