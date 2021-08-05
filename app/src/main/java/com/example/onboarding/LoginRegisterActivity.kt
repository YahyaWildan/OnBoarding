package com.example.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        findViewById<MaterialButton>(R.id.buttonRegistration).setOnClickListener {
            RegisterAction()
        }
        findViewById<TextView>(R.id.buttonLogin).setOnClickListener{
            LoginAction()
        }

    }

    private fun RegisterAction() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun LoginAction() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

}