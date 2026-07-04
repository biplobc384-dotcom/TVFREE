package com.arifur.tvfree

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class ChannelAdapter(
    private var channelList: List<Channel>,
    private val onChannelClick: (Channel) -> Unit
) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    class ChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val channelLogo: ImageView = itemView.findViewById(R.id.channelLogo)
        val channelName: TextView = itemView.findViewById(R.id.channelName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_channel, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = channelList[position]
        holder.channelName.text = channel.name

        // Coil ব্যবহার করে ইন্টারনেট থেকে চ্যানেলের লোগো লোড করা হচ্ছে
        holder.channelLogo.load(channel.image) {
            crossfade(true)
            error(android.R.drawable.ic_dialog_alert) // লোগো লোড না হলে এরর আইকন দেখাবে
        }

        // চ্যানেলে ক্লিক করলে ভিডিও প্লে হওয়ার কমান্ড
        holder.itemView.setOnClickListener {
            onChannelClick(channel)
        }
    }

    override fun getItemCount(): Int = channelList.size

    // সার্চ করার সময় লিস্ট আপডেট করার ফাংশন
    fun updateList(newList: List<Channel>) {
        channelList = newList
        notifyDataSetChanged()
    }
}