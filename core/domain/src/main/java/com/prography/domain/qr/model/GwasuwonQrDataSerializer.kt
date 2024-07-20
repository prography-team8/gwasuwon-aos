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
    INVITE_STUDENT,
    LESSON_CERTIFICATION,
}
interface GwasuwonQrData
@Serializable
data class InviteStudentData(
    val lessonId: Long
) : GwasuwonQrData

@Serializable
data class LessonCertificationData(
    val lessonId: Long
) : GwasuwonQrData


object GwasuwonQrDataSerializer : JsonContentPolymorphicSerializer<GwasuwonQrData>(GwasuwonQrData::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out GwasuwonQrData> {
        return when (val type = element.jsonObject["type"]?.jsonPrimitive?.content) {
            GwasuwonQrType.INVITE_STUDENT.name -> InviteStudentData.serializer()
            GwasuwonQrType.LESSON_CERTIFICATION.name -> LessonCertificationData.serializer()
            else -> throw IllegalArgumentException("Unknown type: $type")
        }
    }
}