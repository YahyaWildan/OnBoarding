package com.example.onboarding

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.onboarding.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityRegisterBinding
    //proggres dialog
    private lateinit var progressDialog: ProgressDialog

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating account in....")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        //handle click, begin regis
        binding.buttonRegisterAction.setOnClickListener{
            //validate data
            validateData()
        }
        binding.backRegister.setOnClickListener{
            startActivity(Intent(this, LoginRegisterActivity::class.java))

        }
    }

    private fun validateData() {
        //get data
        email = binding.editTextEmailRegister.text.toString().trim()
        password = binding.editTextPasswordRegister.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.editTextEmailRegister.error = "Invalid email format"
        }else if(TextUtils.isEmpty(password)) {
            binding.editTextPasswordRegister.error = "Please enter password"
        }else{
            firebaseRegister()
        }

    }

    private fun firebaseRegister() {
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this , LoginActivity::class.java))
                finish()

            }.addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this, "Register failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}