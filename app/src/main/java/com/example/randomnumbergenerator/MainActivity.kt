package com.example.randomnumbergenerator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var minimumEditText: EditText
    lateinit var maximumEditText: EditText
    lateinit var resultEditText: EditText
    lateinit var quantityEditText: EditText

    private val textWatcher = DynamicTextWatcher(
        onChanged = { _, _, _, _ ->
            val minimumValue =
                findViewById<TextView>(R.id.min_edittext).text.toString().toIntOrNull()
            val maximumValue =
                findViewById<TextView>(R.id.max_edittext).text.toString().toIntOrNull()
            val quantityValue =
                findViewById<TextView>(R.id.qtd_edittext).text.toString().toIntOrNull()

            button.isEnabled =
                minimumValue != null && maximumValue != null && quantityValue != null && maximumValue > minimumValue && quantityValue > 0 && (quantityValue <= (maximumValue - minimumValue + 1))
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.generate_button)
        minimumEditText = findViewById(R.id.min_edittext)
        maximumEditText = findViewById(R.id.max_edittext)
        resultEditText = findViewById(R.id.result_edit_text)
        quantityEditText = findViewById(R.id.qtd_edittext)

        minimumEditText.addTextChangedListener(textWatcher)
        maximumEditText.addTextChangedListener(textWatcher)
        quantityEditText.addTextChangedListener(textWatcher)
        button.isEnabled = false

        button.setOnClickListener {
            val minimumValue = minimumEditText.text.toString().toInt()
            val maximumValue = maximumEditText.text.toString().toInt().plus(1)
            val quantity = quantityEditText.text.toString().toInt()

            printAllNumbers(generateXNumbers(minimumValue, maximumValue, quantity))

            val diff = maximumValue - minimumValue
            val timeStamp = System.currentTimeMillis()

            val finalNumber = (timeStamp % diff) + minimumValue
            Toast.makeText(applicationContext, "Your Number is: $finalNumber", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun generateXNumbers(start: Int, end: Int, x: Int): ArrayList<Int> {
        val array = arrayListOf<Int>()
        var i = 0

        while (i < x) {
            val randomNumber = (start..end).random()

            if (!array.contains(randomNumber)) {
                i += 1
                array.add(randomNumber)
            }
        }

        return array
    }

    fun printAllNumbers(numbers: ArrayList<Int>) {
        resultEditText.text.clear()

        for (i in 0 until numbers.size) {
            var newText = resultEditText.text.toString() + "${numbers[i]}"

            if (i != numbers.size - 1) {
                newText += " - "
            }

            resultEditText.setText(newText)
        }
    }

    class DynamicTextWatcher(
        private val afterChanged: ((Editable?) -> Unit) = {},
        private val beforeChanged: ((CharSequence?, Int, Int, Int) -> Unit) = { _, _, _, _ -> },
        private val onChanged: ((CharSequence?, Int, Int, Int) -> Unit) = { _, _, _, _ -> }
    ) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterChanged(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeChanged(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onChanged(s, start, before, count)
        }
    }
}