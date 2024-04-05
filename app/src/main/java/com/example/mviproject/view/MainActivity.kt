package com.example.mviproject.view

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mviproject.R
import com.example.mviproject.databinding.ActivityMainBinding
import com.example.mviproject.viewmodel.MainIntent
import com.example.mviproject.viewmodel.MainState
import com.example.mviproject.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var mainXml : ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var adapter = ImageAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainXml = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainXml.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUI()
        setupObservables()
    }



    private fun setupUI() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainXml.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mainXml.recyclerView.adapter = adapter

        mainXml.buttonFetchData.setOnClickListener {
            lifecycleScope.launch{
                mainViewModel.userIntent.send(MainIntent.FetchData)
            }
        }
    }

    private fun setupObservables() {
        lifecycleScope.launch {
            mainViewModel.state.collect{collector ->
                when(collector) {
                    is MainState.Idle -> {}
                    is MainState.Loading -> {
                        mainXml.buttonFetchData.visibility = View.GONE
                        mainXml.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Data -> {
                        mainXml.buttonFetchData.visibility = View.GONE
                        mainXml.progressBar.visibility = View.GONE
                        mainXml.recyclerView.visibility = View.VISIBLE
                        collector.imageData.let{
                            adapter.newImageData(it)
                        }
                    }
                    is MainState.Error -> {
                        mainXml.buttonFetchData.visibility = View.GONE
                        mainXml.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, collector.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}