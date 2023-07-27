package com.example.teacher_kotlin_app.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teacher_kotlin_app.R

class ChangeName : Fragment() {

    companion object {
        fun newInstance() = ChangeName()
    }

    private lateinit var viewModel: ChangeNameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_name, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChangeNameViewModel::class.java)
        // TODO: Use the ViewModel
    }

}