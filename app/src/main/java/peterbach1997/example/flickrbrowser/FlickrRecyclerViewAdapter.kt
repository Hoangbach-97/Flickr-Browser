package peterbach1997.example.flickrbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ***************CALLED**********")
        return if (photoList.isNotEmpty()) photoList.size else 0
    }

}