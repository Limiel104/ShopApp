package com.example.shopapp.presentation.home

import com.example.shopapp.R
import com.example.shopapp.domain.model.Banner
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var bannerList: List<Banner>

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel()

        bannerList = listOf(
                Banner(
                    categoryId = "women's clothing",
                    resourceId = R.drawable.womans_clothing_banner
                ),
                Banner(
                    categoryId = "men's clothing",
                    resourceId = R.drawable.mens_clothing_banner
                ),
                Banner(
                    categoryId = "jewelery",
                    resourceId = R.drawable.jewelery_banner
                ),
                Banner(
                    categoryId = "electronics",
                    resourceId = R.drawable.electronics_banner
                )
        )
    }

    private fun getCurrentHomeState(): HomeState {
        return homeViewModel.homeState.value
    }

    @Test
    fun `offerList is set correctly on init`() {
        val result = getCurrentHomeState().bannerList
        assertThat(result).isEqualTo(bannerList)
    }
}