package com.example.weatherapp.ui.theme.screen.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor() : ViewModel() {

    private val _cities = MutableStateFlow<List<String>>(emptyList())
    val cities: StateFlow<List<String>> = _cities

    fun addCity(city: String) {
        viewModelScope.launch {
            _cities.value += city.uppercase()
        }
    }

    fun removeCity(city: String) {
        viewModelScope.launch {
            _cities.value -= city
        }
    }
}
