package com.application.x_pass.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.application.x_pass.ui.auth.LoginScreen
import com.application.x_pass.utils.SPHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var spHelper: SPHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (spHelper.isUserAuthenticated()) {
            val userType = spHelper.getUserType()
            val intent = when (userType) {
                1, 4 -> Intent(this, InspectorActivity::class.java)
                else -> Intent(this, InspectorActivity::class.java)
            }
            startActivity(intent)
            finish()
            return
        }

        setContent {
            LoginScreen(
                onLoginSuccess = { user ->
                    spHelper.saveAccessToken(user.value.accessToken)
                    spHelper.saveAuthStatus(true)
                    spHelper.saveUserType(user.value.userType)

                    val intent = when (user.value.userType) {
                        1, 4 -> Intent(this, InspectorActivity::class.java)
                        else -> Intent(this, InspectorActivity::class.java)
                    }
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}
