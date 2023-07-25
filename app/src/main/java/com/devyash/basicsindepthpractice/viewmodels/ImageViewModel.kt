package com.devyash.basicsindepthpractice.viewmodels

import androidx.lifecycle.ViewModel
import com.devyash.basicsindepthpractice.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ImageViewModel:ViewModel() {

    private var _images:MutableStateFlow<List<Image>> = MutableStateFlow(emptyList<Image>())
    private val images:StateFlow<List<Image>> = _images

    fun updateImages(images:List<Image>){
        this._images.value = images
    }
}