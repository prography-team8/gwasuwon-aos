package com.prography.utils.network

/**
 * Created by MyeongKi.
 */
interface NetworkHelper {
    fun isNetworkAvailable(): Boolean
    fun checkNetworkAvailable()
}
class NetworkUnavailableException(message: String) : Exception(message)
