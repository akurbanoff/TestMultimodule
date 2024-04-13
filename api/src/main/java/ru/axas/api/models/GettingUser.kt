package ru.axas.api.models

import com.google.gson.annotations.SerializedName

data class GettingUser(
    val id: Int?,
    val email: String?,
    val tel: String?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("is_superuser")
    val isSuperuser: Boolean,
    @SerializedName("first_name")
    val firstName: String?,
    val patronymic: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("birthtime")
    val birthTime: Int?,
    val gender: Int?,
    val location: String?,
    val lat: Double?,
    val lon: Double?,
    @SerializedName("last_activity")
    val lastActivity: Int?,
    val avatar: String?,
    @SerializedName("is_verified")
    val isVerified: Boolean,
    @SerializedName("notify_about_info")
    val notifyAboutInfo: Boolean
)
