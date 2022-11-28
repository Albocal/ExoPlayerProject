package com.albocal.exoplayerapp.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.albocal.exoplayerapp.databinding.ActivityMainBinding
import com.albocal.exoplayerapp.base.BaseActivity
import com.albocal.exoplayerapp.ext.getViewModel

import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initUI()
        initListeners()
    }

    private fun initViewModel(){
        mainViewModel = getViewModel(viewModelFactory)
    }

    private fun initUI(){

    }

    private fun initListeners(){

    }

}