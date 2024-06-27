package com.prography.qr.domain.model

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
    PARTICIPATE_LESSON,
}
interface GwasuwonQrData
@Serializable
data class InviteStudentData(
    val lessonId: Long
) : GwasuwonQrData

@Serializable
data class ParticipateLessonData(
    val lessonId: Long
) : GwasuwonQrData


object GwasuwonQrDataSerializer : JsonContentPolymorphicSerializer<GwasuwonQrData>(GwasuwonQrData::class) {
    override fun selectDeserializer(element: JsonElement): KSerializer<out GwasuwonQrData> {
        return when (val type = element.jsonObject["type"]?.jsonPrimitive?.content) {
            GwasuwonQrType.INVITE_STUDENT.name -> InviteStudentData.serializer()
            GwasuwonQrType.PARTICIPATE_LESSON.name -> ParticipateLessonData.serializer()
            else -> throw IllegalArgumentException("Unknown type: $type")
        }
    }
}
