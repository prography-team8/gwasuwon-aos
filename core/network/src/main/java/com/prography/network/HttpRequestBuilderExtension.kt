package com.prography.network

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Created by MyeongKi.
 */
inline fun <reified T> HttpRequestBuilder.setJsonBody(body: T) {
    contentType(ContentType.Application.Json)
    setBody(body)
}
