package swaix.dev.motionrecyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_item.view.*
import swaix.dev.motionrecyclerview.R

data class Model(@DrawableRes val icon: Int, @StringRes val title: Int)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

class RowAdapter(private val dataset: List<Model>, private val onItemTouch: (View, Model) -> Unit) :
    RecyclerView.Adapter<RowViewHolder>() {

    var lastTouched: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder =
        RowViewHolder(parent.inflate(R.layout.row_item))

    override fun getItemCount(): Int = dataset.size


    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        holder.bind(dataset[position])
        holder.itemView.setOnTouchListener { v, e ->
            v.visibility = View.INVISIBLE
            lastTouched?.visibility = View.VISIBLE
            lastTouched = v as View

            onItemTouch(v, dataset[position])
            true
        }
    }
}

class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Model) {
        with(itemView.context) {
            itemView.logo.setImageDrawable(ContextCompat.getDrawable(this, item.icon))
            itemView.title.text = getString(item.title)
        }
    }
}