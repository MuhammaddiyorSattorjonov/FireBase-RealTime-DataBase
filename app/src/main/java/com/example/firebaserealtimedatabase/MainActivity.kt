package com.example.firebaserealtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebaserealtimedatabase.adapters.RvAdapter
import com.example.firebaserealtimedatabase.databinding.ActivityMainBinding
import com.example.firebaserealtimedatabase.models.MyMessage
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var adapter: RvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("xabarlar")

        binding.btnSend.setOnClickListener {
            val key = reference.push().key
            reference.child(key!!).setValue(MyMessage(key,
                binding.edtMessage.text.toString(),
                SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Date())))
            Toast.makeText(this, "Send message", Toast.LENGTH_SHORT).show()
            binding.edtMessage.text.clear()
        }
        loadData()
    }

    private fun loadData() {
        adapter = RvAdapter()
        binding.rv.adapter = adapter

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<MyMessage>()
                val children = snapshot.children
                for (child in children) {
                    val value = child.getValue(MyMessage::class.java)
                    if (value != null) {
                        list.add(value)
                    }
                }
                adapter.list.clear()
                adapter.list.addAll(list)
                adapter.notifyDataSetChanged()
                binding.rv.scrollToPosition(list.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}