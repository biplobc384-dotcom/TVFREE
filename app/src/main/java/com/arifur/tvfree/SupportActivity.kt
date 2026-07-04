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

                    tvDevName.text = "Developer: $name"
                    tvDevEmail.text = "Email: $email"
                    tvDevPhone.text = "Phone: $phone"
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
    }
}