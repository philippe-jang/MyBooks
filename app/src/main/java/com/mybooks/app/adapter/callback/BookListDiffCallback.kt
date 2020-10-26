package com.mybooks.app.adapter.callback

import androidx.recyclerview.widget.DiffUtil
import com.mybooks.app.data.Document

/**
 * 기존 도서 목록과 신규 도서 목록을 비교하는 callback
 * @author philippe
 */
class BookListDiffCallback : DiffUtil.ItemCallback<Document>() {
    override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
        return (oldItem.title == newItem.title && oldItem.thumbnail == newItem.thumbnail)
    }

    override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
        return oldItem == newItem
    }
}