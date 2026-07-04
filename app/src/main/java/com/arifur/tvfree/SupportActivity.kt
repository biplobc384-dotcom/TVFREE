package com.arifur.tvfree

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.util.UUID

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        // UI কম্পোনেন্টগুলো চেনাচ্ছি
        val tvDevName = findViewById<TextView>(R.id.tvDevName)
        val tvDevEmail = findViewById<TextView>(R.id.tvDevEmail)
        val tvDevPhone = findViewById<TextView>(R.id.tvDevPhone)
        val etUserMessage = findViewById<EditText>(R.id.etUserMessage)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        // ফায়ারবেস রেফারেন্স
        val database = FirebaseDatabase.getInstance()
        val devInfoRef = database.getReference("developer_info")
        val supportRef = database.getReference("support_messages")

        // ১. ডেভেলপার ইনফরমেশন পড়া (Read Data)
        devInfoRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val name = snapshot.child("name").value?.toString() ?: ""
                    val email = snapshot.child("email").value?.toString() ?: ""
                    val phone = snapshot.child("phone").value?.toString() ?: ""

                    tvDevName.text = getString(R.string.dev_name_format, name)
                    tvDevEmail.text = getString(R.string.dev_email_format, email)
                    tvDevPhone.text = getString(R.string.dev_phone_format, phone)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SupportActivity, "ডেটা লোড হতে সমস্যা হয়েছে!", Toast.LENGTH_SHORT).show()
            }
        })

        // ২. সাপোর্ট মেসেজ পাঠানো (Write Data)
        btnSubmit.setOnClickListener {
            val messageText = etUserMessage.text.toString().trim()

            if (messageText.isNotEmpty()) {
                val messageId = UUID.randomUUID().toString()

                val messageData = mapOf(
                    "message" to messageText,
                    "timestamp" to System.currentTimeMillis()
                )

                supportRef.child(messageId).setValue(messageData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "আপনার মেসেজটি সফলভাবে পাঠানো হয়েছে!", Toast.LENGTH_SHORT).show()
                        etUserMessage.text.clear()
                    }.addOnFailureListener {
                        Toast.makeText(this, "মেসেজ পাঠাতে সমস্যা হয়েছে।", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "দয়া করে আপনার সমস্যার কথা লিখুন", Toast.LENGTH_SHORT).show()
            }
        }

        // ন্যাভিগেশন বার হাইড করার জন্য কোড
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        // সোয়াইপ করলে যেন ন্যাভিগেশন বার কিছুক্ষণের জন্য দেখা যায় এবং আবার হাইড হয়ে যায়
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // শুধু ন্যাভিগেশন বার (নিচের অংশ) হাইড করতে চাইলে:
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

        // বিঃদ্রঃ আপনি যদি উপরের স্ট্যাটাস বার (যেখানে চার্জ, সময় দেখায়) সহ সবকিছু হাইড করে ফুল-স্ক্রিন করতে চান,
        // তবে উপরের লাইনের বদলে নিচের লাইনটি ব্যবহার করবেন:
        // windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }
}
