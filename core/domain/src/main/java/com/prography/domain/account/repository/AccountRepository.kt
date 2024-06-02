package com.prography.domain.account.repository

import com.prography.domain.account.model.SocialLoginType
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
interface AccountRepository {
    fun signIn(type: SocialLoginType, accessKey: String): Flow<Unit>
}