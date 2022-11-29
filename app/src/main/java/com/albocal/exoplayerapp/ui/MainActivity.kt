package com.albocal.exoplayerapp.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.albocal.exoplayerapp.base.BaseActivity
import com.albocal.exoplayerapp.databinding.ActivityMainBinding
import com.albocal.exoplayerapp.ext.getViewModel
import com.albocal.ownexoplayeranalytics.AnalyticsApplication
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import javax.inject.Inject


class MainActivity : BaseActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var analytics : AnalyticsApplication

    private lateinit var mainViewModel: MainViewModel

    private lateinit var player : ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayer()
        initViewModel()
        initUI()
        initListeners()
        initObservers()

    }

    private fun initViewModel(){
        mainViewModel = getViewModel(viewModelFactory)
    }

    private fun initUI(){
        binding.playerView.player = player
    }

    private fun initPlayer(){
        player = ExoPlayer.Builder(this).build()

        val mediaItem: MediaItem =
            MediaItem.fromUri("http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8")

        player.addMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
        player.addAnalyticsListener(analytics.analyticsListener)
        player.addListener(analytics.playerListener)

    }
    private fun initListeners(){

    }

    private fun initObservers(){
        lifecycleScope.launchWhenStarted {
            mainViewModel.pauseTextFlow.collect {
                binding.activityMainPause.text = it
            }
        }
        lifecycleScope.launchWhenStarted {
            mainViewModel.resumeTextFlow.collect {
                binding.activityMainResume.text = it
            }
        }
        lifecycleScope.launchWhenStarted {
            mainViewModel.elapsedTextFlow.collect {
                binding.activityMainElapsedTime.text = it
            }
        }
    }
}