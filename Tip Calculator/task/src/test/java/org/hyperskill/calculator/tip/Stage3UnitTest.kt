package org.hyperskill.calculator.tip

import org.hyperskill.calculator.tip.internals.TipCalculatorUnitTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner

// Version 2.0
@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Stage3UnitTest : TipCalculatorUnitTest<MainActivity>(MainActivity::class.java) {

    companion object {
        const val expectedTextTemplate = "Tip amount: %.2f$"
    }

    @Test
    fun test00_checkTipWithEmptyValue() {
        testActivity {
            slider
            textView

            slider.value = 15f
            advanceClockAndRun()

            val messageTextNotEmptyError =
                "View with id \"$idTextView\" should be empty when \"$idEditText\" is empty"
            val isTextEmpty = textView.text.isNullOrEmpty()
            assertTrue(messageTextNotEmptyError, isTextEmpty)
        }
    }

    @Test
    fun test01_checkValueWithInitialTip() {
        testActivity {
            editText
            textView

            val valueToTest = 50
            editText.setText(valueToTest.toString())
            advanceClockAndRun()
            val tipToTest = 0

            val messageTextError = "View with id \"$idTextView\" should contain formatted output"
            val expectedValue = 0.00
            val expectedText = expectedTextTemplate.format(expectedValue, tipToTest)
            val actualText = textView.text.toString()
            assertEquals(messageTextError, expectedText, actualText)
        }
    }

    @Test
    fun test02_checkBothValueAndTipWithSliderListenerLast() {
        testActivity {
            editText
            textView
            slider

            val valueToTest = 201
            editText.setText(valueToTest.toString())
            advanceClockAndRun()
            val tipToTest = 10
            slider.value = tipToTest.toFloat()
            advanceClockAndRun()

            val messageTextError = "View with id \"$idTextView\" should contain formatted output"
            val expectedValue = 20.10
            val expectedText = expectedTextTemplate.format(expectedValue, tipToTest)
            val actualText = textView.text
            assertEquals(messageTextError, expectedText, actualText)
        }
    }

    @Test
    fun test03_checkEditTextListenerLast() {
        testActivity {
            editText
            slider
            textView

            val tipToTest = 90
            slider.value = tipToTest.toFloat()
            advanceClockAndRun()
            val valueToTest = 39
            editText.setText(valueToTest.toString())
            advanceClockAndRun()

            val messageTextError = "View with id \"$idTextView\" should contain formatted output"
            val expectedValue = 35.10
            val expectedText = expectedTextTemplate.format(expectedValue, tipToTest)
            val actualText = textView.text.toString()
            assertEquals(messageTextError, expectedText, actualText)
        }
    }

    @Test
    fun test04_checkLargeValue() {
        testActivity {
            editText
            textView
            val messageLargeNumberTextError = "Make sure you give support for large numbers"

            try {
                val valueToTest = "100000000000000000"
                editText.setText(valueToTest)
                advanceClockAndRun()
                val tipToTest = 90
                slider.value = tipToTest.toFloat()
                advanceClockAndRun()


                val expectedValue = 90_000_000_000_000_000.00
                val expectedText = expectedTextTemplate.format(expectedValue, tipToTest)
                val actualText = textView.text.toString()
                assertEquals(messageLargeNumberTextError, expectedText, actualText)
            } catch (ex: Exception) {
                throw AssertionError("Exception thrown ${ex.javaClass.simpleName}. $messageLargeNumberTextError", ex)
            }
        }
    }

    @Test
    fun test05_checkDecimalValue() {
        testActivity {
            editText
            textView

            val valueToTest = "70.12"
            editText.setText(valueToTest)
            advanceClockAndRun()
            val tipToTest = 10
            slider.value = tipToTest.toFloat()
            advanceClockAndRun()

            val messageDecimalNumberTextError =
                "Make sure you give support for numbers with decimal part"
            val expectedValue = 7.01
            val expectedText = expectedTextTemplate.format(expectedValue, tipToTest)
            val actualText = textView.text.toString()
            assertEquals(messageDecimalNumberTextError, expectedText, actualText)
        }
    }

    @Test
    fun test06_checkClearingValue() {
        testActivity {
            editText
            textView

            val valueNotEmpty = "100.10"
            editText.setText(valueNotEmpty)
            advanceClockAndRun()
            val tipToTest = 10
            slider.value = tipToTest.toFloat()
            advanceClockAndRun()
            val valueEmpty = ""
            editText.setText(valueEmpty)
            advanceClockAndRun()

            val messageDecimalNumberTextError =
                "View with id \"$idTextView\" should clear if \"$idEditText\" is empty"
            val expectedText = ""
            val actualText = textView.text.toString()
            assertEquals(messageDecimalNumberTextError, expectedText, actualText)
        }
    }

    @Test
    fun test07_check101TipsForValue100() {
        testActivity {
            editText
            textView

            val valueToTest = "100.00"
            editText.setText(valueToTest)
            advanceClockAndRun()

            (0..100).forEach { tipToTest ->
                slider.value = tipToTest.toFloat()
                advanceClockAndRun()

                val messageLongNumberTextError =
                    "Make sure you give support for all 101 possible tip values. With tip value $tipToTest"
                val expectedValue = tipToTest.toDouble()
                val expectedText = expectedTextTemplate.format(expectedValue, tipToTest)
                val actualText = textView.text.toString()
                assertEquals(messageLongNumberTextError, expectedText, actualText)
            }
        }
    }
}