package com.example.teacher_kotlin_app.posts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teacher_kotlin_app.R
import com.example.teacher_kotlin_app.databinding.ActivityPostsBinding
import com.example.teacher_kotlin_app.posts.models.DataClass
import com.example.teacher_kotlin_app.posts.models.MyAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import java.util.Locale

class PostsActivity : AppCompatActivity() {

    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var dataList: ArrayList<DataClass>
    private lateinit var adapter: MyAdapter
    private lateinit var binding: ActivityPostsBinding

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityPostsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val gridLayoutManager = GridLayoutManager(this@PostsActivity, 1)
    binding.recyclerView.layoutManager = gridLayoutManager
    binding.search.clearFocus()

    val builder = AlertDialog.Builder(this@PostsActivity)
    builder.setCancelable(false)
    builder.setView(R.layout.progress_layout)
    val dialog = builder.create()
    dialog.show()

    dataList = ArrayList()
    adapter = MyAdapter(this@PostsActivity, dataList)
    binding.recyclerView.adapter = adapter
    databaseReference = FirebaseDatabase.getInstance().getReference("Posts")
    dialog.show()

    eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            dataList.clear()
            for (itemSnapshot in snapshot.children) {
                val dataClass = itemSnapshot.getValue(DataClass::class.java)
                if (dataClass != null) {
                    dataList.add(dataClass)
                }
            }
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }

        override fun onCancelled(error: DatabaseError) {
            dialog.dismiss()
        }
    })

    binding.fab.setOnClickListener {
        val intent = Intent(this@PostsActivity, UploadActivity::class.java)
        startActivity(intent)
    }

    binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            searchList(newText)
            return true
        }
    })
}
fun searchList(text: String) {
    val searchList = ArrayList<DataClass>()
    for (dataClass in dataList) {
        if (dataClass.dataPriority?.lowercase()
                ?.contains(text.lowercase(Locale.getDefault())) == true
        ) {
            searchList.add(dataClass)
        }
    }
    adapter.searchDataList(searchList)
}
}