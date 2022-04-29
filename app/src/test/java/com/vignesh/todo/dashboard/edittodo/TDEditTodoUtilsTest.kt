package com.vignesh.todo.dashboard.edittodo

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class TDEditTodoUtilsTest {
    @Test
    fun `null value on empty check return true`() {
        val result = TDEditTodoUtils.emptyCheck(null)
        assertThat(result).isTrue()
    }

    @Test
    fun `empty value on empty check return true`() {
        val result = TDEditTodoUtils.emptyCheck("")
        assertThat(result).isTrue()
    }

    @Test
    fun `value on empty check return false`() {
        val result = TDEditTodoUtils.emptyCheck("Test")
        assertThat(result).isFalse()
    }

    @Test
    fun `both value as null on changes maded check return false`() {
        val result = TDEditTodoUtils.checkMade(null, null)
        assertThat(result).isFalse()
    }

    @Test
    fun `oldValue null and newValue value on changes maded check return true`() {
        val result = TDEditTodoUtils.checkMade(null, "Test")
        assertThat(result).isTrue()
    }

    @Test
    fun `oldValue value and newValue null on changes maded check return true`() {
        val result = TDEditTodoUtils.checkMade("Test", null)
        assertThat(result).isTrue()
    }

    @Test
    fun `oldValue value and newValue value as same on changes maded check return false`() {
        val result = TDEditTodoUtils.checkMade("Test", "Test")
        assertThat(result).isFalse()
    }

    @Test
    fun `oldValue value and newValue value as different on changes maded check return true`() {
        val result = TDEditTodoUtils.checkMade("Test", "TestValue")
        assertThat(result).isTrue()
    }
}