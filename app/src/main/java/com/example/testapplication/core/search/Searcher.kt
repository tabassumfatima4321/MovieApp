package com.example.testapplication.core.search

class Searcher<T>(
    private val originalList: MutableList<T>,
    private val toReadableString: ((receivedObj: T) -> String),
) {

    fun search(text: String?, originalListOnEmptyString: Boolean = true): List<T> {
        if (text.isNullOrBlank()) return if (originalListOnEmptyString) originalList else listOf()
        val tokens = tokenizeString(text.lowercase())
        return originalList.filter {
            matchToken(tokens, tokenizeString(toReadableString(it).lowercase()))
        }
    }

    private fun matchToken(searchTokens: List<String>, originalTokens: List<String>): Boolean {
        if (searchTokens.size > originalTokens.size) return false
        val filtered = searchTokens.filter { a ->
            originalTokens.any { b ->
                b.contains(a) /*|| a.contains(b)*/
            }
        }
        return filtered.size == searchTokens.size
    }

    fun tokenizeString(str: String): List<String> {
        return str.trim().split(" ")
    }

}