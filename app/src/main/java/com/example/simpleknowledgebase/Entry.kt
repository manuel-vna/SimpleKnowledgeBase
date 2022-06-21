package com.example.simpleknowledgebase

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "KbTable")
data class Entry (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var category: String,
    var date: String,
    var description: String,
    var source: String
    )
{
    @Ignore
    constructor(id: Int, title: String, category: String, date: String): this(id, title,category,date,"empty", "empty")

    @Ignore
    constructor(id: Int, title: String, category: String, date: String, description: String): this(id, title,category,date,description, "empty")
}







