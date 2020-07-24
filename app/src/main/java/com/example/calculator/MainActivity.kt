package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {
    var lastNumeric=false
    var lastDecimal=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onDigit(view: View) {
        textCalculator.append((view as Button).text)
        lastNumeric=true


    }
    fun onBack(view: View) {

            var value = textCalculator.text.toString()
            if (value.isNotEmpty())
                textCalculator.text = value.substring(0, value.length - 1)

    }

    fun onClear(view:View)
    {
        textCalculator.text=""
        lastNumeric=false
        lastDecimal=false
    }
    fun onDecimal(view:View)
    {
        if(lastNumeric && !lastDecimal)
        {
            textCalculator.append((view as Button).text)
            lastDecimal=true
            lastNumeric=false
        }
    }
    fun onEqual(view:View) {
       if (lastNumeric) {
           try {


               var value = textCalculator.text.toString()
               var valueNew: String = value
               var neg = false
               if (value.startsWith("-")) {
                   valueNew = value.substring(1)
                   neg = true
               }
               var splitValue: List<String> = valueNew.split("/", "+", "*", "-")
               var newSplitValue = splitValue.toMutableList()
               val splitLength = splitValue.size
               var splitString: String = ""
               for (i in 0..valueNew.length - 1) {
                   if (value[i] == '/' || value[i] == '*' || value[i] == '-' || value[i] == '+')
                       splitString += value[i]

               }
               if (neg) {
                   newSplitValue[0] = "-" + newSplitValue[0]
                   splitString = splitString.substring(1)

               }
               var sum: Double
               for (i in splitString.indices) {
                   if (splitString[i] == '*') {
                       sum = newSplitValue[i].toDouble() * newSplitValue[i + 1].toDouble()
                       newSplitValue[i + 1] = sum.toString()
                   } else if (splitString[i] == '+') {
                       sum = newSplitValue[i].toDouble() + newSplitValue[i + 1].toDouble()
                       newSplitValue[i + 1] = sum.toString()
                   } else if (splitString[i] == '-') {
                       sum = newSplitValue[i].toDouble() - newSplitValue[i + 1].toDouble()
                       newSplitValue[i + 1] = sum.toString()
                   } else if (splitString[i] == '/') {
                       sum = newSplitValue[i].toDouble() / newSplitValue[i + 1].toDouble()
                       newSplitValue[i + 1] = sum.toString()
                   }


               }
               value = removeZeroAfterDot(newSplitValue[newSplitValue.size - 1])

                   if (value.contains(".")&&!value.contains("E")) {
                       var index = value.indexOf(".")
                       if (value.length > (index + 4))
                           value = value.substring(0, index + 4)
                   }

               textCalculator.text = value
           }
           catch (e:ArithmeticException)
           {
               e.printStackTrace()
           }
       }
   }

     fun onOperator(view:View)
    {
        if(lastNumeric ||(((view as Button).text)=="-") && textCalculator.text=="")
        {
            textCalculator.append((view as Button).text)
            lastDecimal=false
            lastNumeric=false
        }
    }

    private fun removeZeroAfterDot(result: String): String {

        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }

}
