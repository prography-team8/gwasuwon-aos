package com.prography.network.lesson

import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import com.prography.network.GWASUWON_HOST
import com.prography.network.lesson.body.AttendanceLessonRequestBody
import com.prography.network.lesson.body.CreateLessonRequestBody
import com.prography.network.lesson.body.ForceAttendanceLessonRequestBody
import com.prography.network.lesson.body.JoinLessonRequestBody
import com.prography.network.lesson.response.CreateLessonResponse
import com.prography.network.lesson.response.JoinLessonResponse
import com.prography.network.lesson.response.LessonCardsResponse
import com.prography.network.lesson.response.LessonContractResponse
import com.prography.network.lesson.response.LessonDetailResponse
import com.prography.network.setJsonBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put

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

    suspend fun requestUpdateLesson(requestOption: UpdateLessonRequestOption): CreateLessonResponse {
        return httpClient().put("$GWASUWON_HOST/api/v1/classes/${requestOption.lessonId}") {
            setJsonBody(
                CreateLessonRequestBody(
                    studentName = requestOption.updateOption.studentName,
                    grade = requestOption.updateOption.grade,
                    subject = requestOption.updateOption.subject.name,
                    classDays = requestOption.updateOption.lessonDays.map { it.name },
                    sessionDuration = requestOption.updateOption.sessionDuration.name,
                    startDate = requestOption.updateOption.startDate,
                    numberOfSessions = requestOption.updateOption.numberOfSessions,
                    rescheduleCount = requestOption.updateOption.rescheduleCount,
                    memo = requestOption.updateOption.memo
                )
            )
        }.body()
    }

    suspend fun requestDeleteLesson(lessonId: Long): String {
        return httpClient().delete("$GWASUWON_HOST/api/v1/classes/${lessonId}").body()
    }

    suspend fun requestLessonDetail(lessonId: Long): LessonDetailResponse {
        return httpClient().get("$GWASUWON_HOST/api/v1/classes/$lessonId").body()
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


    suspend fun requestLessonContract(lessonId: Long): LessonContractResponse {
        return httpClient().get("$GWASUWON_HOST/api/v1/classes/$lessonId/contract").body()
    }

    suspend fun requestAttendanceLesson(lessonId: Long): String {
        return httpClient().post("$GWASUWON_HOST/api/v1/schedules/attendance") {
            setJsonBody(
                AttendanceLessonRequestBody(
                    classId = lessonId
                )
            )
        }.body()
    }

    suspend fun requestForceAttendanceLesson(scheduleId: Long): String {
        return httpClient().post("$GWASUWON_HOST/api/v1/schedules/attendance/force") {
            setJsonBody(
                ForceAttendanceLessonRequestBody(
                    scheduleId = scheduleId
                )
            )
        }.body()
    }
}