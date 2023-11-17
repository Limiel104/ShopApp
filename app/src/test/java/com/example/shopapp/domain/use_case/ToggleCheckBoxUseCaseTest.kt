package com.example.shopapp.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ToggleCheckBoxUseCaseTest {

    private lateinit var toggleCheckBoxUseCase: ToggleCheckBoxUseCase
    private lateinit var categoryFilterMap: Map<String,Boolean>

    @Before
    fun setUp() {
        toggleCheckBoxUseCase = ToggleCheckBoxUseCase()

        categoryFilterMap = mapOf(
            Pair("Men's clothing",true),
            Pair("Women's clothing",true),
            Pair("Jewelery",true),
            Pair("Electronics",true)
        )
    }

    @Test
    fun `checkbox is toggled - from true to false`() {
        val map = toggleCheckBoxUseCase(categoryFilterMap,"Women's clothing")

        assertThat(map).isEqualTo(
            mapOf(
                Pair("Men's clothing",true),
                Pair("Women's clothing",false),
                Pair("Jewelery",true),
                Pair("Electronics",true)
            )
        )
    }

    @Test
    fun `checkbox is toggled - from false to true`() {
        val map = toggleCheckBoxUseCase(categoryFilterMap,"Electronics")

        assertThat(map).isEqualTo(
            mapOf(
                Pair("Men's clothing",true),
                Pair("Women's clothing",true),
                Pair("Jewelery",true),
                Pair("Electronics",false)
            )
        )
    }
}