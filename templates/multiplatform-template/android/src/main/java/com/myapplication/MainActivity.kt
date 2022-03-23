package com.myapplication

import MainScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.myapplication.model.FullName
import com.myapplication.model.Profile
import com.myapplication.tools.DateParser
import screens.profile.ImagePicker
import theme.BottomNavBarDemoTheme
import theme.ImagePickerTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            BottomNavBarDemoTheme {
                MainScreen()
            }
        }
    }
}