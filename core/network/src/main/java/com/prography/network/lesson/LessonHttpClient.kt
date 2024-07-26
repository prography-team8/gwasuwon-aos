package com.prography.network.lesson

import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.network.GWASUWON_HOST
import com.prography.network.lesson.body.CreateLessonRequestBody
import com.prography.network.lesson.body.JoinLessonRequestBody
import com.prography.network.lesson.response.CreateLessonResponse
import com.prography.network.lesson.response.JoinLessonResponse
import com.prography.network.lesson.response.LessonContractResponse
import com.prography.network.lesson.response.LessonSchedulesResponse
import com.prography.network.lesson.response.LessonCardsResponse
import com.prography.network.setJsonBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post

/**
 * Created by MyeongKi.
 */
class LessonHttpClient(private val httpClient: () -> HttpClient) {
    suspend fun requestLessonCards(): LessonCardsResponse {
        return httpClient().get("$GWASUWON_HOST/api/v1/classes").body()
    }

    suspend fun requestCreateLesson(requestOption: CreateLessonRequestOption): CreateLessonResponse {
        return httpClient().post("$GWASUWON_HOST/api/v1/classes") {
            setJsonBody(
                CreateLessonRequestBody(
                    studentName = requestOption.studentName,
                    grade = requestOption.grade,
                    subject = requestOption.subject.name,
                    classDays = requestOption.lessonDays.map { it.name },
                    sessionDuration = requestOption.sessionDuration.name,
                    startDate = requestOption.startDate,
                    numberOfSessions = requestOption.numberOfSessions,
                    rescheduleCount = requestOption.rescheduleCount,
                    memo = requestOption.memo
                )
            )
        }.body()
    }

    suspend fun requestJoinLesson(lessonId: Long): JoinLessonResponse {
        return httpClient().post("$GWASUWON_HOST/api/v1/classes/join") {
            setJsonBody(
                JoinLessonRequestBody(
                    classId = lessonId
                )
            )
        }.body()
    }

    suspend fun requestLessonSchedules(lessonId: Long): LessonSchedulesResponse {
        return httpClient().get("$GWASUWON_HOST/api/v1/classes/$lessonId").body()
    }

    suspend fun requestLessonContract(lessonId: Long): LessonContractResponse {
        return httpClient().get("$GWASUWON_HOST/api/v1/classes/$lessonId/contract").body()
    }
}