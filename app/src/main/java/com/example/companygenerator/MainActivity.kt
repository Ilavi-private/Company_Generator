package com.example.companygenerator  // ui-тест

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.companygenerator.ui.mainscreen.MainScreen


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)  // устанавливаем разметку экрана
        if (savedInstanceState == null) {
            // заменяем на наш MainScreen
            supportFragmentManager.beginTransaction().replace(R.id.container, MainScreen()).commitNow()
        }
    }
}
