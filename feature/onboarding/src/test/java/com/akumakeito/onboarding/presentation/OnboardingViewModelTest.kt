package com.akumakeito.onboarding.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.akumakeito.commonmodels.domain.FiatCurrency
import com.akumakeito.commonmodels.lari
import com.akumakeito.commonmodels.rub
import com.akumakeito.commonmodels.usd
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.convert.presentation.search.SearchingInteractor
import com.akumakeito.core.appsettings.AppSettingsRepository
import com.akumakeito.core.testing.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class OnboardingViewModelTest {

    @get:Rule
    val mockKRule = MockKRule(this)

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var viewModel: OnboardingViewModel

    @MockK
    lateinit var repository: CurrencyRepository

    @MockK
    lateinit var appSettingsRepository: AppSettingsRepository

    @MockK
    lateinit var searchingInteractor: SearchingInteractor

    @Before
    fun setUp() {
        viewModel = OnboardingViewModel(
            appSettingsRepository = appSettingsRepository,
            searchingInteractor = searchingInteractor,
            repository = repository,
        )
    }

}