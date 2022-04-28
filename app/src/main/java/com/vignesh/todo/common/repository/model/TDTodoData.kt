package com.vignesh.todo.common.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo_list")
data class TDTodoData(
    var name: String? = "",//todoName
    var description: String? = "",//todoDescription
    var category: String? = "",//category
    var status: String? = "",//complete,in-progress,yet-to-start
    var reminder: String? = "",//Daily,Weekly,Monthly
    var reminderTime: String? = "",
    var createdAt: Long = Date().time,
    var deletedAt: Long? = null,
    var updatedAt: Long? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
