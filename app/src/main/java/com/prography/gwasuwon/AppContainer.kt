package com.prography.gwasuwon

import NavigationEvent
import com.prography.account.AccountInfoManagerImpl
import com.prography.database.dialog.DialogLocalDataSource
import com.prography.database.dialog.provideDialogDatabaseDriver
import com.prography.domain.account.AccountInfoManager
import com.prography.domain.account.SocialLoginEvent
import com.prography.domain.account.repository.AccountRepositoryImpl
import com.prography.domain.account.usecase.SignInUseCase
import com.prography.domain.account.usecase.SignUpUseCase
import com.prography.domain.configuration.ConfigurationEvent
import com.prography.domain.dialog.usecase.IsShowingNotifyLessonDeductedDialogUseCase
import com.prography.domain.dialog.usecase.UpdateShownNotifyLessonDeductedDialogUseCase
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.respository.LessonRepositoryImpl
import com.prography.domain.lesson.usecase.CheckLessonByAttendanceUseCase
import com.prography.domain.lesson.usecase.CreateLessonUseCase
import com.prography.domain.lesson.usecase.DeleteLessonUseCase
import com.prography.domain.lesson.usecase.LoadLessonContractUrlUseCase
import com.prography.domain.lesson.usecase.LoadLessonDatesUseCase
import com.prography.domain.lesson.usecase.LoadLessonUseCase
import com.prography.domain.lesson.usecase.LoadLessonsUseCase
import com.prography.domain.lesson.usecase.UpdateLessonUseCase
import com.prography.domain.preference.AccountPreferenceImpl
import com.prography.domain.preference.ThemePreferenceImpl
import com.prography.lesson.fake.FakeLessonsDataSource
import com.prography.network.HttpClientFactory
import com.prography.network.account.AccountHttpClient
import com.prography.network.account.AccountRemoteDataSource
import com.prography.utils.clipboar.ClipboardHelperImpl
import com.prography.utils.security.GwasuwonAccessTokenHelper
import com.prography.utils.security.GwasuwonRefreshTokenHelper
import kotlinx.coroutines.flow.MutableSharedFlow

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

    private val accountPreference by lazy {
        AccountPreferenceImpl(GwasuwonApplication.currentApplication)
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
        HttpClientFactory.createGwasuwonHttpClient(accountInfoManager) {
            navigateEventFlow.emit(NavigationEvent.NavigateSignInRoute)
        }
    }

    private val accountRepository by lazy {
        AccountRepositoryImpl(
            remoteDataSource = AccountRemoteDataSource(
                httpClient = AccountHttpClient(
                    httpClient = gwasuwonHttpClient
                )
            )
        )
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

    val loadLessonUseCase by lazy {
        LoadLessonUseCase(
            lessonRepository
        )
    }
    val loadLessonDatesUseCase by lazy {
        LoadLessonDatesUseCase()
    }
    val updateLessonUseCase by lazy {
        UpdateLessonUseCase(
            lessonRepository
        )
    }
    val deleteLessonUseCase by lazy {
        DeleteLessonUseCase(
            lessonRepository
        )
    }
    val checkLessonByAttendanceUseCase by lazy {
        CheckLessonByAttendanceUseCase(
            lessonRepository
        )
    }

    private val dialogDataSource by lazy {
        DialogLocalDataSource(
            provideDialogDatabaseDriver(GwasuwonApplication.currentApplication)
        )
    }

    val isShowingNotifyLessonDeductedDialogUseCase by lazy {
        IsShowingNotifyLessonDeductedDialogUseCase(
            dialogDataSource
        )
    }
    val updateShownNotifyLessonDeductedDialogUseCase by lazy {
        UpdateShownNotifyLessonDeductedDialogUseCase(
            dialogDataSource
        )
    }
}