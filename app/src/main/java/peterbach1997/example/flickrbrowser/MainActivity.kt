package peterbach1997.example.flickrbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import peterbach1997.example.flickrbrowser.databinding.ActivityMainBinding
import peterbach1997.example.flickrbrowser.databinding.ContentMainBinding

class MainActivity : AppCompatActivity(), GetRawData.OnDownloadComplete,
    GetFlickrJsonData.OnDataAvailable,
    RecyclerItemClickListener.OnRecyclerClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ContentMainBinding
    private val flickrRecyclerViewAdapter = FlickrRecyclerViewAdapter(ArrayList())


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: **********CALLED*********")
        super.onCreate(savedInstanceState)

        binding = ContentMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setSupportActionBar(binding.toolbar)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                binding.recyclerView,
                this
            )
        )
        binding.recyclerView.adapter = flickrRecyclerViewAdapter
        val url = createUri(
            "https://www.flickr.com/services/feeds/photos_public.gne",
            "arduino",
            "en-us",
            true
        )
        val getRawData = GetRawData(this)
        getRawData.execute(url)
    }

    private fun createUri(
        baseUrl: String,
        searchCriteria: String,
        lang: String,
        matchAll: Boolean
    ): String {
        Log.d(TAG, "createUri: *******************START")
        return Uri.parse(baseUrl)
            .buildUpon()
            .appendQueryParameter("tags", searchCriteria)
            .appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY")
            .appendQueryParameter("lang", lang)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .build()
            .toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: ***********CALLED**********")
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ********************CALLED************")
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.item_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp: **********************CALLED******************")
        return super.onSupportNavigateUp()
    }

    companion object {
        //         class variable: static in JAVA  ->one copy created -> performance
        private const val TAG = "MainActivity"
    }


    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete: ************CALLED******* data: $data")
            val getFlickrJsonData = GetFlickrJsonData(this)
            getFlickrJsonData.execute(data)

        } else {
            Log.d(TAG, "onDownloadComplete: ************ERROR********* $data")
        }

    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG, "onDataAvailable: called")
        flickrRecyclerViewAdapter.loadNewPhoto(data)
        Log.d(TAG, "onDataAvailable: ENDED")
    }

    override fun error(e: Exception) {
        Log.d(TAG, "error: called")
    }

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(this, "onItemClick: $position", Toast.LENGTH_LONG).show()
        Log.i(TAG, "onItemClick: ******************CALLED")
        val photo = flickrRecyclerViewAdapter.getPhoto(position)
        if (photo != null) {
            val intent = Intent(this, PhotoDetailActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER, photo)
            startActivity(intent)
        }

    }

    override fun onItemLongClick(view: View, position: Int) {
        Toast.makeText(this, "onItemLongClick: $position", Toast.LENGTH_LONG).show()
        Log.i(TAG, "onItemLongClick: ******************CALLED")
    }


}