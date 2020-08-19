package com.altimetrik.itunessearch.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.altimetrik.itunessearch.apis.ApisRepository
import com.altimetrik.itunessearch.models.Result

class MainActivityViewModel: ViewModel() {

    fun getiTunes(term: String): LiveData<List<Result>>? {
        return ApisRepository.getiTunes(term)
    }
}