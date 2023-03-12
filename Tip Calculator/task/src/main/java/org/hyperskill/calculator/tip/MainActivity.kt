package org.hyperskill.calculator.tip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var slider: Slider
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.edit_text)
        slider = findViewById(R.id.slider)
        textView = findViewById(R.id.text_view)

        // Define the TextWatcher for the EditText
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Only accept digits and .
                if (s != null && s.isNotEmpty() && !s.matches(Regex("[\\d.]*"))) {
                    editText.setText(s.filter { it.isDigit() || it == '.' })
                    editText.setSelection(editText.text.length)
                }

                // Update the TextView with the user input values and formatting
                val billValue = editText.text.toString().toBigDecimalOrNull() ?: BigDecimal.ZERO
                val tipPercentage = slider.value.toBigDecimal()
                val tipAmountFormatted = String.format("%.2f", billValue * tipPercentage / BigDecimal.valueOf(100)).toBigDecimal()
                val text = if (s != null && s.isNotEmpty()) {
                    "Tip amount: ${tipAmountFormatted}$"
                } else {
                    ""
                }
                textView.text = text
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Define the OnChangeListener for the Slider
        slider.addOnChangeListener { slider, value, fromUser ->
            // Update the TextView with the user input values and formatting
            val billValue = editText.text.toString().toBigDecimalOrNull() ?: BigDecimal.ZERO
            val tipPercentage = value.toBigDecimal()
            val tipAmountFormatted = String.format("%.2f", billValue * tipPercentage / BigDecimal.valueOf(100)).toBigDecimal()
            val text = if (editText.text.isNotEmpty()) {
                "Tip amount: ${tipAmountFormatted}$"
            } else {
                ""
            }
            textView.text = text
        }
    }
}

