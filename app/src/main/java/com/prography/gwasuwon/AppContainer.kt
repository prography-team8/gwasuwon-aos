package com.prography.gwasuwon

import NavigationEvent
import com.prography.account.AccountInfoManagerImpl
import com.prography.domain.account.AccountInfoManager
import com.prography.domain.account.SocialLoginEvent
import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.model.AccountStatus
import com.prography.domain.account.repository.AccountRepository
import com.prography.domain.account.request.SignInRequestOption
import com.prography.domain.account.request.SignUpRequestOption
import com.prography.domain.account.usecase.SignInUseCase
import com.prography.domain.account.usecase.SignUpUseCase
import com.prography.domain.configuration.ConfigurationEvent
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.respository.LessonRepositoryImpl
import com.prography.domain.lesson.usecase.CreateLessonUseCase
import com.prography.domain.lesson.usecase.LoadLessonContractUrlUseCase
import com.prography.domain.lesson.usecase.LoadLessonsUseCase
import com.prography.domain.preference.AccountPreference
import com.prography.domain.preference.ThemePreferenceImpl
import com.prography.lesson.fake.FakeLessonsDataSource
import com.prography.network.HttpClientFactory
import com.prography.utils.clipboar.ClipboardHelperImpl
import com.prography.utils.security.GwasuwonAccessTokenHelper
import com.prography.utils.security.GwasuwonRefreshTokenHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
object AppContainer {
    val commonLessonEvent: MutableSharedFlow<CommonLessonEvent> = MutableSharedFlow()

    val configurationEvent: MutableSharedFlow<ConfigurationEvent> = MutableSharedFlow()
    val navigateEventFlow: MutableSharedFlow<NavigationEvent> = MutableSharedFlow()
    val socialLoginEventFlow: MutableSharedFlow<SocialLoginEvent> = MutableSharedFlow()

    val clipboardHelper by lazy {
        ClipboardHelperImpl(GwasuwonApplication.currentApplication)
    }

    private val gwasuwonAccessTokenHelper by lazy {
        GwasuwonAccessTokenHelper(GwasuwonApplication.currentApplication)
    }
    private val gwasuwonRefreshTokenHelper by lazy {
        GwasuwonRefreshTokenHelper(GwasuwonApplication.currentApplication)
    }

    //    private val accountPreference by lazy {
//        AccountPreferenceImpl(GwasuwonApplication.currentApplication)
//    }
    private val accountPreference by lazy {
        object : AccountPreference {
            override fun getAccountStatus(): AccountStatus {
                return AccountStatus.ACTIVE
            }

            override fun setAccountStatus(accountStatus: AccountStatus) {
            }

        }
    }
    val accountInfoManager: AccountInfoManager by lazy {
        AccountInfoManagerImpl.apply {
            init(
                accessTokenHelper = gwasuwonAccessTokenHelper,
                refreshTokenHelper = gwasuwonRefreshTokenHelper,
                accountPreference = accountPreference
            )
        }
    }
    private val gwasuwonHttpClient by lazy {
        HttpClientFactory.createGwasuwonHttpClient(accountInfoManager)
    }

    //    private val accountRepository by lazy {
//        AccountRepositoryImpl(
//            remoteDataSource = AccountRemoteDataSource(
//                httpClient = AccountHttpClient(
//                    httpClient = gwasuwonHttpClient
//                )
//            )
//        )
//    }
    //FIXME
    private val accountRepository by lazy {
        object : AccountRepository {
            override fun signIn(requestOption: SignInRequestOption): Flow<AccountInfo> {
                return flow {
                    emit(
                        AccountInfo(
                            status = AccountStatus.PENDING,
                            refreshToken = "",
                            accessToken = "",
                        )
                    )
                }
            }

            override fun signUp(requestOption: SignUpRequestOption): Flow<AccountInfo> {
                return flow {
                    emit(
                        AccountInfo(
                            status = AccountStatus.ACTIVE,
                            refreshToken = "",
                            accessToken = "",
                        )
                    )
                }
            }

        }
    }

    private val lessonRepository = LessonRepositoryImpl(
        FakeLessonsDataSource()
    )

    val themePreference by lazy {
        ThemePreferenceImpl(GwasuwonApplication.currentApplication)
    }
    val signInUseCase by lazy {
        SignInUseCase(
            accountRepository,
            accountInfoManager
        )
    }
    val signUpUseCase by lazy {
        SignUpUseCase(
            accountRepository,
            accountInfoManager
        )
    }
    val loadLessonsUseCase by lazy {
        LoadLessonsUseCase(
            lessonRepository
        )
    }
    val createLessonUseCase by lazy {
        CreateLessonUseCase(
            lessonRepository
        )
    }

    val loadLessonContractUrlUseCase by lazy {
        LoadLessonContractUrlUseCase(
            lessonRepository
        )
    }
}