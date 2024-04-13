package ru.axas.auction.models.realty

import com.google.gson.annotations.SerializedName
import ru.axas.api.models.GettingUser

data class GettingRealty(
    val id: Int,
    @SerializedName("residential_type")
    val residentialType: Int?,
    @SerializedName("commercial_type")
    val commercialType: Int?,
    val city: String?,
    val address: String?,
    val lat: Double?,
    val lon: Double?,
    val stage: Int?,
    @SerializedName("max_stage")
    val maxStage: Int?,
    @SerializedName("room_num")
    val roomNum: Int?,
    val year: Int?,
    val height: Int?,
    @SerializedName("room_count")
    val roomCount: Int?,
    @SerializedName("bathroom_type")
    val bathroomType: Int?,
    val description: String?,
    @SerializedName("balcony_type")
    val balconyType: Int?,
    @SerializedName("building_type")
    val buildingType: Int?,
    val price: Long?,
    @SerializedName("living_area")
    val livingArea: Int?,
    @SerializedName("kitchen_area")
    val kitchenArea: Int?,
    @SerializedName("total_area")
    val totalArea: Int?,
    val docs: List<GettingRealtyDocsOrImage>?,
    val images: List<GettingRealtyDocsOrImage>?,
    val user: GettingUser?

)
