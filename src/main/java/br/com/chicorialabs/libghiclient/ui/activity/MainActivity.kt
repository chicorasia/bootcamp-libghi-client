package br.com.chicorialabs.libghiclient.ui.activity

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.libghiclient.constants.Constants
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.IS_WATCHED
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.RELEASE_DATE
import br.com.chicorialabs.libghiclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FilmClickedListener {

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

        getContentProvider()


        // criar o adapter, com interfaces para clique no checkbox e no ratingbar
        // Main precisa implementar essa interface!
        // herdar o LoaderManager aqui na Main e usá-lo para carregar os dados



//        Refatoração para aprimorar o app:
        // criar o ViewModel
        // criar a classe wrapper para o ContentProviderLiveData (ou o método para

    }

    private fun getContentProvider() {

        try {
            val url = "content://br.com.chicorialabs.libghi.provider/films"
            val data = Uri.parse(url)
            val cursor: Cursor? = contentResolver.query(
                data,
                null,
                null,
                null,
                RELEASE_DATE
            )
            filmRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = FilmClientAdapter(this@MainActivity, this@MainActivity, cursor as Cursor)

            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun filmCheckBoxClickedListener(cursor: Cursor?) {
       TODO("Not yoet implemented")
    }

    override fun filmRatingChangedListener(cursor: Cursor?) {
//        TODO("Not yet implemented")
    }
}