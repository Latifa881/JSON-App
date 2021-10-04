package com.example.jsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TestLooperManager
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.text.isDigitsOnly
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var edEURO: EditText
    lateinit var spCurrencies: Spinner
    lateinit var tvConvertedValue: TextView
    lateinit var tvDate: TextView
    lateinit var btConvert: Button
    var cur = ""


    fun convertCurrency(num: Double) {
        if (!edEURO.text.isEmpty()) {
            val enteredNumber = edEURO.text.toString().toDouble()
            tvConvertedValue.setText((enteredNumber * num).toString())
           // Toast.makeText(baseContext, (enteredNumber * num).toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edEURO = findViewById(R.id.edEURO)
        tvConvertedValue = findViewById(R.id.tvConvertedValue)
        tvDate = findViewById(R.id.tvDate)
        spCurrencies = findViewById(R.id.spCurrencies)
        btConvert = findViewById(R.id.btConvert)

        // access the items of the list
        val countriesArrayList = arrayListOf("Choose the currency","sar", "jpy", "inr", "kwd", "usd", "qar", "aud", "cny")
        if (spCurrencies != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, countriesArrayList
            )
            spCurrencies.adapter = adapter
        }
        spCurrencies.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
               cur= countriesArrayList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }



        btConvert.setOnClickListener {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            val call: Call<CurrencyCountriesDetails>? = apiInterface!!.doGetListResources()
            call?.enqueue(object : Callback<CurrencyCountriesDetails?> {
                override fun onResponse(
                    call: Call<CurrencyCountriesDetails?>?,
                    response: Response<CurrencyCountriesDetails?>
                ) {
                    Log.d("TAG", response.code().toString() + "")
                    var displayResponse = ""
                    val resource: CurrencyCountriesDetails? = response.body()
                    val date = resource?.date
                    tvDate.text = "Date: $date"
                    val curList = resource?.eur

                    val selectedCurrency = cur

                    //Toast.makeText(baseContext,cur,Toast.LENGTH_LONG).show()

                    when {
                        selectedCurrency == "sar" -> {
                            convertCurrency(curList?.sar?.toDouble()!!)
                        }
                        selectedCurrency == "jpy" -> {
                            convertCurrency(curList?.jpy?.toDouble()!!)
                        }
                        selectedCurrency == "inr" -> {
                            convertCurrency(curList?.inr?.toDouble()!!)
                        }
                        selectedCurrency == "kwd" -> {
                            convertCurrency(curList?.kwd?.toDouble()!!)
                        }
                        selectedCurrency == "usd" -> {
                            convertCurrency(curList?.usd?.toDouble()!!)
                        }
                        selectedCurrency == "qar" -> {
                            convertCurrency(curList?.qar?.toDouble()!!)
                        }
                        selectedCurrency == "aud" -> {
                            convertCurrency(curList?.aud?.toDouble()!!)
                        }
                        selectedCurrency == "cny" -> {
                            convertCurrency(curList?.cny?.toDouble()!!)
                        }

                    }

                }

                override fun onFailure(call: Call<CurrencyCountriesDetails?>, t: Throwable?) {
                    call.cancel()
                }
            })
        }

    }
}