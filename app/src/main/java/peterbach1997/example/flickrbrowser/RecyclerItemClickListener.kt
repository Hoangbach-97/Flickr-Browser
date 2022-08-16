package peterbach1997.example.flickrbrowser

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(context:Context, recyclerView:RecyclerView, private val listener: OnRecyclerClickListener)
    : RecyclerView.SimpleOnItemTouchListener()
{
    private  val TAG =  "RecyclerItemClickListener"
    interface OnRecyclerClickListener{
        fun onItemClick(view:View, position:Int)
        fun onItemLongClick(view: View, position: Int)
    }

    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.i(TAG, "onSingleTapUp: start")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            if (childView != null) {
                listener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            Log.i(TAG, "onLongPress: start")
            val childView = recyclerView.findChildViewUnder(e.x, e.y)
            if (childView != null) {
                listener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.i(TAG, "onInterceptTouchEvent: start $e")
        val result = gestureDetector.onTouchEvent(e)
        Log.i(TAG, "onInterceptTouchEvent:  returning $result")
        return result
    }
}