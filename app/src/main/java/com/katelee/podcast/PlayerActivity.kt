package com.katelee.podcast

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.katelee.podcast.databinding.ActivityPlayerBinding
import com.katelee.podcast.model.PlayerViewModel

/**
 * Created by Kate on 2020-04-16.
 */

class PlayerActivity : AppCompatActivity() {
    var mediaPlayer : MediaPlayer? = null
    lateinit var binding: ActivityPlayerBinding
    lateinit var viewModel: PlayerViewModel

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
        viewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.artworkUrl = extras.getString(EXTRA_ARTWORK_URL, "")
        viewModel.mediaName = extras.getString(EXTRA_NAME, "")
        viewModel.mediaUrl = mediaUrl

        viewModel.timeUpdate.observe(this, Observer<Boolean> {
            viewModel.timeNow.postValue(mediaPlayer!!.currentPosition)
            viewModel.timerStart()
        })
        binding.progressSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) { mediaPlayer!!.seekTo(progress) }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.start.setOnClickListener {
            if (!mediaPlayer!!.isPlaying) {
                binding.start.isSelected = true
                viewModel.timerStart()
                mediaPlayer!!.seekTo(viewModel.timeNow.value ?: 0)
                mediaPlayer!!.start()
            } else {
                binding.start.isSelected = false
                viewModel.timerEnd()
                mediaPlayer!!.pause()
            }
        }
        binding.previous.setOnClickListener {
            mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition - 30 * 1000)
        }
        binding.next.setOnClickListener {
            mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition + 30 * 1000)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.isPrepared.value = false
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(viewModel.mediaUrl)
            prepareAsync() // might take long! (for buffering, etc)
        }
        mediaPlayer!!.setOnPreparedListener {
            viewModel.isPrepared.value = true
            viewModel.duration.value = mediaPlayer!!.duration

            binding.start.performClick()
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
    }

    override fun onStop() {
        super.onStop()
        viewModel.timerEnd()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

@BindingAdapter("enable")
fun setViewEnabled(view: View, enable: Boolean) {
    view.isEnabled = enable
}