package br.com.chicorialabs.libghiclient.ui.activity

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns._ID
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.IS_WATCHED
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.RATING
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

        getContentProvider()
//        Refatoração para aprimorar o app:
//         criar o ViewModel
//         criar a classe wrapper para o ContentProviderLiveData (ou o método para

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
                _ID
            )

            Log.i("lib_ghi", "getContentProvider: cursor count: ${cursor?.count}")
            val adapter = FilmClientAdapter(
                this@MainActivity,
                object : FilmClickedListener {
                    override fun filmCheckBoxClickedListener(
                        cursor: Cursor?,
                        isChecked: Boolean,
                        id: Long
                    ) {
                        val url = "content://br.com.chicorialabs.libghi.provider/films"
                        val data = Uri.parse(url)
                        val values = ContentValues().apply {
                            if (isChecked) {
                                put(IS_WATCHED, 1)
                            } else {
                                put(IS_WATCHED, 0)
                            }
                        }

                        val updateUri = contentResolver.update(
                            Uri.withAppendedPath(data, id.toString()),
                            values,
                            "_ID = ?",
                            arrayOf(id.toString())
                        )


                        Log.i("lib_ghi", "filmCheckBoxClickedListener: atualizada $updateUri, $id")
                    }

                    override fun filmRatingChangedListener(
                        cursor: Cursor?,
                        rating: Float,
                        id: Long
                    ) {
                        val url = "content://br.com.chicorialabs.libghi.provider/films"
                        val data = Uri.parse(url)
                        val values = ContentValues().apply {
                            put(RATING, rating.toInt())
                        }

                        contentResolver.update(
                            Uri.withAppendedPath(data, id.toString()),
                            values,
                            "_ID = ?",
                            arrayOf(id.toString())
                        )
                    }

                },
                cursor as Cursor
            )


            filmRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                this.adapter = adapter
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

}