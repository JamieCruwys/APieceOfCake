package uk.co.jamiecruwys.apieceofcake.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cake.view.*
import uk.co.jamiecruwys.apieceofcake.R
import uk.co.jamiecruwys.apieceofcake.api.Cake

class CakeAdapter(private val cakes: ArrayList<Cake>, private val view: CakeItemView) : RecyclerView.Adapter<CakeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cake, parent, false)
        return CakeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CakeViewHolder, position: Int) {
        val cake = cakes[position]
        holder.itemView.cake_title.text = cake.title
        Picasso.get().load(cake.image).into(holder.itemView.cake_image)
        holder.itemView.setOnClickListener { view.onCakeClicked(cake) }
    }

    override fun getItemCount() = cakes.size

    fun setItems(items: List<Cake>) {
        cakes.clear()
        cakes.addAll(items)
        notifyDataSetChanged()
    }
}