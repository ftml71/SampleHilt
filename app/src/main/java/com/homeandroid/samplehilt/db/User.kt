package com.homeandroid.samplehilt.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        var name: String? = null,
        var family: String? = null
)