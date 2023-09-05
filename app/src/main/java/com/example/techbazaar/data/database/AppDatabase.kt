package com.example.techbazaar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.techbazaar.data.database.dao.ProductDao
import com.example.techbazaar.data.database.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        private var database:AppDatabase?=null
        private val databaseName ="App DataBase"

        @Synchronized
        fun getInstance(context: Context):AppDatabase{
            if(database==null){
                database=Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    databaseName
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

            }
            return database!!
        }
    }


    abstract fun productDao() :ProductDao

}