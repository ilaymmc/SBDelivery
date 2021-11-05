package ru.skillbranch.sbdelivery.domain.filter

import io.reactivex.rxjava3.core.Single
import ru.skillbranch.sbdelivery.domain.entity.DishEntity
import ru.skillbranch.sbdelivery.repository.DishesRepositoryContract
import ru.skillbranch.sbdelivery.repository.error.EmptyDishesError

class CategoriesFilterUseCase(private val repository: DishesRepositoryContract) : CategoriesFilter {
    override fun categoryFilterDishes(categoryId: String): Single<List<DishEntity>> {
        return repository.getCachedDishes()
            .map {
                if (categoryId.isEmpty())  it
                else it.filter { dish -> dish.categoryId == categoryId } }
            .flatMap {
                if (it.isEmpty()) Single.error(EmptyDishesError("No items in this category"))
                else Single.just(it)
            }
    }
}