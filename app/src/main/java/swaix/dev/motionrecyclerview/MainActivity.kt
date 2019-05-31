package swaix.dev.motionrecyclerview

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import swaix.dev.motionrecyclerview.adapters.Model
import swaix.dev.motionrecyclerview.adapters.RowAdapter


fun View.getLocationOnScreen(): Point {
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    return Point(location[0], location[1])
}

class MainActivity : AppCompatActivity() {

    lateinit var anchorView: View
    lateinit var anchorItem: Model

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LayoutInflater.from(this).inflate(R.layout.row_item, animatedView, false)

        recyclerList.adapter = RowAdapter(
            listOf(
                Model(R.drawable.ic_0, R.string.title_0),
                Model(R.drawable.ic_1, R.string.title_1),
                Model(R.drawable.ic_2, R.string.title_2),
                Model(R.drawable.ic_3, R.string.title_3),
                Model(R.drawable.ic_5, R.string.title_4)
            )
        ) { v, i ->
            initAnimatedView(v, View.VISIBLE, i)
            root.transitionToEnd()
        }

        animatedView.animated_logo.setOnClickListener {
            root.transitionToStart()
            root.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                }

                override fun allowsTransition(p0: MotionScene.Transition?): Boolean {
                    return true
                }

                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                }

                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                }

                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                    initAnimatedView(anchorView, View.GONE, anchorItem)
                    root.setTransitionListener(null)
                }

            })
        }

//        recyclerList.addOnItemTouchListener(RecyclerTouchElevationListener(this, recyclerList, { v, item ->
//            initAnimatedView(v)
//            animatedView.animated_logo.setImageDrawable(ContextCompat.getDrawable(this, item.icon))
//            animatedView.animated_title.text = getString(item.title)
//        }, {
//            //            animatedView.animated_logo.setOnClickListener { null}
//        }))
    }

    private fun initAnimatedView(view: View, visibility: Int, item: Model) {
        anchorView = view
        anchorItem = item
        animatedView.isClickable = visibility == View.VISIBLE
        animatedView.isFocusable = visibility == View.VISIBLE

        animatedView.animated_logo.setImageDrawable(ContextCompat.getDrawable(this, item.icon))
        animatedView.animated_title.text = getString(item.title)

        val set = root.getConstraintSet(R.id.start)
        set.clear(R.id.animatedView)
        set.constrainWidth(R.id.animatedView, view.width)
        set.constrainHeight(R.id.animatedView, view.height)
        if (view.right - view.width < 0) {
            set.connect(
                R.id.animatedView,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
                view.right
            )
        } else {
            set.connect(
                R.id.animatedView,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
                view.getLocationOnScreen().x
            )
        }
        set.setVisibility(R.id.animatedView, visibility)
        animatedView.isClickable = false
        animatedView.isFocusable = false
        set.connect(R.id.animatedView, ConstraintSet.TOP, R.id.recyclerList, ConstraintSet.TOP, 0)
        set.applyTo(root)
    }
}
