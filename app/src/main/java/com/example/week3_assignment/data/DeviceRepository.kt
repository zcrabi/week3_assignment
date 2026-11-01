package com.example.week3_assignment.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class DeviceRepository {

    private val _devices = MutableStateFlow(sampleDevices)
    val devices: StateFlow<List<SmartDevice>> = _devices.asStateFlow()

    // Gerçek bir uygulamada burada cihaza istek atılırdı, bu yüzden main thread dışında çalışıyor
    suspend fun toggleDevice(deviceId: Int) {
        withContext(Dispatchers.IO) {
            _devices.update { list ->
                list.map { device ->
                    if (device.id == deviceId && device.isOnline) {
                        device.copy(isActive = !device.isActive)
                    } else {
                        device
                    }
                }
            }
        }
    }
}

private val sampleDevices = listOf(
    SmartDevice(1, "Köşe Lambası", DeviceCategory.LIGHT, "Salon", isOnline = true, isActive = true, powerWatt = 15.0),
    SmartDevice(2, "Tavan Avizesi", DeviceCategory.LIGHT, "Salon", isOnline = true, isActive = false, powerWatt = 60.0),
    SmartDevice(3, "Bahçe Kamerası", DeviceCategory.CAMERA, "Bahçe", isOnline = true, isActive = true, powerWatt = 8.5),
    SmartDevice(4, "Giriş Kamerası", DeviceCategory.CAMERA, "Antre", isOnline = true, isActive = true, powerWatt = 8.5),
    SmartDevice(5, "Merkezi Termostat", DeviceCategory.THERMOSTAT, "Salon", isOnline = true, isActive = true, powerWatt = 12.0),
    SmartDevice(6, "Yatak Odası Kliması", DeviceCategory.THERMOSTAT, "Yatak Odası", isOnline = true, isActive = false, powerWatt = 1200.0),
    SmartDevice(7, "Mutfak Spotları", DeviceCategory.LIGHT, "Mutfak", isOnline = true, isActive = false, powerWatt = 45.0),
    SmartDevice(8, "Roborock S7", DeviceCategory.VACUUM, "Koridor", isOnline = true, isActive = true, powerWatt = 50.0),
    SmartDevice(9, "Balkon Aydınlatması", DeviceCategory.LIGHT, "Balkon", isOnline = false, isActive = false, powerWatt = 25.0),
    SmartDevice(10, "Çalışma Odası Lambası", DeviceCategory.LIGHT, "Ofis", isOnline = true, isActive = true, powerWatt = 12.0)
)
