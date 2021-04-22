package br.com.chicorialabs.libghiclient.ui.activity

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns._ID
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
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
    private val listener: FilmClickedListener,
    private var mCursor: Cursor
) : RecyclerView.Adapter<FilmClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmClientViewHolder {
        val binding = ListaFilmeItemBinding.inflate(
            LayoutInflater.from(context),
            parent, false
        )
        return FilmClientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmClientViewHolder, position: Int) {
        mCursor?.let {
            it.moveToPosition(position)
            holder.bind(it)
            Log.i("lib_ghi", "onBindViewHolder: ${holder.filmTitleTv.text}, " +
                    "position $position, cursor ${it.position}")
        }

        holder.filmIsWatchedCb.setOnCheckedChangeListener { _, isChecked ->
            listener.filmCheckBoxClickedListener(mCursor, isChecked, holder.id)
        }

        holder.filmRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            listener.filmRatingChangedListener(mCursor, rating, holder.id)
        }

    }

    override fun getItemCount(): Int {
        return mCursor.count
    }

}

class FilmClientViewHolder(binding: ListaFilmeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    val filmTitleTv = binding.cardFilmTitle
    val filmOriginalTitleTv = binding.cardFilmOriginalTitle
    val filmDirectorTv = binding.cardFilmDirector
    val filmYearTv = binding.cardFilmYear
    val filmIsWatchedCb = binding.cardIswatchedCb
    val filmRatingBar = binding.cardRatingbar
    var id: Long = 0

    fun bind(cursor: Cursor) {
        cursor.let {
            id = it.getLong(it.getColumnIndex(_ID))
            filmTitleTv.text = it.getString(it.getColumnIndex(TITLE))
            filmOriginalTitleTv.text = it.getString(it.getColumnIndex(ORIGINAL_TITLE))
            filmDirectorTv.text = it.getString(it.getColumnIndex(DIRECTOR))
            filmYearTv.text = it.getString(it.getColumnIndex(RELEASE_DATE))
            with(filmIsWatchedCb) {
                this.isChecked = it.getInt(it.getColumnIndex(IS_WATCHED)) == 1
            }
            filmRatingBar.rating = it.getInt(it.getColumnIndex(RATING)).toFloat()
        }
    }
}

interface FilmClickedListener {

    fun filmCheckBoxClickedListener(cursor: Cursor?, isChecked: Boolean, position: Long)

    fun filmRatingChangedListener(cursor: Cursor?, rating: Float, position: Long)

}

