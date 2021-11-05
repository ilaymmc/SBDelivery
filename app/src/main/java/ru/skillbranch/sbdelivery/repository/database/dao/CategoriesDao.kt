package ru.skillbranch.sbdelivery.repository.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single
import ru.skillbranch.sbdelivery.repository.database.entity.CategoryPersistEntity

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories_table")
    fun getAllCategories(): Single<List<CategoryPersistEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategories(categories: List<CategoryPersistEntity>)
}
