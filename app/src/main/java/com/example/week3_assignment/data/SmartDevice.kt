package com.example.week3_assignment.data

enum class DeviceCategory(val label: String, val icon: String) {
    LIGHT("Aydınlatma", "💡"),
    CAMERA("Kamera", "📷"),
    THERMOSTAT("Termostat", "🌡️"),
    VACUUM("Süpürge", "🧹")
}

data class SmartDevice(
    val id: Int,
    val name: String,
    val category: DeviceCategory,
    val room: String,
    val isOnline: Boolean,
    val isActive: Boolean,
    val powerWatt: Double
)
