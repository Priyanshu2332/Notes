package com.example.internshalanotes.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.internshalanotes.DataBaseHelper
import com.example.internshalanotes.R
import com.example.internshalanotes.databinding.FragmentLogInScreenBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LogInScreen : Fragment() {

    private lateinit var binding: FragmentLogInScreenBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var dataBaseHelper: DataBaseHelper

    companion object {
        private const val TAG = "LogInScreen"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInScreenBinding.inflate(inflater, container, false)
        dataBaseHelper = DataBaseHelper(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        // Check if user is already logged in
        if (isLoggedIn()) {
            navigateToMainScreen()
            return
        }

        // Configure Google Sign-In
        configureGoogleSignIn()

        // Handle login button click
        binding.editLogIn.setOnClickListener {
            val email = binding.editemail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show()
            } else {
                loginWithEmail(email)
            }
        }

        // Handle Google sign-in button click
        binding.editLogIn.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun isLoggedIn(): Boolean {
        return dataBaseHelper.isAnyUserLoggedIn()
    }

    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun loginWithEmail(email: String) {
        // Check if user exists in SQLite
        val userExists = dataBaseHelper.checkUserExists(email)
        if (userExists) {
            dataBaseHelper.markUserAsLoggedIn(email)
            Toast.makeText(requireContext(), "Login successful.", Toast.LENGTH_SHORT).show()
            navigateToMainScreen()
        } else {
            // Save the email to SQLite if it doesn't exist (should not happen with Google Sign-In)
            val inserted = dataBaseHelper.insertUser(email)
            if (inserted) {
                dataBaseHelper.markUserAsLoggedIn(email) // Mark user as logged in
                Toast.makeText(requireContext(), "New user registered.", Toast.LENGTH_SHORT).show()
                navigateToMainScreen()
            } else {
                Toast.makeText(requireContext(), "Failed to register user.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task -> if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val email = auth.currentUser?.email
                    email?.let { val inserted = dataBaseHelper.insertUser(it)
                        if (inserted) {
                            dataBaseHelper.markUserAsLoggedIn(it) // Mark user as logged in
                            Toast.makeText(requireContext(), "New user registered.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Failed to register user.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    navigateToMainScreen()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToMainScreen() {
        Log.d(TAG, "Navigating to MainScreenFragment")
        Navigation.findNavController(requireView()).navigate(R.id.action_logInScreen_to_mainScreen)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    // Google Sign-In successful, authenticate with Firebase
                    firebaseAuthWithGoogle(account.idToken!!)
                } else {
                    // Google Sign-In failed
                    Log.e(TAG, "Google Sign-In failed: account is null")
                    Toast.makeText(requireContext(), "Google Sign-In failed.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                // Google Sign-In failed
                Log.e(TAG, "Google sign in failed", e)
                Toast.makeText(requireContext(), "Google Sign-In failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
