package com.akumakeito.selectfavcurrency.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.akumakeito.convert.domain.CurrencyRepository
import com.akumakeito.convert.presentation.search.SearchingInteractor
import com.akumakeito.core.appsettings.AppSettingsRepository
import com.akumakeito.core.testing.MainCoroutineRule
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import org.junit.Before
import org.junit.Rule
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
    lateinit var viewModel: SelectFavCurrencyViewModel

    @MockK
    lateinit var repository: CurrencyRepository

    @MockK
    lateinit var appSettingsRepository: AppSettingsRepository

    @MockK
    lateinit var searchingInteractor: SearchingInteractor

    @Before
    fun setUp() {
        viewModel = SelectFavCurrencyViewModel(
            appSettingsRepository = appSettingsRepository,
            searchingInteractor = searchingInteractor,
            repository = repository,
        )
    }

}