package com.example.weatherapp.ui.theme.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.ui.theme.data.WeatherRes
import com.example.weatherapp.ui.theme.network.WeatherAPI
import com.example.weatherapp.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val weatherAPI: WeatherAPI
) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherRes?>(null)
    val weather: StateFlow<WeatherRes?> = _weather

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchWeather(cityName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = weatherAPI.getWeather(cityName = cityName, apiKey = BuildConfig.WEATHER_API_KEY)
                _weather.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
