package com.example.techbazaar.ui.activity

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.techbazaar.MainActivity
import com.example.techbazaar.R
import com.example.techbazaar.data.database.AppDatabase
import com.example.techbazaar.data.database.entity.ProductEntity
import com.example.techbazaar.databinding.ActivityCheckOutBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Date
import java.util.Locale

class CheckOutActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var binding :ActivityCheckOutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // RAZOR_Pay
        /*
        * To ensure faster loading of the Checkout form,
        * call this method as early as possible in your checkout flow
        * */
        val co = Checkout()
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime
        co.setKeyID("rzp_test_k6WqcaU4WUb78q")

        startPayment()

    }

//    private fun startPayment() {
//        /*
//        *  You need to pass the current activity to let Razorpay create CheckoutActivity
//        * */
//        val activity: Activity = this
//        val co = Checkout()
//
//        val price = intent.getStringExtra("totalCost")
//
//        if (price != null) {
//            try {
//                val options = JSONObject()
//
//                options.put("name","TechBazar")
//                options.put("description","Demoing Charges")
//                //You can omit the image option to fetch the image from the dashboard
//                options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
//                options.put("theme.color", "#3399cc");
//                options.put("currency","INR");
//                options.put("order_id", "order_DBJOWzybf0sJbb");
//                options.put("amount", (price.toInt() * 100).toString()) //pass amount in currency subunits
//                // ... rest of your code
//
//                val retryObj = JSONObject();
//                retryObj.put("enabled", true);
//                retryObj.put("max_count", 4);
//                options.put("retry", retryObj);
//
//                val prefill = JSONObject()
//                prefill.put("email","abhishekchauhan9586@gmail.com")
//                prefill.put("contact","8826487886")
//
//                options.put("prefill",prefill)
//                co.open(activity,options)
//            } catch (e: Exception) {
//                Toast.makeText(activity, "Error in payment: ${e.message}", Toast.LENGTH_LONG).show()
//                e.printStackTrace()
//            }
//        } else {
//            Toast.makeText(activity, "Price is null", Toast.LENGTH_LONG).show()
//        }
//
//
////        val price =intent.getStringExtra("totalCost")
////
////        try {
////            val options = JSONObject()
////            options.put("name","TechBazar")
////            options.put("description","Demoing Charges")
////            //You can omit the image option to fetch the image from the dashboard
////            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
////            options.put("theme.color", "#3399cc");
////            options.put("currency","INR");
////            options.put("order_id", "order_DBJOWzybf0sJbb");
////            options.put("amount",(price!!.toInt()*100).toString())//pass amount in currency subunits
////
////            val retryObj = JSONObject();
////            retryObj.put("enabled", true);
////            retryObj.put("max_count", 4);
////            options.put("retry", retryObj);
////
////            val prefill = JSONObject()
////            prefill.put("email","abhishekchauhan9586@gmail.com")
////            prefill.put("contact","8826487886")
////
////            options.put("prefill",prefill)
////            co.open(activity,options)
////        }catch (e: Exception){
////            Toast.makeText(activity, "Error in payment: ${e.message}", Toast.LENGTH_LONG).show()
////            e.printStackTrace()
////        }
//    }

    private fun startPayment() {
        /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()

        val price = intent.getStringExtra("totalCost")

        if (price != null) {
            try {
                val options = JSONObject()

                options.put("name","TechBazar")
                options.put("description","Demoing Charges")
                options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
                options.put("theme.color", "#3399cc");
                options.put("currency","INR");
//                options.put("order_id", "order_DBJOWzybf0sJbb");
//                options.put("order_id", generateOrderID());
                options.put("amount", (price.toInt() * 100).toString())

                val retryObj = JSONObject()
                retryObj.put("enabled", true)
                retryObj.put("max_count", 4)
                options.put("retry", retryObj)

                val prefill = JSONObject()
                prefill.put("email","abhishekchauhan9586@gmail.com")
                prefill.put("contact","8826487886")

                options.put("prefill",prefill)
                co.open(activity,options)
            } catch (e: Exception) {
                Toast.makeText(activity, "Error in payment: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        } else {
            Toast.makeText(activity, "Price is null", Toast.LENGTH_LONG).show()
        }
    }

//    override fun onPaymentSuccess(p0: String?) {
//        Toast.makeText(this, "payment success", Toast.LENGTH_SHORT).show()
//        uploadSuccessOrderPlacedData()
//    }
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    outState.putStringArrayList("productIds", intent.getStringArrayListExtra("productIds"))
    outState.putString("totalCost", intent.getStringExtra("totalCost"))

    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        if (savedInstanceState!= null){
            intent.putExtras(savedInstanceState)
        }

    }

    override fun onPaymentSuccess(p0: String?) {
        try {
            // Your existing code here
            Toast.makeText(this, "Payment success", Toast.LENGTH_SHORT).show()
            uploadSuccessOrderPlacedData()
        } catch (e: Exception) {
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "payment failed", Toast.LENGTH_LONG).show()
    }


    private fun uploadSuccessOrderPlacedData() {
        val id=intent.getStringArrayListExtra("productIds")
        for (currentId in id!!){
            //fetching data for that current id to save in placed order
            fetchData(currentId)

        }
    }

    private fun fetchData(productId: String?) {

        val dao = AppDatabase.getInstance(this).productDao()

        Firebase.firestore.collection("Products")
            .document(productId!!).get()
            .addOnSuccessListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    dao.deleteProduct(ProductEntity(productId))
                }


            saveData(it.getString("productName"),
                it.getString("productSellingPrice"),
                productId
            )
        }
    }

    private fun saveData(name: String?, price: String?, productId: String?) {

        //to get userId we have to use preferences
        val preference=this.getSharedPreferences("user", MODE_PRIVATE)

        val data = hashMapOf<String,Any>()
        data["name"]=name!!
        data["price"]=price!!
        data["productId"]=productId!!
        data["status"]="Ordered"
        data["userId"]=preference.getString("number","")!!

        val firestore =Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
        data["orderID"]=key

        firestore.document(key).set(data)
            .addOnSuccessListener {
                Toast.makeText(this, "order placed", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
        }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()

            }

    }

//    private fun generateOrderID(): String {
//        val sdf = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
//        val currentDate = sdf.format(Date())
//        val randomSuffix = (1000..9999).random()
//
//        return "ORDER$currentDate$randomSuffix"
//    }


}