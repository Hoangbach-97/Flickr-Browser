package peterbach1997.example.flickrbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FickrImageHolder(view: View): RecyclerView.ViewHolder(view){
    var thumbnail:ImageView = view.findViewById(R.id.thumbnail)
    var title:TextView =  view.findViewById(R.id.textView) //id=title
}
class FlickrRecyclerViewAdapter(private var photoList:List<Photo>): RecyclerView.Adapter<FickrImageHolder>() {
private val TAG = "Adapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FickrImageHolder {
//        called by the layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: ********************CALLED************")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return FickrImageHolder(view)
    }

    fun loadNewPhoto(newData: List<Photo>){
        photoList = newData
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int):Photo? {
        return if (photoList.isNotEmpty()) photoList[position] else null
    }
    override fun onBindViewHolder(holder: FickrImageHolder, position: Int) {
//       call layout manager when it wants new data in an existing view
        val photoItem = photoList[position]
        Log.d(TAG, "onBindViewHolder:  ${photoItem.title}->$position")
//        context of thumbnail, load(): load image from the URL
        Picasso.with(holder.thumbnail.context).load(photoItem.image)
            .error(R.drawable.placeholder) //display when error
            .placeholder(R.drawable.placeholder) // place holder when downloading, download background thread
            .into(holder.thumbnail) //store and display when finished
//textview hold the photo's title
        holder.title.text = photoItem.title
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ***************CALLED**********")
        return if (photoList.isNotEmpty()) photoList.size else 0
    }

}