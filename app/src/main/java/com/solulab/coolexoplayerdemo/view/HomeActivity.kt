package com.solulab.coolexoplayerdemo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.solulab.coolexoplayerdemo.R
import com.solulab.coolexoplayerdemo.databinding.ActivityMainBinding
import com.tofik.coolexoplayer.exoplayer.cool.widget.PressablePlayerSelector
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {
    private val viewModel by lazy { HomeViewModel() }
    private lateinit var homeBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) viewModel.init(applicationContext)
        homeBinding.lifecycleOwner = this
        homeBinding.model = viewModel
        val selector = PressablePlayerSelector(player_container)
        player_container!!.playerSelector = selector
        viewModel.setSelector(selector)
        viewModel.getHomeList()

    }
}
