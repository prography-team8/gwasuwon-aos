package com.prography.domain.preference

import android.app.Application
import com.prography.domain.account.model.AccountRole
import com.prography.domain.account.model.AccountStatus

/**
 * Created by MyeongKi.
 */
class AccountPreferenceImpl(private val context: Application) : AccountPreference {
    override fun getAccountStatus(): AccountStatus {
        return try {
            AccountStatus.valueOf(context.getString(KEY_ACCOUNT_STATUS) ?: "")
        } catch (e: Exception) {
            AccountStatus.NONE
        }
    }

    override fun setAccountStatus(accountStatus: AccountStatus) {
        context.putString(KEY_ACCOUNT_STATUS, accountStatus.name)

    }

    override fun getAccountRole(): AccountRole {
        return try {
            AccountRole.valueOf(context.getString(KEY_ACCOUNT_ROLE) ?: "")
        } catch (e: Exception) {
            AccountRole.USER
        }
    }

    override fun setAccountRole(role: AccountRole) {
        context.putString(KEY_ACCOUNT_ROLE, role.name)
    }

    override fun setInvitedLessonId(lessonId: Long) {
        context.putLong(KEY_INVITED_LESSON_ID, lessonId)
    }

    override fun getInvitedLessonId(): Long? {
        val lessonId = context.getLong(KEY_INVITED_LESSON_ID, INVALID_LESSON_ID)
        return if (lessonId == INVALID_LESSON_ID) {
            null
        } else {
            lessonId
        }
    }

    companion object {
        private const val KEY_ACCOUNT_STATUS = "accountStatus"
        private const val KEY_ACCOUNT_ROLE = "accountRole"
        private const val KEY_INVITED_LESSON_ID = "invitedLessonId"
        private const val INVALID_LESSON_ID = Long.MIN_VALUE
    }
}