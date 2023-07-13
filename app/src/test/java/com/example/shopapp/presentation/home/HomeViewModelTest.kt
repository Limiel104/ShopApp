package com.example.shopapp.presentation.home

import com.example.shopapp.domain.model.Offer
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var offerList: List<Offer>

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel()

        offerList = listOf(
            Offer(
                categoryId = "women's clothing",
                discountPercent = 10,
                description = "All clothes for women now 10% cheaper"
            ),
            Offer(
                categoryId = "men's clothing",
                discountPercent = 15,
                description = "All clothes for men now 15% cheaper"
            ),
            Offer(
                categoryId = "women's clothing",
                discountPercent = 20,
                description = "All shirts for women 20% cheaper with code SHIRT20"
            ),
            Offer(
                categoryId = "jewelery",
                discountPercent = 50,
                description = "Buy two pieces of jewelery for the price of one"
            ),
            Offer(
                discountPercent = 13,
                description = "13% off for purchase above 200\$"
            ),
            Offer(
                discountPercent = 10,
                description = "25% off for purchase above 500\$"
            )
        )
    }

    private fun getCurrentHomeState(): HomeState {
        return homeViewModel.homeState.value
    }

    @Test
    fun `offerList is set correctly on init`() {
        val offerList = getCurrentHomeState().offerList
        assertThat(offerList).isEqualTo(offerList)
    }
}