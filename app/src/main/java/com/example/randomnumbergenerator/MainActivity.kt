package com.example.randomnumbergenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var minimumEditText: EditText
    lateinit var maximumEditText: EditText

    private val textWatcher = DynamicTextWatcher(
        onChanged = {_,_,_,_ ->
            val minimumValue = findViewById<TextView>(R.id.min_edittext).text.toString().toIntOrNull()
            val maximumValue = findViewById<TextView>(R.id.max_edittext).text.toString().toIntOrNull()

            button.isEnabled = minimumValue != null && maximumValue != null && maximumValue > minimumValue
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.generate_button)
        minimumEditText = findViewById(R.id.min_edittext)
        maximumEditText = findViewById(R.id.max_edittext)

        minimumEditText.addTextChangedListener(textWatcher)
        maximumEditText.addTextChangedListener(textWatcher)
        button.isEnabled = false

        button.setOnClickListener {
            val minimumValue = minimumEditText.text.toString().toInt()
            val maximumValue = maximumEditText.text.toString().toInt()
            val diff = maximumValue - minimumValue
            val timeStamp = System.currentTimeMillis()

            val finalNumber = (timeStamp % diff) + minimumValue
            Toast.makeText(applicationContext, "Your Number is: $finalNumber", Toast.LENGTH_SHORT).show()
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