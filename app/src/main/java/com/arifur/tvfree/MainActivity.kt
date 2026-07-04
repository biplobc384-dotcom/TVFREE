package com.arifur.tvfree

// আপনার আগের প্যাকেজ নামটিই এখানে থাকবে

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchChannel: EditText
    private lateinit var channelAdapter: ChannelAdapter

    private var channelList = mutableListOf<Channel>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI কম্পোনেন্টগুলো পরিচয় করানো হচ্ছে
        playerView = findViewById(R.id.playerView)
        recyclerView = findViewById(R.id.recyclerViewChannels)
        searchChannel = findViewById(R.id.searchChannel)

        // ExoPlayer (ভিডিও প্লেয়ার) ইনিশিয়ালাইজ করা হচ্ছে
        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        // RecyclerView সেটআপ (২ কলামের গ্রিড ভিউ)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        channelAdapter = ChannelAdapter(channelList) { channel ->
            playVideo(channel.url)
        }
        recyclerView.adapter = channelAdapter

        // ফায়ারবেস থেকে ডেটা লোড করা
        fetchChannelsFromFirebase()

        // সার্চ অপশন চালু করা
        setupSearch()
    }

    private fun fetchChannelsFromFirebase() {
        // "channels" কালেকশন থেকে রিয়েল-টাইম ডেটা আনা হচ্ছে
        db.collection("channels").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Toast.makeText(this, "Error loading channels", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            if (snapshot != null) {
                channelList.clear()
                for (document in snapshot.documents) {
                    val channel = document.toObject(Channel::class.java)
                    if (channel != null) {
                        channelList.add(channel)
                    }
                }
                channelAdapter.notifyDataSetChanged()

                // অ্যাপ ওপেন হলে লিস্টের প্রথম চ্যানেলটি অটোমেটিক প্লে হবে
                if (channelList.isNotEmpty()) {
                    playVideo(channelList[0].url)
                }
            }
        }
    }

    private fun playVideo(url: String) {
        // ওয়েব প্লেয়ারের লিংকের ভেতর থেকে সরাসরি .m3u8 লিংকটি আলাদা করার লজিক
        val actualStreamUrl = if (url.contains("?url=")) url.substringAfter("?url=") else url

        val mediaItem = MediaItem.Builder()
            .setUri(actualStreamUrl)
            .setMimeType(MimeTypes.APPLICATION_M3U8) // লাইভ HLS স্ট্রিমের জন্য
            .build()

        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
    }

    private fun setupSearch() {
        searchChannel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = channelList.filter {
                    it.name.contains(s.toString(), ignoreCase = true)
                }
                channelAdapter.updateList(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release() // অ্যাপ বন্ধ হলে প্লেয়ার মেমোরি ক্লিয়ার করবে (ল্যাগ কমানোর জন্য জরুরি)
    }
}