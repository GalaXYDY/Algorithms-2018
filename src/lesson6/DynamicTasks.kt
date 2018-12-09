@file:Suppress("UNUSED_PARAMETER")

package lesson6

import java.io.File
import java.util.Arrays.*
import kotlin.collections.ArrayList
import kotlin.math.min

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
fun longestCommonSubSequence(first: String, second: String): String {
    TODO()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Средняя
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    if (list.isEmpty() || list.size == 1) return list
    var max = 0
    val length = IntArray(list.size)
    val prev = IntArray(list.size)
    fill(length, 1)
    fill(prev, -1)
    for (i in 1 until list.size) {
        for (j in 0 until i) {
            if (list[j] < list[i] && length[j] + 1 > length[i]) {
                prev[i] = j
                length[i] = length[j] + 1
                if (length[max] < length[i]) max = i
            }
        }
    }
    var count = length[max]
    val result = IntArray(count)
    var i = max
    while (i != -1) {
        result[--count] = list[i]
        i = prev[i]
    }
    return result.toList()
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Сложная
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    val list = ArrayList<List<String>>()
    File(inputName).forEachLine { list.add(it.split(" ")) }
    val height = list.size
    val length = list[0].size
    val field = Array(height) { IntArray(length) }
    field[0][0] = list[0][0].toInt()
    for (i in 1 until length) {
        field[0][i] = list[0][i].toInt() + field[0][i - 1]
    }
    for (i in 1 until height) {
        field[i][0] = list[i][0].toInt() + field[i - 1][0]
    }
    for (i in 1 until length) {
        for (j in 1 until height) {
            val min = min(min(field[j - 1][i], field[j][i - 1]), field[j - 1][i - 1])
            field[j][i] = list[j][i].toInt() + min
        }
    }
    return field[height - 1][length - 1]
}

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5