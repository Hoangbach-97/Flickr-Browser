package peterbach1997.example.flickrbrowser

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class GetFlickrJsonData(val listener: OnDataAvailable) :
    AsyncTask<String, Void, ArrayList<Photo>>() {

    private val TAG = "GetFlickrJsonData"

    interface OnDataAvailable {
        fun onDataAvailable(data: List<Photo>)
        fun error(e: Exception)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Photo> {
        Log.d(TAG, "doInBackground: starts")
        val photoList = ArrayList<Photo>()
        try {
            val jsonData = JSONObject(params[0])
            val jsonArr = jsonData.getJSONArray("items")
            for (i in 0 until jsonArr.length()) {
                val jsonPhoto = jsonArr.getJSONObject(i)
                val title = jsonPhoto.getString("title")
                val author = jsonPhoto.getString("author")
                val authorId = jsonPhoto.getString("author_id")
                val tags = jsonPhoto.getString("tags")
                val jsonMedia = jsonPhoto.getJSONObject("media")
                val photoUrl = jsonMedia.getString("m")
                val link = photoUrl.replaceFirst("_m.jpg", "_b.jpg")

                val photoObject = Photo(title, author, authorId, link, tags, photoUrl)
                photoList.add(photoObject)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d(TAG, "doInBackground:  ***********ERROR******* ${e.message}")
            cancel(true)
            listener.error(e)
        }
        Log.d(TAG, "doInBackground:  END***************")
        return photoList
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        super.onPostExecute(result)
        listener.onDataAvailable(result)
        Log.d(TAG, "onPostExecute:  END***************")
    }
}