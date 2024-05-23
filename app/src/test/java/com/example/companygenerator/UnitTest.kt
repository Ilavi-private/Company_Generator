package com.example.companygenerator

import com.example.companygenerator.ui.mainscreen.MainScreenView
import junit.framework.TestCase
import org.apache.commons.math3.stat.StatUtils
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.math.exp



class UnitTest {

    // Seed set up
    private val random = Random()
    private val testCount = 100000

    private lateinit var viewModel: MainScreenView
    private var generatedNumbers = ArrayList<Double>(0)

    @Before
    fun setUp() {
        viewModel = MainScreenView()
    }


    private fun testLog(numPairs: ArrayList<Double>, generatedMean: Double, generatedDispersion: Double) {
        val numPairsDouble = numPairs.toDoubleArray()
        val statMean = StatUtils.mean(numPairsDouble)
        val statVariance = StatUtils.variance(numPairsDouble)

        TestCase.assertEquals("Mean is wrong", generatedMean, statMean, 1E-1)
        TestCase.assertEquals("Dispersion is wrong", generatedDispersion, statVariance, 0.8)

    }

    @Test
    fun testCalculation() {
        val mean = random.nextDouble()
        val dispersion = random.nextDouble()

        viewModel.mean = mean
        viewModel.dispersion = dispersion

        for (i in 0..testCount){
            val result = viewModel.handleGeneration()

            assertEquals("getResult > 0: $result",
                true, result!! > 0)

            generatedNumbers.add(result)
        }

        testLog(
            generatedNumbers,
            exp(mean + dispersion / 2.0),
            exp(2 * mean + dispersion) * (exp(dispersion) - 1)
        )
    }
}
