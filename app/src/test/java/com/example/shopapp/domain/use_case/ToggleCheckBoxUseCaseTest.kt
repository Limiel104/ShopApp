package com.example.shopapp.domain.use_case

import com.example.shopapp.util.Category
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
            Pair(Category.Men.title,true),
            Pair(Category.Women.title,true),
            Pair(Category.Jewelery.title,true),
            Pair(Category.Electronics.title,true)
        )
    }

    @Test
    fun `checkbox is toggled - from true to false`() {
        val map = toggleCheckBoxUseCase(categoryFilterMap,"Women's clothing")

        assertThat(map).isEqualTo(
            mapOf(
                Pair(Category.Men.title,true),
                Pair(Category.Women.title,false),
                Pair(Category.Jewelery.title,true),
                Pair(Category.Electronics.title,true)
            )
        )
    }

    @Test
    fun `checkbox is toggled - from false to true`() {
        val map = toggleCheckBoxUseCase(categoryFilterMap,"Electronics")

        assertThat(map).isEqualTo(
            mapOf(
                Pair(Category.Men.title,true),
                Pair(Category.Women.title,true),
                Pair(Category.Jewelery.title,true),
                Pair(Category.Electronics.title,false)
            )
        )
    }
}