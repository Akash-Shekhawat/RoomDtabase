package com.example.roomdtabase.roomdata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdtabase.EmployeeDataClass


    @Database(entities = [EmployeeDataClass::class], version = 1)
    abstract class AppRoomDatabase : RoomDatabase() {
        abstract fun employeeDao(): RoomEmployeeDoa
    }
