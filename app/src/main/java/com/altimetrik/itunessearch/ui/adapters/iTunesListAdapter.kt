package com.altimetrik.itunessearch.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altimetrik.itunessearch.R
import com.altimetrik.itunessearch.models.Result
import com.altimetrik.itunessearch.ui.CustomClickListener
import com.altimetrik.itunessearch.utilities.AppConstants
import com.altimetrik.itunessearch.utilities.Utility
import com.squareup.picasso.Picasso

class iTunesListAdapter(private val list: ArrayList<Any>, private val cart: Boolean,private val customClickListener: CustomClickListener?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): iTuneViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return iTuneViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val iTune: Result = list[position] as Result
        (holder as iTuneViewHolder).bind(iTune)
        if (cart) {
            holder.cbx.visibility = View.GONE
        }
        holder.cbx.setOnClickListener {
            when{
                holder.cbx.isChecked->iTune.isCart=true
                else->iTune.isCart=false
            }
            customClickListener?.customClick(holder.cbx,position)
        }

    }


    override fun getItemCount(): Int = list.size


    class iTuneViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_view, parent, false)) {
        private val ivArtWork: ImageView
        private val context: Context
        private val tvArtistName: TextView
        private val tvTrackName: TextView
        private val tvCollectionName: TextView
        private val tvCollectionPrice: TextView
        private val tvReleaseDate: TextView
        val cbx: CheckBox

        init {
            context = parent.context
            ivArtWork = itemView.findViewById(R.id.ivArtWork)
            tvArtistName = itemView.findViewById(R.id.tvArtistName)
            tvTrackName = itemView.findViewById(R.id.tvTrackName)
            tvCollectionName = itemView.findViewById(R.id.tvCollectionName)
            tvCollectionPrice = itemView.findViewById(R.id.tvCollectionPrice)
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate)
            cbx = itemView.findViewById(R.id.cbx)
        }

        fun bind(iTune: Result) {
            val url = iTune.artworkUrl100
            val artistName = iTune.artistName ?: context.getString(R.string.no_content)
            val trackName = iTune.trackName ?: context.getString(R.string.no_content)
            val collectionName = iTune.collectionName ?: context.getString(R.string.no_content)
            val releaseDate = iTune.releaseDate ?: context.getString(R.string.no_content)
            val collectionPrice = iTune.collectionPrice ?: 0.0
            Picasso.with(context).load(url).fit().centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivArtWork)
            tvArtistName.text = artistName
            tvTrackName.text = trackName
            tvCollectionName.text = collectionName
            tvCollectionPrice.text = "${collectionPrice}$"
            tvReleaseDate.text = Utility.convertDate(
                releaseDate,
                AppConstants.YYYY_MM_DD_T_HH_MM_SS_Z,
                AppConstants.YYYY_MM_DD_HH_MM
            )
            val checked=iTune.isCart
            cbx.isChecked= checked?:false

        }


    }


}