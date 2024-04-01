package com.example.korlearn2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface LocationsDao {

    /////LOCATION/////////
    @Query ("SELECT * FROM Location")
    suspend fun getLocation(): List<Location>
    @Query ("SELECT id FROM Location")
    suspend fun getLocationsIdList(): List<Int>
    @Query ("SELECT * FROM Location WHERE rulerName = 'null'")
    suspend fun getLocationsWithoutRuler(): List<Location>
    @Query ("SELECT * FROM Location WHERE id = :id")
    suspend fun getLocationById(id: Int): Location
    @Query ("SELECT rulerName FROM Location WHERE rulerName != 'null'")
    suspend fun getRulersOnLocations(): List<String>
    @Query("SELECT id FROM Location WHERE depleted = 'false'")
    suspend fun getNotDepletedLocations() : List<Int>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)
    @Update
    suspend fun updateLocation(location: Location)
    /////LEADERS/////////
    @Query ("SELECT * FROM LocalRuler")
    suspend fun getLocalRuler(): List<LocalRuler>
    @Query ("SELECT * FROM LocalRuler WHERE id = :id")
    suspend fun getLocalRulerById(id: Int): LocalRuler
    @Query ("SELECT * FROM LocalRuler WHERE rulerName = :rulerName")
    suspend fun getLocalRulerByName(rulerName: String): LocalRuler
    @Query ("SELECT * FROM LocalRuler WHERE rulerName NOT IN (:occupied)")
    suspend fun getFreeLocalRulers(occupied: List<String>): List<LocalRuler>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalRuler(localRuler: LocalRuler)
    @Update
    suspend fun updateLocalRuler(localRuler: LocalRuler)
    /////SQUADS/////////
    @Query ("SELECT * FROM Squad")
    suspend fun getSquad(): List<Squad>

    @Query ("SELECT * FROM Squad WHERE id = :id")
    suspend fun getSquadById(id: Int): Squad

    @Query ("SELECT * FROM Squad WHERE locationId = :locationId")
    suspend fun getSquadsInLocation(locationId: Int): List<Squad>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSquad(squad: Squad)
    @Update
    suspend fun updateSquad(squad: Squad)
    /////YOUR_STATS/////////
    @Query ("SELECT * FROM YourStats")
    suspend fun getYourStats(): List<YourStats>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYourStats(yourStats: YourStats)
    @Update
    suspend fun updateYourStats(yourStats: YourStats)

    /////ENEMY_STATS/////////
    @Query ("SELECT * FROM EnemyStats")
    suspend fun getEnemyStats(): List<EnemyStats>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnemyStats(enemyStats: EnemyStats)
    @Update
    suspend fun updateEnemyStats(enemyStats: EnemyStats)
}