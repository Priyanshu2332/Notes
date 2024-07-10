package com.example.internshalanotes.ui.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.internshalanotes.R

class SplashScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splashscreen, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to LogInScreen using Navigation Component
            Navigation.findNavController(view).navigate(R.id.action_splashscreen_to_logInScreen)
        }, 3000) // Delay for 3 seconds


        return view
    }
}