package br.com.chicorialabs.libghiclient.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.libghiclient.constants.Constants
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.IS_WATCHED
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.RATING
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.RELEASE_DATE
import br.com.chicorialabs.libghiclient.databinding.ActivityMainBinding
import javax.security.auth.login.LoginException

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
                _ID
            )

            Log.i("lib_ghi", "getContentProvider: cursor count: ${cursor?.count}")
            val adapter = FilmClientAdapter(
                this@MainActivity,
                this@MainActivity,
                cursor as Cursor
            )
//            adapter.setHasStableIds(true)

            filmRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                this.adapter = adapter

            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun filmCheckBoxClickedListener(cursor: Cursor?, isChecked: Boolean) {

/*        Substituir toda essa complexidade por uma chamada por meio de Intent, passando o valor a ser gravado (true / false);
          O resto do tratamento deve acontecer no ContentProvider.
          Se funcionar, posso trazer o listener de volta para dentro do adapter como um object expression.
*/

        val url = "content://br.com.chicorialabs.libghi.provider/films"
        val data = Uri.parse(url)
        val id = cursor?.getLong(cursor.getColumnIndex(_ID))
        val values = ContentValues().apply {
            if (isChecked) {
                put(IS_WATCHED, 1)
            } else {
                put(IS_WATCHED, 0)
            }
        }
//

        val updateUri = contentResolver.update(
            Uri.withAppendedPath(data, id.toString()),
            values,
            "_ID = ?",
            arrayOf(id.toString())
        )


        Log.i("lib_ghi", "filmCheckBoxClickedListener: atualizada $updateUri, $id")
    }

    override fun filmRatingChangedListener(cursor: Cursor?, rating: Float) {

        val url = "content://br.com.chicorialabs.libghi.provider/films"
        val data = Uri.parse(url)
        val id = cursor?.getLong(cursor.getColumnIndex(_ID))
        val values = ContentValues().apply {
            put(Constants.RATING, rating.toInt())
        }
//

        val updateUri = contentResolver.update(
            Uri.withAppendedPath(data, id.toString()),
            values,
            "_ID = ?",
            arrayOf(id.toString())
        )

        Log.i("lib_ghi", "filmCheckBoxClickedListener: atualizada $updateUri, " +
                "$id com valor ${cursor?.getInt(cursor?.getColumnIndex(RATING))}")


    }

}