package com.example.teacher_kotlin_app.ui.profile

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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


        //Change name on logo
        val name1 = email.substringBefore("@")

        val firstLetter = name.capitalize(Locale.ROOT) ?: "S"


        //Change name to actual

        myTextView4 = view.findViewById(R.id.nameProfile)
        myTextView4.text = "Name: Hello $name1."



        //Change email to actual
        myTextView1 = view.findViewById(R.id.emailProfile)
        myTextView1.text = "Email: Your email is \"$email\"."




        // Change role to Actual
        myTextView3 = view.findViewById(R.id.roleProfile)
        myTextView3.text = "Role: Your role is \"$role\" ."

        myTextView2 = view.findViewById(R.id.uidProfile)
        myTextView2.text = "Uid: Your uid is \"$uid\"."


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference



    }

}