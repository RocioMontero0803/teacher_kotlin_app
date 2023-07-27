package com.example.teacher_kotlin_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacher_kotlin_app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var appPicturesAdapter: AppPicturesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the HomeViewModel
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Fetch app pictures from the HomeViewModel
        homeViewModel.fetchAppPictures()

        // Initialize the RecyclerView and Adapter
        val recyclerView: RecyclerView = binding.appPicturesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        appPicturesAdapter = AppPicturesAdapter()
        recyclerView.adapter = appPicturesAdapter

        // Observe the LiveData property and update the UI when the data changes
        homeViewModel.appPictures.observe(viewLifecycleOwner, { appPicturesList ->
            // Update the UI with the app pictures data
            appPicturesAdapter.submitList(appPicturesList)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
