package com.example.teacher_kotlin_app.ui.posts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teacher_kotlin_app.databinding.FragmentPostBinding
import com.example.teacher_kotlin_app.posts.UploadActivity
import com.example.teacher_kotlin_app.posts.models.DataClass
import com.example.teacher_kotlin_app.posts.models.MyAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import java.util.Locale

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var dataList: ArrayList<DataClass>
    private lateinit var adapter: MyAdapter
    private var imageUri: Uri? = null
    private lateinit var uid : String
    private lateinit var auth : FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val gridLayoutManager = GridLayoutManager(activity, 1)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.search.clearFocus()

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        dataList = ArrayList()
        adapter = activity?.let { MyAdapter(it, dataList) }!!
        binding.recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Posts")
        //  dialog.show()

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
                //   dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                //    dialog.dismiss()
            }
        })

        binding.fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, UploadActivity::class.java)
            startActivity(intent)
        })

//        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
//        databaseReference!!.child(uid).child("status").get().addOnSuccessListener {
//            Log.i("firebase", "Got role ${it.value}")
//            if (it.value == "Student") {
//                binding.fab.visibility = View.GONE
//            }
//        }.addOnFailureListener{
//            Log.e("firebase", "Error getting data", it)
//        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })
        return root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}