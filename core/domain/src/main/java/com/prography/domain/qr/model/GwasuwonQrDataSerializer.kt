package com.prography.domain.qr.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Created by MyeongKi.
 */
@Serializable
enum class GwasuwonQrType {
    JOIN_CLASS,
    ATTENDANCE_CLASS,
}

interface GwasuwonQrData {
    val type: GwasuwonQrType
}

@Serializable
data class JoinClassData(
    override val type: GwasuwonQrType,
    val classId: Long
) : GwasuwonQrData

@Serializable
data class AttendanceClassData(
    override val type: GwasuwonQrType,
    val classId: Long
) : GwasuwonQrData


object GwasuwonQrDataSerializer : JsonContentPolymorphicSerializer<GwasuwonQrData>(GwasuwonQrData::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out GwasuwonQrData> {
        return when (val type = element.jsonObject["type"]?.jsonPrimitive?.content) {
            GwasuwonQrType.JOIN_CLASS.name -> JoinClassData.serializer()
            GwasuwonQrType.ATTENDANCE_CLASS.name -> AttendanceClassData.serializer()
            else -> throw IllegalArgumentException("Unknown type: $type")
        }
    }
}
