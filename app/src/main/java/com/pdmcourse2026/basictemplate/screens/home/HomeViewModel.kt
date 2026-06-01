package com.pdmcourse2026.basictemplate.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdmcourse2026.basictemplate.data.api.OptionApiRepository
import com.pdmcourse2026.basictemplate.data.api.OptionRepository
import com.pdmcourse2026.basictemplate.models.Option
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
  private val optionRepository: OptionRepository = OptionApiRepository()

  private val _options = MutableStateFlow<List<Option>>(emptyList())
  val options = _options.asStateFlow()

  private val _loading = MutableStateFlow(false)
  val loading = _loading.asStateFlow()
  private val _error = MutableStateFlow<String?>(null)
  val error = _error.asStateFlow()

  private val _voteMessage = MutableStateFlow<String?>(null)
  val voteMessage = _voteMessage.asStateFlow()

  init {
    loadOptions()
  }

  fun postVote(optionId: Int) {
    _loading.value = true

    viewModelScope.launch {
      optionRepository.postVote(optionId)
        .onSuccess { _voteMessage.value = "Voto subido exitosamente" }
        .onFailure { _voteMessage.value = "Hubo un error al subir el voto" }
    }

    _loading.value = false
  }

  fun loadOptions() {
    _loading.value = true
    _error.value = null

    viewModelScope.launch {
      optionRepository.getOptions()
        .onSuccess { _options.value = it }
        .onFailure { _error.value = "Hubo un error al cargar las opciones" }
    }

    _loading.value = false
  }
}