package com.katelee.podcast

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.katelee.podcast.databinding.ActivityPlayerBinding
import com.katelee.podcast.model.PlayerViewModel

/**
 * Created by Kate on 2020-04-16.
 */

class PlayerActivity : AppCompatActivity() {
    var mediaPlayer : MediaPlayer? = null
    lateinit var binding: ActivityPlayerBinding
    val handler = Handler()
    var runnable: Runnable? = null

    companion object {
        private const val EXTRA_MEDIA_URL = "EXTRA_MEDIA_URL"
        private const val EXTRA_ARTWORK_URL = "EXTRA_ARTWORK_URL"
        private const val EXTRA_NAME = "EXTRA_NAME"

        fun getStartIntent(context: Context, mediaUrl: String, artworkUrl: String?, name: String): Intent {
            return Intent(context, PlayerActivity::class.java)
                .putExtra(EXTRA_MEDIA_URL, mediaUrl)
                .putExtra(EXTRA_ARTWORK_URL, artworkUrl)
                .putExtra(EXTRA_NAME, name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras == null) {
            finish()
            return
        }
        val mediaUrl = extras.getString(EXTRA_MEDIA_URL)
        if (mediaUrl == null || mediaUrl.isEmpty()) {
            finish()
            return
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        val viewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.artworkUrl = extras.getString(EXTRA_ARTWORK_URL, "")
        viewModel.mediaName = extras.getString(EXTRA_NAME, "")
        binding.start.isEnabled = false
        binding.previous.isEnabled = false
        binding.next.isEnabled = false

        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(mediaUrl)
            prepareAsync() // might take long! (for buffering, etc)
        }
        mediaPlayer!!.setOnPreparedListener {
            binding.progressBar.visibility = View.GONE
            binding.timeEnd.text = formatTime(mediaPlayer!!.duration)
            binding.timeNow.text = formatTime(0)
            binding.progressSeekBar.max = mediaPlayer!!.duration
            binding.start.isEnabled = true
            binding.start.performClick()
            binding.previous.isEnabled = true
            binding.next.isEnabled = true
        }
        mediaPlayer!!.setOnCompletionListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.message_media_complete)
                .setPositiveButton(R.string.replay) {  _, _ ->
                    mediaPlayer!!.seekTo(0)
                    mediaPlayer!!.start()
                }
                .setNegativeButton(R.string.finish) { _, _ -> finish() }
                .show()
        }
        binding.progressSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer!!.seekTo(progress)
                    binding.timeNow.text = formatTime(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.start.setOnClickListener {
            if (!mediaPlayer!!.isPlaying) {
                mediaPlayer!!.start()
                binding.start.isSelected = true
                asyncProgressBar()
            } else {
                mediaPlayer!!.pause()
                binding.start.isSelected = false
                handler.removeCallbacks(runnable)
                runnable = null
            }
        }
        binding.previous.setOnClickListener {
            mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition - 30 * 1000)
        }
        binding.next.setOnClickListener {
            mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition + 30 * 1000)
        }
    }

    private fun asyncProgressBar() {
        runnable = Runnable {
            binding.progressSeekBar.progress = mediaPlayer!!.getCurrentPosition()
            binding.timeNow.text = formatTime(mediaPlayer!!.getCurrentPosition())
            handler.postDelayed(runnable, 500)
        }
        runnable!!.run()
    }

    private fun formatTime(mills: Int): String {
        return "${String.format("%02d", mills/1000/60)}:${kotlin.String.format("%02d", mills/1000%60)}"
    }

    override fun onStop() {
        handler.removeCallbacks(runnable)
        runnable = null
        super.onStop()
    }

    override fun onDestroy() {
        mediaPlayer!!.release()
        mediaPlayer = null
        super.onDestroy()
    }
}