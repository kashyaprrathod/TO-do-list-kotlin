package com.kotlin.todolist.dbts

import androidx.room.*

@Dao
interface JobDao {

    @Insert
    fun insertData(ent : JobEntities);

    @Query("SELECT * FROM JobEntities WHERE Title = :title")
    fun getDataByTitle(title:String):List<JobEntities>;

    @Query("SELECT * FROM JobEntities WHERE Title = :title AND Job = :task")
    fun getDataByTitleAndTask(title: String,task: String):JobEntities;

    @Query("SELECT COUNT(Status) FROM JobEntities WHERE Status = :status")
    fun getDataByStatus(status:Boolean):Int;

    @Query("SELECT COUNT(Id) FROM JOBENTITIES")
    fun getTotolColumn():Int

    @Query("SELECT COUNT(Id) FROM JobEntities WHERE Status = 1")
    fun getRemainingTaskCount():Int

    @Query("SELECT DISTINCT Title FROM JOBENTITIES")
    fun getTableName() : List<String>

    @Query("SELECT COUNT(Status) FROM JOBENTITIES WHERE Title = :listName AND Status = 0")
    fun getTableRemainingStatus(listName : String) : Int

    @Query("SELECT EXISTS(SELECT * FROM JOBENTITIES WHERE Title = :title AND Job = :task)")
    fun ifRecordAvailable(title : String, task : String):Boolean

    @Update
    fun updateData(ent: JobEntities)

    @Delete
    fun deleteData(ent: JobEntities)

    @Query("DELETE FROM JobEntities WHERE Title =:title")
    fun deleteDataBYTitle(title:String)

}