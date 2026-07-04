package com.arifur.tvfree // আপনার আসল প্যাকেজ নামটিই রাখবেন

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Intent
import android.content.res.Configuration


class MainActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchChannel: EditText
    private lateinit var channelAdapter: ChannelAdapter

    private var channelList = mutableListOf<Channel>()
    private val db = FirebaseFirestore.getInstance()

    // ফুলস্ক্রিন স্টেট ট্র্যাক করার জন্য ভেরিয়েবল
    private var isPlayerFullScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerView = findViewById(R.id.playerView)
        recyclerView = findViewById(R.id.recyclerViewChannels)
        searchChannel = findViewById(R.id.searchChannel)

        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        // প্লেয়ারের ফুলস্ক্রিন বাটনের লজিক
        playerView.setFullscreenButtonClickListener { isFullScreen ->
            if (isFullScreen) {
                enterFullScreen()
            } else {
                exitFullScreen()
            }
        }

        // ন্যাভিগেশন বার হাইড করার জন্য কোড
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        // সোয়াইপ করলে যেন ন্যাভিগেশন বার কিছুক্ষণের জন্য দেখা যায় এবং আবার হাইড হয়ে যায়
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // শুধু ন্যাভিগেশন বার (নিচের অংশ) হাইড করতে চাইলে:
        // windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

        // বিঃদ্রঃ আপনি যদি উপরের স্ট্যাটাস বার (যেখানে চার্জ, সময় দেখায়) সহ সবকিছু হাইড করে ফুল-স্ক্রিন করতে চান,
        // তবে উপরের লাইনের বদলে নিচের লাইনটি ব্যবহার করবেন:
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        // 📱 ফোনের ব্যাক বোতাম (Back Button) হ্যান্ডেল করার লজিক
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isPlayerFullScreen) {
                    // ফুলস্ক্রিন থাকলে সেটা বন্ধ করে স্বাভাবিক মুডে ফিরবে
                    exitFullScreen()
                } else {
                    // ফুলস্ক্রিন না থাকলে স্বাভাবিকভাবে অ্যাপ থেকে বের হয়ে যাবে
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            }
        })

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        channelAdapter = ChannelAdapter(emptyList()) { channel ->
            playVideo(channel.url)
        }
        recyclerView.adapter = channelAdapter

        fetchChannelsFromFirebase()
        setupSearch()
        val btnOpenSupport = findViewById<Button>(R.id.btnOpenSupport)
        btnOpenSupport.setOnClickListener {
            val intent = Intent(this, SupportActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // সাপোর্ট বাটনটি খুঁজে বের করা
        val btnOpenSupport = findViewById<View>(R.id.btnOpenSupport)

        // ফোন ল্যান্ডস্কেপ (আড়াআড়ি/ফুল স্ক্রিন) মোডে গেলে বাটন হাইড হয়ে যাবে
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            btnOpenSupport?.visibility = View.GONE
        }
        // ফোন আবার পোর্ট্রেট (সোজা) মোডে আসলে বাটন দেখা যাবে
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            btnOpenSupport?.visibility = View.VISIBLE
        }
    }

    private fun fetchChannelsFromFirebase() {
        db.collection("channels").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Toast.makeText(this, "Error loading channels", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val newChannels = snapshot.documents.mapNotNull { it.toObject(Channel::class.java) }
                
                // মাস্টার লিস্ট আপডেট করা হচ্ছে সার্চের জন্য
                channelList.clear()
                channelList.addAll(newChannels)
                
                // এডাপ্টার আপডেট করা হচ্ছে (DiffUtil ব্যবহার করবে)
                channelAdapter.updateList(newChannels)

                if (newChannels.isNotEmpty() && player.mediaItemCount == 0) {
                    playVideo(newChannels[0].url)
                }
            }
        }
    }

    private fun playVideo(url: String) {
        val actualStreamUrl = if (url.contains("?url=")) url.substringAfter("?url=") else url
        val mediaItem = MediaItem.Builder()
            .setUri(actualStreamUrl)
            .setMimeType(MimeTypes.APPLICATION_M3U8)
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

    // ফুলস্ক্রিন চালু করার ফাংশন
    private fun enterFullScreen() {
        isPlayerFullScreen = true
        searchChannel.visibility = View.GONE
        recyclerView.visibility = View.GONE

        val params = playerView.layoutParams as ConstraintLayout.LayoutParams
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
        params.dimensionRatio = null
        playerView.layoutParams = params

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, playerView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    // ফুলস্ক্রিন থেকে বের হওয়ার ফাংশন
    private fun exitFullScreen() {
        isPlayerFullScreen = false
        searchChannel.visibility = View.VISIBLE
        recyclerView.visibility = View.VISIBLE

        val params = playerView.layoutParams as ConstraintLayout.LayoutParams
        params.width = 0
        params.height = 0
        params.dimensionRatio = "16:9"
        playerView.layoutParams = params

        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, playerView).show(WindowInsetsCompat.Type.systemBars())
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}