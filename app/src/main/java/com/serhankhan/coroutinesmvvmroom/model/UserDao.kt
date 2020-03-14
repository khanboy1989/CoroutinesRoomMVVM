package com.serhankhan.coroutinesmvvmroom.model

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user:User):Long

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUser(username:String):User?

    @Query("DELETE FROM user WHERE id = :id")
    fun deleteUser(id:Long)


}