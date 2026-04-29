package pw.coding.konnecto.core.util

interface Paginator<T> {

    suspend fun loadNextItems()
}