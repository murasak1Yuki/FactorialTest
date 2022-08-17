package ru.shvec.factorialtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.shvec.factorialtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel()
        binding.buttonCalculate.setOnClickListener {
            Log.d("MainActivity", "hi")
            viewModel.calculate(binding.editTextNumber.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.progress.observe(this) {
            if (it) {
                binding.progressBarLoading.visibility = View.VISIBLE
                binding.buttonCalculate.isEnabled = false
            } else {
                binding.progressBarLoading.visibility = View.GONE
                binding.buttonCalculate.isEnabled = true
            }
        }
        viewModel.error.observe(this) {
            if (it) {
                Toast.makeText(
                    this,
                    "You did not entered value",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.factorial.observe(this) {
            binding.textViewFactorial.text = it
        }
    }
}