package com.homeandroid.samplehilt.ui.save

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.homeandroid.samplehilt.R
import com.homeandroid.samplehilt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private val userViewmodel: UserViewmodel by viewModels()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        binding.userViewmodel = userViewmodel
        userViewmodel.status.observe(this, Observer {
            if (it == true) {
                Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "not successful", Toast.LENGTH_SHORT).show()
            }
        })
        binding.savebtn.setOnClickListener {
            userViewmodel.save()
        }
    }
}