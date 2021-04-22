package br.com.chicorialabs.libghiclient.ui.activity

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.DIRECTOR
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.IS_WATCHED
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.ORIGINAL_TITLE
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.RATING
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.RELEASE_DATE
import br.com.chicorialabs.libghiclient.constants.Constants.Companion.TITLE
import br.com.chicorialabs.libghiclient.databinding.ListaFilmeItemBinding

class FilmClientAdapter(
    val context: Context,
    private val filmClickedListener: FilmClickedListener,
    private var mCursor: Cursor? = null
) : RecyclerView.Adapter<FilmClientViewHolder>() {

    fun setCursor(newCursor: Cursor?) {
        mCursor = newCursor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmClientViewHolder {
        val binding = ListaFilmeItemBinding.inflate(
            LayoutInflater.from(context),
            parent, false
        )
        return FilmClientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmClientViewHolder, position: Int) {
        //mover o cursor até a posição
        mCursor?.let {
            it.moveToPosition(position)
            holder.bind(it)
        }

        holder.filmIsWatchedCb.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(context, "Clicou em ${holder.filmTitleTv.text}!", Toast.LENGTH_LONG).show()
            val url = "content://br.com.chicorialabs.libghi.provider/films"
            val data = Uri.parse(url)
            val values = ContentValues().apply {
                if (isChecked) {
                    put(IS_WATCHED, 1)
                } else {
                    put(IS_WATCHED, 0)
                }
            }

//           context.contentResolver.update(
//               Uri.withAppendedPath(data, mCursor?.getInt(0).toString()),
//               values,
//               null
//           )

        }

    }

    override fun getItemCount(): Int {
        return mCursor?.count ?: 0
    }

}

class FilmClientViewHolder(binding: ListaFilmeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    val filmTitleTv = binding.cardFilmTitle
    val filmOriginalTitleTv = binding.cardFilmOriginalTitle
    val filmDirectorTv = binding.cardFilmDirector
    val filmYearTv = binding.cardFilmYear
    val filmIsWatchedCb = binding.cardIswatchedCb
    val filmRatingBar = binding.cardRatingbar

    fun bind(cursor: Cursor) {
        cursor.let {
            filmTitleTv.text = it.getString(it.getColumnIndex(TITLE))
            filmOriginalTitleTv.text = it.getString(it.getColumnIndex(ORIGINAL_TITLE))
            filmDirectorTv.text = it.getString(it.getColumnIndex(DIRECTOR))
            filmYearTv.text = it.getString(it.getColumnIndex(RELEASE_DATE))
            with(filmIsWatchedCb) {
                this.isChecked = it.getInt(it.getColumnIndex(IS_WATCHED)) == 1
            }
            filmRatingBar.numStars = it.getInt(it.getColumnIndex(RATING))
        }


    }


}

interface FilmClickedListener {

    fun filmCheckBoxClickedListener(cursor: Cursor?)

    fun filmRatingChangedListener(cursor: Cursor?)

}

