package com.albocal.exoplayerapp.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.albocal.exoplayerapp.base.BaseActivity
import com.albocal.exoplayerapp.databinding.ActivityMainBinding
import com.albocal.exoplayerapp.ext.getViewModel
import com.albocal.ownexoplayeranalytics.AnalyticsApplication
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import javax.inject.Inject


class MainActivity : BaseActivity() {

    companion object {
        const val TAG = "MainActivity"
    }
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var analitics : AnalyticsApplication

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initUI()
        initListeners()
        initObservers()
    }

    private fun initViewModel(){
        mainViewModel = getViewModel(viewModelFactory)
    }

    private fun initUI(){
        val player = ExoPlayer.Builder(this).build()
        player.repeatMode = Player.REPEAT_MODE_ALL;

        binding.playerView.player = player

        val mediaItem: MediaItem =
            MediaItem.fromUri("http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8")
        player.addMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
        player.addAnalyticsListener(analitics.listener)
    }

    private fun initListeners(){

    }


    private fun initObservers(){
        lifecycleScope.launchWhenStarted {
            mainViewModel.resumeState.collect {
                Log.d(TAG,"New collect resume: $it")
            }
        }
        lifecycleScope.launchWhenStarted {
            mainViewModel.pauseState.collect {
                Log.d(TAG,"New collect pause: $it")
            }
        }
    }
}