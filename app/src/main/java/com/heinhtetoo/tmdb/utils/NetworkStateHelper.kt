package com.heinhtetoo.tmdb.utils

import androidx.core.view.isVisible
import com.heinhtetoo.tmdb.databinding.LayoutNetworkStateBinding
import com.heinhtetoo.tmdb.utils.Resource.Status.*

object NetworkStateHelper {
    fun setState(
        binding: LayoutNetworkStateBinding,
        status: Resource.Status?,
        isNoInternet: Boolean = false
    ) {
        when {
            isNoInternet -> onNoConnection(binding)
            status == LOADING -> onLoading(binding)
            status == ERROR -> onError(binding)
            status == SUCCESS -> onLoaded(binding)
        }
    }

    fun onLoading(binding: LayoutNetworkStateBinding) {
        binding.clLoading.isVisible = true
        binding.clNoInternetConnection.isVisible = false
        binding.clError.isVisible = false
    }

    fun onLoaded(binding: LayoutNetworkStateBinding) {
        binding.clLoading.isVisible = false
        binding.clNoInternetConnection.isVisible = false
        binding.clError.isVisible = false
    }

    fun onError(binding: LayoutNetworkStateBinding) {
        binding.clLoading.isVisible = false
        binding.clNoInternetConnection.isVisible = false
        binding.clError.isVisible = true
    }

    fun onNoConnection(binding: LayoutNetworkStateBinding) {
        binding.clLoading.isVisible = false
        binding.clNoInternetConnection.isVisible = true
        binding.clError.isVisible = false
    }
}