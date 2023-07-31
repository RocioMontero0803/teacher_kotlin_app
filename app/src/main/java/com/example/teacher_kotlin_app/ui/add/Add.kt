package com.example.teacher_kotlin_app.ui.add

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.teacher_kotlin_app.MainActivity.Companion.role
import com.example.teacher_kotlin_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Add : Fragment() {

    private lateinit var nameAdd: EditText
    private lateinit var uidAdd: EditText
    private lateinit var changeTeacher: Button
    private lateinit var changeStudent: Button
    private lateinit var buttonAdd: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference



    private lateinit var viewModel: AddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Display action bar and name
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //Variables

        nameAdd = view.findViewById(R.id.nameAdd)
        uidAdd = view.findViewById(R.id.uidAdd)
        changeStudent = view.findViewById(R.id.changeStudent)
        changeTeacher = view.findViewById(R.id.changeTeacher)
        buttonAdd = view.findViewById(R.id.buttonAdd)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        var newRole = "Student"
        var tr = false


        changeTeacher.setOnClickListener {
            newRole = "Teacher"
            Toast.makeText(requireContext(), "Change to: Teacher.", Toast.LENGTH_SHORT).show()
        }

        changeStudent.setOnClickListener {
            newRole = "Student"
            Toast.makeText(requireContext(), "Change to: Student.", Toast.LENGTH_SHORT).show()
        }

        buttonAdd.setOnClickListener {
            if (role == "owner" || role == "Teacher") {
                tr = true
            }

            if(tr){
                change(newRole)
            } else {
                Toast.makeText(requireContext(), "You are not allowed", Toast.LENGTH_SHORT).show()

            }

        }



        return view
    }

    private fun change(newRole: String){
        val writeName = nameAdd.text.toString()
        val writeUid = uidAdd.text.toString()

        mDbRef.child("user").child(writeUid).child("name").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val name = task.result?.value as? String
                if (name == writeName){
                    val userRef = mDbRef.child("user").child(writeUid).child("role")
                    userRef.setValue(newRole)
                } else {
                    Toast.makeText(requireContext(), "The name is not the same.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Error  retrieving Data.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        // TODO: Use the ViewModel
    }

}