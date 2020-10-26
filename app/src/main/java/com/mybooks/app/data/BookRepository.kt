package com.mybooks.app.data

import com.mybooks.app.api.BookService

/**
 * 도서 관련 저장소 (API 호출)
 * @author philippe
 */
class BookRepository(private val bookService: BookService) {

    /**
     * 도서 검색
     */
    suspend fun getBookList(searchWord: String, pageNo: Int) = bookService.searchBooks(searchWord, page = pageNo)

}