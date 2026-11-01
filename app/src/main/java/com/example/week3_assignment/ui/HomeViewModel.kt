package com.example.week3_assignment.ui

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week3_assignment.data.DeviceRepository
import com.example.week3_assignment.data.SmartDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

const val ALL_ROOMS = "Tümü"

@Immutable
data class RoomChip(
    val name: String,
    val isSelected: Boolean
)

@Immutable
data class HomeUiState(
    val devices: List<SmartDevice> = emptyList(),
    val roomChips: List<RoomChip> = emptyList(),
    val totalPowerText: String = "0.0 W",
    val workingDeviceText: String = "",
    val isListEmpty: Boolean = false
)

class HomeViewModel(
    private val repository: DeviceRepository = DeviceRepository()
) : ViewModel() {

    // Arama metni ayrı tutuluyor, yazarken TextField'da gecikme/imleç kayması olmasın diye
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedRoom = MutableStateFlow(ALL_ROOMS)

    val uiState: StateFlow<HomeUiState> = combine(
        repository.devices,
        _searchQuery,
        _selectedRoom
    ) { devices, query, selectedRoom ->
        buildUiState(devices, query, selectedRoom)
    }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            // Ekran ilk açıldığında boş görünmesin diye başlangıç durumu da hazır veriden hesaplanıyor
            initialValue = buildUiState(repository.devices.value, "", ALL_ROOMS)
        )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onRoomSelected(room: String) {
        _selectedRoom.value = room
    }

    fun onToggleDevice(deviceId: Int) {
        viewModelScope.launch {
            repository.toggleDevice(deviceId)
        }
    }

    private fun buildUiState(
        devices: List<SmartDevice>,
        query: String,
        selectedRoom: String
    ): HomeUiState {
        val filteredDevices = devices.filter { device ->
            val roomMatches = selectedRoom == ALL_ROOMS || device.room == selectedRoom
            val nameMatches = device.name.contains(query.trim(), ignoreCase = true)
            roomMatches && nameMatches
        }

        // Sayaç arama/filtreden etkilenmez, evdeki tüm aktif ve online cihazları toplar
        val workingDevices = devices.filter { it.isOnline && it.isActive }
        val totalPower = workingDevices.sumOf { it.powerWatt }

        val rooms = listOf(ALL_ROOMS) + devices.map { it.room }.distinct()

        return HomeUiState(
            devices = filteredDevices,
            roomChips = rooms.map { RoomChip(name = it, isSelected = it == selectedRoom) },
            totalPowerText = String.format(Locale.US, "%.1f W", totalPower),
            workingDeviceText = "${workingDevices.size} cihaz şu an çalışıyor",
            isListEmpty = filteredDevices.isEmpty()
        )
    }
}