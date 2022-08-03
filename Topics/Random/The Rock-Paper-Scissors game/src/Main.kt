import kotlin.random.Random

fun makeDecision(): String {
    val map = mapOf(1 to "Rock", 2 to "Paper", 3 to "Scissors")
    return map[Random.nextInt(1, 4)]!!
}