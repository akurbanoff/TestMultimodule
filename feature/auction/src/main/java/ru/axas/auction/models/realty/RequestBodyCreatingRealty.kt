package ru.axas.auction.models.realty

import com.google.gson.annotations.SerializedName

data class RequestBodyCreatingRealty(
    @SerializedName("residential_type")
    val residentialType: Int? = null,
    @SerializedName("commercial_type")
    val commercialType: Int? = null,
    val city: String? = null,
    val address: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val stage: Int? = null,
    @SerializedName("max_stage")
    val maxStage: Int? = null,
    @SerializedName("room_num")
    val roomNum: Int? = null,
    val year: Int? = null,
    val height: Int? = null,
    @SerializedName("room_count")
    val roomCount: Int? = null,
    @SerializedName("bathroom_type")
    val bathroomType: Int? = null,
    val description: String? = null,
    @SerializedName("balcony_type")
    val balconyType: Int? = null,
    @SerializedName("building_type")
    val buildingType: Int? = null,
    val price: Long? = null,
    @SerializedName("living_area")
    val livingArea: Int? = null,
    @SerializedName("kitchen_area")
    val kitchenArea: Int? = null,
    @SerializedName("total_area")
    val totalArea: Int? = null,
    @SerializedName("doc_ids")
    val docIds: List<Int>? = null,
    @SerializedName("image_ids")
    val imageIds: List<Int>? = null
)
