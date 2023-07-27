package com.example.teacher_kotlin_app.ui.profile

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.teacher_kotlin_app.MainActivity.Companion.email
import com.example.teacher_kotlin_app.MainActivity.Companion.name
import com.example.teacher_kotlin_app.MainActivity.Companion.role
import com.example.teacher_kotlin_app.MainActivity.Companion.uid
import com.example.teacher_kotlin_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

class Profile : Fragment() {

    private lateinit var myTextView: TextView
    private lateinit var myTextView1: TextView
    private lateinit var myTextView2: TextView
    private lateinit var myTextView3: TextView
    private lateinit var myTextView4: TextView
    private lateinit var btnChangeName: Button
    private lateinit var changeN: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var addProfile: Button

    companion object {
        fun newInstance() = Profile()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        btnChangeName = view.findViewById(R.id.btnChangeName)
        changeN = view.findViewById(R.id.changeName)

        // Initialize Firebase Auth and Database reference
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        // Change name on logo
        var name1 = email.substringBefore("@").capitalize()
        val firstLetter = name.capitalize(Locale.ROOT) ?: "S"

        // Change name to actual
        myTextView4 = view.findViewById(R.id.nameProfile)
        myTextView4.text = "Name: Hello $name1."

        // Change email to actual
        myTextView1 = view.findViewById(R.id.emailProfile)
        myTextView1.text = "Email: Your email is \"$email\"."

        // Change role to Actual
        myTextView3 = view.findViewById(R.id.roleProfile)
        myTextView3.text = "Role: Your role is \"$role\" ."

        myTextView2 = view.findViewById(R.id.uidProfile)
        myTextView2.text = "Uid: Your uid is \"$uid\"."

        btnChangeName.setOnClickListener {
            val newName = changeN.text.toString().trim()
            if (newName.isNotEmpty()) {
                // Update the name in the Firebase database
                mDbRef.child("user").child(uid).child("name").setValue(newName)
                    .addOnSuccessListener {
                        // Update the displayed name
                        myTextView4.text = "Name: Hello $newName."
                        // Update the stored name in the companion object for MainActivity
                        name = newName
                        Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener { exception ->
                        // Handle the failure, if any
                        // For example, you can show an error toast
                        Toast.makeText(context, "Failed to update name: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }
}