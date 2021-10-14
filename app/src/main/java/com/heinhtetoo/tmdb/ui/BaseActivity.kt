package com.heinhtetoo.tmdb.ui

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    abstract fun onLoading()
    abstract fun onLoaded()
    abstract fun onError()
}