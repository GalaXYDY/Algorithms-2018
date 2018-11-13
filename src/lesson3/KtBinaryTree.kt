package lesson3

import java.util.SortedSet
import kotlin.NoSuchElementException

// Attention: comparable supported but comparator is not
class KtBinaryTree<T : Comparable<T>> : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private var root: Node<T>? = null

    override var size = 0
        private set

    private class Node<T>(var value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null
    }

    override fun add(element: T): Boolean {
        val closest = find(element)
        val comparison = if (closest == null) -1 else element.compareTo(closest.value)
        if (comparison == 0) {
            return false
        }
        val newNode = Node(element)
        when {
            closest == null -> root = newNode
            comparison < 0 -> {
                assert(closest.left == null)
                closest.left = newNode
            }
            else -> {
                assert(closest.right == null)
                closest.right = newNode
            }
        }
        size++
        return true
    }

    override fun checkInvariant(): Boolean =
            root?.let { checkInvariant(it) } ?: true

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        if (left != null && (left.value >= node.value || !checkInvariant(left))) return false
        val right = node.right
        return right == null || right.value > node.value && checkInvariant(right)
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */

    private fun enter(node: Node<T>, newOne: Node<T>?) {
        val parent = findParent(node.value)
        if (parent == null) {
            if (newOne == null) {
                root = null
                return
            }
            root!!.value = newOne.value
            root!!.left = newOne.left
            root!!.right = newOne.right
            return
        }
        if (parent.left === node) parent.left = newOne
        if (parent.right === node) parent.right = newOne
    }

    private fun del(node: Node<T>) {
        enter(node, null)
    }

    override fun remove(element: T): Boolean {
        val element2 = find(element)
        if (element2 == null || element2.value !== element) return false

        if (element2.left == null && element2.right == null) del(element2)
        else if (element2.left == null) enter(element2, element2.right)
        else if (element2.right == null) enter(element2, element2.left)
        else {
            val change = min(element2.left)
            val value = change!!.value
            remove(change.value)
            size++
            element2.value = value
        }
        size--
        return true
    }

    private fun min(root: Node<T>?): Node<T>? {
        if (root == null) return null
        if (root.right != null) return min(root.right)

        return root
    }

    private fun findParent(value: T, start: Node<T>): Node<T> {
        if (start.left != null && start.left!!.value === value
                || start.right != null && start.right!!.value === value) return start
        val comparison = value.compareTo(start.value)
        when {
            comparison > 0 -> {
                if (start.right == null) return start
                return findParent(value, start.right!!)
            }
            comparison < 0 -> {
                if (start.left == null) return start
                return findParent(value, start.left!!)
            }
            else -> return start
        }
    }

    private fun findParent(value: T): Node<T>? {
        if (root == null || (root as Node<T>).value === value) return null
        return findParent(value, root!!)
    }

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    private fun find(value: T): Node<T>? =
            root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T> {
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }
    }

    inner class BinaryTreeIterator : MutableIterator<T> {

        private var current: Node<T>? = null

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private fun findNext(): Node<T>? {
            return null
        }

        override fun hasNext(): Boolean = findNext() != null

        override fun next(): T {
            current = findNext()
            return (current ?: throw NoSuchElementException()).value
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        override fun remove() {
            TODO()
        }
    }

    override fun iterator(): MutableIterator<T> = BinaryTreeIterator()

    override fun comparator(): Comparator<in T>? = null

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> {
        TODO()
    }

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }
}
