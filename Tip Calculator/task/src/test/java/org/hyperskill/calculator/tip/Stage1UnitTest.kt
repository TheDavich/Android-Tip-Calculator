package org.hyperskill.calculator.tip

import org.hyperskill.calculator.tip.internals.TipCalculatorUnitTest
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner

// Version 2.0
@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Stage1UnitTest : TipCalculatorUnitTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun test00_checkEditText() {
         testActivity {
            editText
        }
    }

    @Test
    fun test01_checkSlider() {
        testActivity {
            slider
        }
    }

    @Test
    fun test02_checkTextView() {
        testActivity {
            textView
        }
    }
}