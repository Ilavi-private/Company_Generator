package com.example.companygenerator.ui.mainscreen  // объявление пакета, в котором находится класс MainScreen

import androidx.lifecycle.ViewModelProvider  // импорт класса ViewModelProvider из библиотеки androidx.lifecycle
import android.os.Bundle  // импорт класса Bundle
import android.text.Editable  // импорт класса Editable
import android.text.TextWatcher  // импорт интерфейса TextWatcher
import androidx.fragment.app.Fragment  // импорт класса Fragment из библиотеки androidx.fragment.app
import android.view.LayoutInflater  // импорт класса LayoutInflater
import android.view.View  // импорт класса View
import android.view.ViewGroup  // импорт класса ViewGroup
import android.widget.Button  // импорт класса Button
import android.widget.EditText  // импорт класса EditText
import android.widget.TextView  // импорт класса TextView
import com.example.angelicahelp.R  // импорт класса R из пакета com.example.angelicahelp

// объявление класса MainScreen, который является подклассом Fragment
class MainScreen : Fragment() {
    private lateinit var viewModel: MainScreenView  // объявление приватного свойства viewModel класса MainScreenViewModel
    private lateinit var meanValue: EditText  // объявление приватного свойства meanValue класса EditText
    private lateinit var varianceValue: EditText  // объявление приватного свойства varianceValue класса EditText
    private lateinit var randomNumber: TextView  // объявление приватного свойства randomNumber класса TextView
    private lateinit var btn: Button  // объявление приватного свойства btn класса Button

    // переопределяем функцию onCreate, который принимает параметр savedInstanceState
    override fun onCreate(savedInstanceState: Bundle?) {
        // вызываем метод onCreate из родительского класса и передаем ему параметр savedInstanceState
        super.onCreate(savedInstanceState)
        // инициализация свойства viewModel с помощью ViewModelProvider
        viewModel = ViewModelProvider(this).get(MainScreenView::class.java)
    }

    // переопределение метода onCreateView, который создает и возвращает View для фрагмента
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // создание View, используя разметку из файла fragment_main_screen.xml
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        // инициализация свойства meanValue с помощью findViewById и идентификатора mean_val
        meanValue = view.findViewById(R.id.mean_val)
        // инициализация свойства varianceValue с помощью findViewById и идентификатора variance_value
        varianceValue = view.findViewById(R.id.variance_value)
        // инициализация свойства randomNumber с помощью findViewById и идентификатора random_number_result
        randomNumber = view.findViewById(R.id.random_number_result)
        // инициализация свойства btn с помощью findViewById и идентификатора get_random_num
        btn = view.findViewById(R.id.get_random_num)

        // установка текста в поле meanValue, используя значение из viewModel или строку "empty"
        meanValue.setText(viewModel.mean?.toString()?:getString(R.string.empty))
        // установка текста varianceValue
        varianceValue.setText(viewModel.dispersion?.toString()?: getString(R.string.empty))
        // установка текста для TextView randomNumber
        randomNumber.setText(viewModel.randomNum?.toString()?: getString(R.string.zero_count))
        // устанавливаем текст для кнопки
        btn.setText(R.string.button_count)


        // проверяется изменение текста в EditText
        meanValue.addTextChangedListener(object : TextWatcher{
            // переопределение beforeTextChanged
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            // переопределение onTextChanged
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val input = p0.toString()  // принимаем входной тескт и присваиваем input
                viewModel.mean = input.toDoubleOrNull()
            }

            // переопределение afterTextChanged
            override fun afterTextChanged(p0: Editable?) {
                return
            }
        })

        varianceValue.addTextChangedListener(object : TextWatcher {
            // переопределение beforeTextChanged
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            // переопределение onTextChanged
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val input = p0.toString()  // тоже считываем число и перерабатываем в строчку
                viewModel.dispersion = input.toDoubleOrNull()
            }

            // переопределение afterTextChanged
            override fun afterTextChanged(p0: Editable?) {
                return
            }
        })

        // обрабатываем нажатия кнопки
        btn.setOnClickListener {
            // если кнопка нажата
            if (viewModel.handleGeneration() != null)
            {
                // получаем и передаем число из распределения
                randomNumber.setText(viewModel.randomNum.toString())
            }
            // в противном случае оставляем текст из result
            else randomNumber.setText(getString(R.string.app_result))
        }

        return view  // возвращаем view
    }

}