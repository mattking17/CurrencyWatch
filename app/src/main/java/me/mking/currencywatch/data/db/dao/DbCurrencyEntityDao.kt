package me.mking.currencywatch.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.mking.currencywatch.data.db.entity.DbCurrencyEntity
import me.mking.currencywatch.domain.entity.CurrencyEntity

@Dao
interface DbCurrencyEntityDao {

    @Query("select * from currency_entities where isBase = 1 limit 1")
    fun baseCurrencyEntity(): Flow<DbCurrencyEntity?>

    @Query("select * from currency_entities")
    fun availableCurrencies(): Flow<List<DbCurrencyEntity>>

    @Query("select * from currency_entities where isPreferred = 1")
    fun preferredCurrencyEntities(): Flow<List<DbCurrencyEntity>>

    @Query("update currency_entities set isPreferred = 1, isBase = 0 where isBase = 1")
    suspend fun swapBaseAsPreferred()

    @Query("select count(*) from currency_entities")
    suspend fun availableCurrencyCount(): Int

    @Query("update currency_entities set isBase = 0")
    suspend fun clearBaseCurrencies()

    @Update
    suspend fun updateCurrencyEntity(currencyEntity: DbCurrencyEntity)

    @Insert
    suspend fun insert(vararg dbCurrencyEntity: DbCurrencyEntity)

    @Query("delete from currency_entities")
    suspend fun clear()

    @Query("select * from currency_entities where code = :code")
    suspend fun getCurrencyEntityByCode(code: String): DbCurrencyEntity
}