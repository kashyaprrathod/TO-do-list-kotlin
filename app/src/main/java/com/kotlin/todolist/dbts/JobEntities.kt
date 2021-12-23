package com.kotlin.todolist.dbts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "JobEntities")
data class JobEntities(
    @ColumnInfo(name= "Title") var title: String,
    @ColumnInfo(name= "Job") var job: String,
    @ColumnInfo(name= "Status") var status: Boolean
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var Id: Int? = null

}
