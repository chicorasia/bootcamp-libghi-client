package br.com.chicorialabs.libghiclient.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.libghiclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val filmRecyclerView: RecyclerView by lazy {
        binding.mainFilmRecyclerview
    }

    private val progressBar: ProgressBar by lazy {
        binding.mainProgressbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}