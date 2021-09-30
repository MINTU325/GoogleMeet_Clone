package com.example.googlemeet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.googlemeet.model.FeedBack
import com.example.googlemeet.model.FeedbackDao

@Database(entities = [FeedBack::class], version = 1)
abstract class FeedbackRoomDataBase : RoomDatabase() {


    abstract fun getFeedbackDao(): FeedbackDao

    companion object {
        private var INSTANCE: FeedbackRoomDataBase? = null

        fun getDataBaseObject(context: Context): FeedbackRoomDataBase {
            if (INSTANCE == null) {

                var builder = Room.databaseBuilder(context.applicationContext,
                    FeedbackRoomDataBase::class.java,
                    "task_db")
                builder.fallbackToDestructiveMigration()

                INSTANCE = builder.build()
                return INSTANCE!!
            } else {
                return INSTANCE!!
            }
        }
    }


}