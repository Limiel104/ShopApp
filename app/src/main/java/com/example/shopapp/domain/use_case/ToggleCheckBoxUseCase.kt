package com.example.shopapp.domain.use_case

class ToggleCheckBoxUseCase {
    operator fun invoke(map: Map<String,Boolean>, selectedCheckBox: String): Map<String,Boolean> {
        val returnMap: MutableMap<String,Boolean> = mutableMapOf()

        map.forEach {
            if(it.key == selectedCheckBox) {
                returnMap[it.key] = !it.value
            }
            else {
                returnMap[it.key] = it.value
            }
        }
        return returnMap
    }
}