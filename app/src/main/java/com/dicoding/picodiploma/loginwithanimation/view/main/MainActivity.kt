package com.dicoding.picodiploma.loginwithanimation.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.Result
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.view.MapsActivity
import com.dicoding.picodiploma.loginwithanimation.view.StoriesAdapter
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.detail.AddStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private val storiesAdapter = StoriesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        setupView()
        setupAction()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                true
            }
            R.id.map -> {
                navigateMapsActivity()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateMapsActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)

    }

    private fun setupView() {
        binding.rvStory.apply {
            adapter = storiesAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.getPagedStories().observe(this@MainActivity) { pagingData ->
                storiesAdapter.submitData(lifecycle, pagingData)
            }
        }

        storiesAdapter.addLoadStateListener { loadState ->
            binding.linearProgressBar.visibility = if (loadState.source.refresh is LoadState.Loading) {
                View.VISIBLE
            } else {
                View.GONE
            }

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error

            errorState?.let {
                Toast.makeText(
                    this,
                    "Error: ${it.error.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun setupAction() {
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

}