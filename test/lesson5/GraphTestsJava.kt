package lesson5

import org.junit.jupiter.api.Tag
import kotlin.test.Test

class GraphTestsJava : AbstractGraphTests() {
    @Test
    @Tag("Normal")
    fun testFindEulerLoop() {
        findEulerLoop { let { JavaGraphTasks.findEulerLoop(it) } }
    }

    @Test
    @Tag("Hard")
    fun testLongestSimplePath() {
        longestSimplePath { let { JavaGraphTasks.longestSimplePath(it) } }
    }

}