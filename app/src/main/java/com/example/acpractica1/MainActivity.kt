package com.example.acpractica1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.acpractica1.ui.screens.Navigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aquí debería realizarse la comprobación de conectividad
            Navigation()
        }
    }
}