package ru.skillbranch.sbdelivery.domain

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ru.skillbranch.sbdelivery.domain.entity.DishEntity
import ru.skillbranch.sbdelivery.repository.DishesRepositoryContract
import ru.skillbranch.sbdelivery.repository.error.EmptyDishesError
import java.util.*

class SearchUseCaseImpl(private val repository: DishesRepositoryContract) : SearchUseCase {

    override fun getDishes(): Single<List<DishEntity>> = repository.getCachedDishes()


    override fun findDishesByName(searchText: String): Observable<List<DishEntity>> =
        repository.findDishesByName(searchText)
                .flatMap {
                    if (it.isEmpty()) Observable.error(EmptyDishesError("No items in this category"))
                    else Observable.just(it)
                }

//        repository.getCachedDishes().toObservable()
//            .map { dishes ->
//                dishes.filter {
//                    it.title.toLowerCase(Locale.ROOT).contains(searchText.trim().toLowerCase(Locale.ROOT))
//                }
//            }
//            .flatMap {
//                if (it.isEmpty()) Observable.error(EmptyDishesError("No items in this category"))
//                else Observable.just(it)
//            }

}