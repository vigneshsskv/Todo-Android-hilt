package com.vignesh.todo.common.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo_category")
data class TDTodoCategoryData(
    var name: String = "",
    var createdAt: Long = Date().time,
    var updatedAt: Long? = null,
    var deletedAt: Long? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
