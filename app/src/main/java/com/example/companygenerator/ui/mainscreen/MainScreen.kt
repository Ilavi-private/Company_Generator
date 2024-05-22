package com.example.companygenerator.ui.mainscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.companygenerator.R

class MainScreen : Fragment() {
    private lateinit var viewModel: MainScreenView
    private lateinit var button: Button

    private lateinit var randomNum: TextView

    private lateinit var meanValue: EditText
    private lateinit var dispersionValue: EditText



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainScreenView::class.java]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        meanValue = view.findViewById(R.id.mean_val)
        dispersionValue = view.findViewById(R.id.variance_value)

        button = view.findViewById(R.id.get_random_num)

        randomNum = view.findViewById(R.id.random_number_result)


        // записываем текст в meanValue
        meanValue.setText(viewModel.mean?.toString()?:getString(R.string.empty))
        // записываем текст в dispersionValue
        dispersionValue.setText(viewModel.dispersion?.toString()?: getString(R.string.empty))
        // узаписываем текст на кнопке
        button.setText(R.string.button_count)
        // записываем текст в randomNum
        randomNum.text = viewModel.randomNum?.toString()?: getString(R.string.app_result)


        dispersionValue.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val input = s.toString()  // получаем число и записываем в inout
                viewModel.dispersion = input.toDoubleOrNull()
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

        // обработчик изменеия значений в полях
        meanValue.addTextChangedListener(object : TextWatcher{
            // переопределение beforeTextChanged
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val input = s.toString()  // присваиваем input введеный текст
                viewModel.mean = input.toDoubleOrNull()
            }


            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

        // обрабатываем нажатия кнопки
        button.setOnClickListener {
            // если кнопка нажата
            if (viewModel.handleGeneration() != null)
            {
                // получаем и передаем число из распределения
                randomNum.text = viewModel.randomNum.toString()
            }
            // в противном случае оставляем 0
            else randomNum.text = getString(R.string.app_result)
        }

        return view
    }

}
