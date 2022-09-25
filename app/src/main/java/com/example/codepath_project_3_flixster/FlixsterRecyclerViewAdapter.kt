package com.example.codepath_project_3_flixster

import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FlixsterRecyclerViewAdapter(private val movies: List<Movies>) : RecyclerView.Adapter<FlixsterRecyclerViewAdapter.FlixsterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlixsterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie, parent, false)
        return FlixsterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlixsterViewHolder, position: Int) {
        val movie = movies[position]
        val posterPathBaseUrl = "https://image.tmdb.org/t/p/w500"
        holder.mItem = movie
        holder.mMovieTitle.text = movie.title
        holder.mMovieOverview.text = movie.overview
        holder.mMovieReleaseDate.text = "Release Date: " + movie.releaseDate
        Glide.with(holder.mView)
            .load(posterPathBaseUrl+movie.posterPath)
            .placeholder(R.drawable.placeholder_image)
            //         .error(R.drawable.not_found.xml)
            .centerInside()
            .into(holder.mMoviePoster)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class FlixsterViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Movies? = null
        val mMovieTitle: TextView = mView.findViewById<View>(R.id.titleTextView) as TextView
        val mMovieOverview: TextView = mView.findViewById<View>(R.id.overviewTextView) as TextView
        val mMovieReleaseDate: TextView = mView.findViewById<View>(R.id.releaseDateTextView) as TextView
        val mMoviePoster: ImageView = mView.findViewById<View>(R.id.posterImageView) as ImageView
    }
}