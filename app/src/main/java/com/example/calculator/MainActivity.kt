package com.example.calculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {
    var lastDigit = false
    var checkOpeartor = false

    private lateinit var binding : ActivityMainBinding
    private lateinit var expression : Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onBack(view:View){
        val length = binding.inputText.text.length
        if(length>0){
            binding.inputText.text= binding.inputText.text.subSequence(0,length-1)
        }
        val lastChar = binding.inputText.text.toString().last()
        if(lastChar.isDigit()) {
            evaluate()
        }


    }

    fun digitClick(view:View){
        if (lastDigit== false && checkOpeartor==false) {

            binding.inputText.text = (view as Button).text
            checkOpeartor = true
            lastDigit = true


        }
        else {

            binding.inputText.append((view as Button).text)
            checkOpeartor = true

        }
        evaluate()
    }

    fun onOperator(view:View){
        view as Button
        val symbol = view.text
        if(checkOpeartor) {
            binding.inputText.append(symbol)
            checkOpeartor=false
            lastDigit= true

         }
    }

    fun onDecimal(view: View){
        view as Button

        binding.inputText.append(view.text)

    }

    fun onEqual(view:View){
        evaluate()
        binding.inputText.setTextSize(TypedValue.COMPLEX_UNIT_SP,32F)
        binding.outputText.setTextSize(TypedValue.COMPLEX_UNIT_SP,44F)
//        binding.outputText.setTextColor(Color.parseColor("#013220"))
//        binding.inputText.text =binding.outputText.text.toString().drop(1)

    }




    fun onClear(view:View){
        binding.inputText.text=""
        lastDigit= false

    }

    fun allClear(view:View){
        binding.inputText.text=""
        binding.outputText.text=""
        binding.inputText.setTextSize(TypedValue.COMPLEX_UNIT_SP,44F)
        binding.outputText.setTextSize(TypedValue.COMPLEX_UNIT_SP,37F)
        var lastDigit = false
        var checkOpeartor = false

    }

    private fun evaluate() {
        if (checkOpeartor == true) {
            val txt = binding.inputText.text.toString()
            expression= ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                binding.outputText.visibility = View.VISIBLE
                binding.outputText.text= "= " + result.toString()

            } catch (e: ArithmeticException) {
                Log.d("TAG", "ERROR")
                binding.outputText.text="ERROR"
            }
        }
    }

}