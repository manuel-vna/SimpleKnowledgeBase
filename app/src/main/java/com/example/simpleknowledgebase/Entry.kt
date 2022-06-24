package com.example.simpleknowledgebase

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "KbTable")
data class Entry (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var date: String,
    var title: String,
    var category: String,
    var description: String,
    var source: String
    )
{
    @Ignore
    constructor(id: Int,date: String, title: String, category: String): this(id,date, title,category,"empty", "empty")

    @Ignore
    constructor(id: Int, date: String, title: String, category: String, description: String): this(id,date,title,category,description, "empty")
}







