package com.example.companygenerator.ui.mainscreen  //gfrtn c класс MainScreen

import androidx.lifecycle.ViewModel

import java.lang.Math.PI
import java.util.Random
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.sqrt


class MainScreenView : ViewModel() {
    var randomNum: Double? = null
        private set
    // случайное число
    var mean: Double? = null  // мат ожидание
    var dispersion: Double? = null  // дисперсия

    // функциия для получения числа из логнормального распредеделия
    fun handleGeneration(): Double? {
        if (mean!! > 0 && dispersion!! >= 0)
        //ищем число
            randomNum = normalGenerator(mean!!, dispersion!!)

        return randomNum  // возвращаем число
    }

    // функция нвхождения рпспределения
    private fun normalGenerator(mu: Double, sigma: Double): Double {
        val u1 = Random().nextDouble()  // выбираем случайные числа
        val u2 = Random().nextDouble()

        val z = sqrt(-2.0 * ln(u1)) * cos(2.0 * PI * u2)  // подсчитываем z

        return exp(mu + sqrt(sigma) * z)  // вычисляем экспоненту по заданной формуле
    }

}
