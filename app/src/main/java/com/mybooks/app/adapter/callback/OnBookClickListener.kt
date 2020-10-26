package com.mybooks.app.adapter.callback

import com.mybooks.app.data.Document

/**
 * 도서 목록 클릭 리스너
 * @author philippe
 */
interface OnBookClickListener {
    fun onClickBook(document: Document)
}