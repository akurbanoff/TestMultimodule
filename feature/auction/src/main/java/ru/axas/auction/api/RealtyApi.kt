package ru.axas.auction.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import ru.axas.core.network.RudApi
import ru.axas.auction.models.realty.GettingRealty
import ru.axas.auction.models.realty.GettingRealtyDocsOrImage
import ru.axas.auction.models.realty.RequestBodyCreatingRealty

interface RealtyApi {

    @GET("/api/realty/")
    suspend fun getMyRealtyList(
        @Query("page") page: Int?
    ): Response<RudApi<List<GettingRealty>>>

    @POST("/api/realty/")
    suspend fun createRealty(
        @Body request: RequestBodyCreatingRealty
    ): Response<RudApi<GettingRealty>>

    @GET("/api/realty/{realty_id}/")
    suspend fun getRealtyById(
        @Path("realty_id") realtyId: Int
    ): Response<RudApi<GettingRealty>>

    @PUT("/api/realty/{realty_id}/")
    suspend fun updateRealty(
        @Path("realty_id") realtyId: Int,
        @Body request: RequestBodyCreatingRealty
    ): Response<RudApi<GettingRealty>>

    @DELETE("/api/realty/{realty_id}/")
    suspend fun deleteRealty(
        @Path("realty_id") realtyId: Int
    ): Response<RudApi<Any>>

    @Multipart
    @POST("/api/realty/images/")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<RudApi<GettingRealtyDocsOrImage>>

    @Multipart
    @POST("/api/realty/docs/")
    suspend fun uploadDocs(
        @Part doc: MultipartBody.Part
    ): Response<RudApi<GettingRealtyDocsOrImage>>
}