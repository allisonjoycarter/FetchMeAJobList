package com.example.fetchmeajoblist.ui.hiringList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchmeajoblist.data.HiringListRepository
import com.example.fetchmeajoblist.models.HiringListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HiringListViewModel @Inject constructor(
    private val hiringListRepository: HiringListRepository
): ViewModel() {
    private var _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private var _errors = MutableSharedFlow<Throwable>()
    val errors: SharedFlow<Throwable> = _errors.asSharedFlow()

    private var _hiringItems = MutableStateFlow(emptyList<HiringListItem>())
    val hiringItems: StateFlow<List<HiringListItem>> = _hiringItems.asStateFlow()

    init {
        fetchHiringItems()
    }

    fun fetchHiringItems() = viewModelScope.launch {
        _loading.value = true
        runCatching {
            hiringListRepository.fetchHiringList()
        }.onSuccess { result ->
            _hiringItems.emit(result)
            _loading.value = false
        }.onFailure { error ->
            Timber.e(error, "Error when fetching hiring items")
            _errors.emit(error)
            _loading.value = false
        }
    }
}
