package com.example.codepath_project_3_flixster

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


private const val BASE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
class FlixsterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flixster, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.layoutManager = StaggeredGridLayoutManager(2,1)
        else
            recyclerView.layoutManager = StaggeredGridLayoutManager(1,1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()
        val httpClient = AsyncHttpClient()
        val requestParams = RequestParams()
        //requestParams["api_key"] = API_KEY
        httpClient[
                BASE_URL,
                requestParams,
                object : JsonHttpResponseHandler() {
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.e("ERROR", "Code 200 not found")
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                        Log.v("SUCCESS", "Request successful")
                        Log.v("json response",json.toString())
                        val resultsArrayJSON : JSONArray = json?.jsonObject?.get("results") as JSONArray
                        val gson = Gson()
                        val arrayBookType = object : TypeToken<List<Movies>>() {}.type
                        val models : List<Movies> = gson.fromJson(resultsArrayJSON.toString(),arrayBookType)
                        recyclerView.adapter = FlixsterRecyclerViewAdapter(models)
                        progressBar.hide()
                        }

                }
        ]
    }

    fun movieFromJson(jsonObject: JSONObject?): Movies? {
        val arrayMovieType = object : TypeToken<List<Movies>>() {}.type
        val movie = Movies()
        // Deserialize json into object fields
        try {
            movie.title = jsonObject?.getString("title")
            movie.overview = jsonObject?.getString("overview")
            movie.posterPath = jsonObject?.getString("poster_path")
            movie.releaseDate = jsonObject?.getString("release_date")
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }
        return movie
    }
}