package com.example.companygenerator

import com.example.companygenerator.ui.mainscreen.MainScreenView
import junit.framework.TestCase
import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.sqrt

class MainScreenViewUnitTest {

    // Seed set up
    private val random = Random()
    private val testCount = 1_500_000

    private val meanDelta = 1E-1
    private val varianceDelta = 0.8
    private val skewnessDelta = 1.1
    private val kurtosisDelta = 3.5

    private lateinit var viewModel: MainScreenView
    private var generatedNumbers = ArrayList<Double>(0)

    @Before
    fun setUp() {
        viewModel = MainScreenView()
    }


    @Test
    fun testCalculation() {
        val mean = random.nextDouble()
        val dispersion = random.nextDouble()

        viewModel.mean = mean
        viewModel.dispersion = dispersion

        for (i in 0..testCount){
            val result = viewModel.handleGeneration()
            assertEquals("getResult must be >= 0: $result",
                true, result != null && result > 0)
            addNumber(result!!)
        }

        testLog(
            generatedNumbers,
            exp(mean + dispersion / 2.0),
            exp(2 * mean + dispersion) * (exp(dispersion) - 1),
            sqrt(exp(dispersion) - 1) * (exp(dispersion) + 2),
            exp(4 * dispersion) + 2 * exp(3 * dispersion) + 3 * exp(2 * dispersion) - 6
        )
    }

    private fun addNumber(num: Double) {
        generatedNumbers.add(num)
    }

    private fun testLog(a: ArrayList<Double>, m: Double, v: Double, sk: Double, kur: Double) {
        val d = a.toDoubleArray()
        val gm = StatUtils.mean(d)
        val gv = StatUtils.variance(d)

        val gskewness = DescriptiveStatistics(d).skewness
        val gkurtosis = DescriptiveStatistics(d).kurtosis
        println(
            "Distribution: " +
            "${abs(gm - m)} ${abs(gv - v)} " +
                    "${abs(gskewness - sk)} ${abs(gkurtosis - kur)}"
        )
        TestCase.assertEquals("Mean is different", m, gm, meanDelta)
        TestCase.assertEquals("Dispersion is different", v, gv, varianceDelta)

        TestCase.assertEquals("Skewness is different", sk, gskewness, skewnessDelta)
        TestCase.assertEquals("Kurtosis is different", kur, gkurtosis, kurtosisDelta)
    }

    @Test
    fun testTextView() {
        for (i in 0..testCount) {
            viewModel.mean = random.nextDouble()
            viewModel.dispersion = random.nextDouble()

            val resultOfGetResultFunction = viewModel.handleGeneration()
            val randomNumberFiled = viewModel.randomNum

            assertEquals("Get result must be not null",true,
                resultOfGetResultFunction != null)
            assertEquals("Get result must be more then 0",true,
                resultOfGetResultFunction!! >= 0)
            assertEquals("Filed \"randomNumber\" must be not null",true,
                randomNumberFiled != null)
            assertEquals("Filed \"randomNumber\" must be equal getResult", true,
                randomNumberFiled!! == resultOfGetResultFunction)
        }

    }

}
