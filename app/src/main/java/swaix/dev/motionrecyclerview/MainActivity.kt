package swaix.dev.motionrecyclerview

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
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
        ) { view, model ->
            initAnimatedView(view)
            animatedView.animated_logo.setImageDrawable(ContextCompat.getDrawable(this, model.icon))
            animatedView.animated_title.text = getString(model.title)
        }
    }

    private fun initAnimatedView(view: View) {
        val set = root.getConstraintSet(R.id.start)
        set.clear(R.id.animatedView)
        set.constrainWidth(R.id.animatedView, view.width)
        set.constrainHeight(R.id.animatedView, view.height)

        set.connect(
            R.id.animatedView,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            view.getLocationOnScreen().x
        )
        set.connect(R.id.animatedView, ConstraintSet.TOP, R.id.recyclerList, ConstraintSet.TOP, 0)

        set.applyTo(root)
    }
}
