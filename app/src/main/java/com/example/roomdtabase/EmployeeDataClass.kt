package com.example.roomdtabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emp_table")
data class EmployeeDataClass(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "emp_id")
    var id: Int = 0,
    var name: String? = null,
    var contact: String? = null,
    var address: String? = null
)