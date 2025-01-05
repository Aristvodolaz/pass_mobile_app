package com.application.x_pass.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.application.x_pass.ui.inspector.InspectorScreen
import com.application.x_pass.ui.theme.XpassTheme
import com.application.x_pass.utils.SPHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdminActivity  : AppCompatActivity() {
    @Inject
    lateinit var spHelper: SPHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XpassTheme{
                InspectorScreen(spHelper = spHelper)
            }
        }
    }
}