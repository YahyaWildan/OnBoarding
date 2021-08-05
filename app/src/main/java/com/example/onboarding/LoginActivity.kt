package com.example.onboarding

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.onboarding.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    //viewbinding
    private lateinit var binding: ActivityLoginBinding

    //progress dialog

    private lateinit var progressDialog: ProgressDialog

    //firebaseauth
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure progress dialog

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging in....")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseauth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, open register activity

        //handle click, begin login
        binding.buttonLoginAction.setOnClickListener{
            validateData()
        }

        binding.backLogin.setOnClickListener{
            startActivity(Intent(this, LoginRegisterActivity::class.java))
        }
    }

    private fun validateData() {
        //get data
        email = binding.editTextEmailLogin.text.toString().trim()
        password = binding.editTextPasswordLogin.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email
            binding.editTextEmailLogin.error = "Invalid email format"
        }else if(TextUtils.isEmpty(password)){
            binding.editTextPasswordLogin.error = "Please enter password"
        }else{
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        //show progres
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login success
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "LoggedIn as $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()

        }.addOnFailureListener{e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkUser() {
        //if user is already logged in go to profile
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}