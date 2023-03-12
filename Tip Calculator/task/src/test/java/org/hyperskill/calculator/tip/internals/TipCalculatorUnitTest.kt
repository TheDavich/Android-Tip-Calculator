package org.hyperskill.calculator.tip.internals

import android.app.Activity
import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.slider.Slider
import org.junit.Assert.assertEquals
import java.util.concurrent.TimeUnit

open class TipCalculatorUnitTest<T : Activity>(clazz: Class<T>) : AbstractUnitTest<T>(clazz) {
    companion object {
        const val idEditText = "edit_text"
        const val idTextView = "text_view"
        const val idSlider = "slider"
    }


    val editText by lazy  {
        activity.findViewByString<EditText>(idEditText).apply {
            val messageInputTypeError = "View \"$idEditText\" should have inputType number"
            val expectedInputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            val actualInputType = inputType
            assertEquals(messageInputTypeError, expectedInputType, actualInputType)
        }
    }
    val textView by lazy  {
        activity.findViewByString<TextView>(idTextView).apply {
            val messageInitialTextError = "View \"$idTextView\" should initially have empty text"
            val expectedInitialText = ""
            val actualInitialText = text.toString()
            assertEquals(messageInitialTextError, expectedInitialText, actualInitialText)
        }
    }

    val slider by lazy  {
        activity.findViewByString<Slider>(idSlider).apply {
            assertSliderInitialValues(
                idString = idSlider,
                expectedStepSize = 1.0f,
                expectedValueFrom = 0.0f,
                expectedValueTo = 100.0f,
                expectedValue = 0f
            )
        }
    }

    private fun Slider.assertSliderInitialValues(
        idString: String,
        expectedStepSize: Float,
        expectedValueFrom: Float,
        expectedValueTo: Float,
        expectedValue: Float
    ){
        val messageStepSizeError = "View \"$idString\" should have proper stepSize attribute"
        val actualStepSize = stepSize
        assertEquals(messageStepSizeError, expectedStepSize, actualStepSize)

        val messageValueFromError = "View \"$idString\" should have proper valueFrom attribute"
        val actualValueFrom = valueFrom
        assertEquals(messageValueFromError, expectedValueFrom, actualValueFrom)

        val messageValueToError = "View \"$idString\" should have proper valueTo attribute"
        val actualValueTo = valueTo
        assertEquals(messageValueToError, expectedValueTo, actualValueTo)

        val messageValueError = "View \"$idString\" should have proper initial value"
        val actualValue = value
        assertEquals(messageValueError, expectedValue, actualValue)
    }

    protected fun advanceClockAndRun(millis: Long = 500) {
        shadowLooper.idleFor(millis, TimeUnit.MILLISECONDS)
    }
}