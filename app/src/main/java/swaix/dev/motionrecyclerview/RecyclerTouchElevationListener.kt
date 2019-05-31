package swaix.dev.motionrecyclerview

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import swaix.dev.motionrecyclerview.adapters.Model
import swaix.dev.motionrecyclerview.adapters.RowAdapter

class RecyclerTouchElevationListener(
    context: Context,
    val recyclerView: RecyclerView,
    val onItemTouched: (View, Model) -> Unit,
    val onItemReleased: () -> Unit
) :
    RecyclerTouchListener() {

    private val gestureDetector: GestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                val clickedView = recyclerView.findChildViewUnder(e2?.x ?: -1f, e2?.y ?: -1f)
                val adapter = (recyclerView.adapter as RowAdapter)

                clickedView?.let {
                    recyclerView.findContainingViewHolder(clickedView)?.apply {
                        onItemTouched(it, adapter.dataset[adapterPosition])
                    }
                }
                return super.onScroll(e1, e2, distanceX, distanceY)
            }
        })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.e("ACTION", "${e.action}")
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null) {
            val adapter = (rv.adapter as RowAdapter)
            rv.findContainingViewHolder(child)?.apply {


                gestureDetector.onTouchEvent(e)
                if (e.action == MotionEvent.ACTION_DOWN) {
                    onItemTouched(child, adapter.dataset[adapterPosition])
//                    child.visibility = View.INVISIBLE
                }
            }
        }
        if (e.action == MotionEvent.ACTION_UP) {
//            rv.children.forEach {
//                it.visibility = View.VISIBLE
//            }
            onItemReleased()
        }

        return super.onInterceptTouchEvent(rv, e)
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        Log.e("TAG", "${e.action}")
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}