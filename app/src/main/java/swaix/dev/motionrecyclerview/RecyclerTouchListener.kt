package swaix.dev.motionrecyclerview

import android.util.Log
import android.view.MotionEvent
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING


open class RecyclerTouchListener : RecyclerView.OnItemTouchListener {


    @CallSuper
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val requestCancelDisallowInterceptTouchEvent = rv.scrollState == SCROLL_STATE_SETTLING
        val action = e.actionMasked

        when (action) {
            MotionEvent.ACTION_DOWN -> if (requestCancelDisallowInterceptTouchEvent) {
                rv.parent.requestDisallowInterceptTouchEvent(false)

                // only if it touched the top or the bottom. Thanks to @Sergey's answer.
                if (!rv.canScrollVertically(-1) || !rv.canScrollVertically(1)) {
                    // stop scroll to enable child view to get the touch event
                    rv.stopScroll()
                    // do not consume the event
                    return false
                }
            }
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        Log.e("TAG", "${e.action}")
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}