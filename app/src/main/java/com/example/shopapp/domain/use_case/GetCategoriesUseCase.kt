package com.example.shopapp.domain.use_case

import com.example.shopapp.util.Category
import com.example.shopapp.util.getCategory

class GetCategoriesUseCase() {
    operator fun invoke(): List<Category> {
        return getCategory()
    }
}