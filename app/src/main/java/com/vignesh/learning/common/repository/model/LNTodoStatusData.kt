package com.vignesh.learning.common.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo_status")
data class LNTodoStatusData(
    var name: String = "",
    var createdAt: Long = Date().time,
    var updatedAt: Long?,
    var deletedAt: Long?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}