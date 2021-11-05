package ru.skillbranch.sbdelivery.repository.mapper

import ru.skillbranch.sbdelivery.core.adapter.CategoryItemState
import ru.skillbranch.sbdelivery.repository.models.Category

class CategoriesMapper {

    fun mapDtoToState(dto: List<Category>, selectedCategoryId: String? = null):
        List<CategoryItemState> =
        dto.map { CategoryItemState(
            categoryId = it.categoryId,
            title = it.name,
            selected = selectedCategoryId == it.categoryId
        ) }

}