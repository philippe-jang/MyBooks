package com.mybooks.app.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mybooks.app.adapter.callback.BookListDiffCallback
import com.mybooks.app.adapter.callback.OnBookClickListener
import com.mybooks.app.adapter.viewholder.BookImageTypeViewHolder
import com.mybooks.app.adapter.viewholder.BookTextTypeViewHolder
import com.mybooks.app.data.Document

/**
 * 도서 리스트 어댑터
 * @author philippe
 */
class BookListAdapter(var itemViewType: Int, private val bookClickListener: OnBookClickListener) :
    ListAdapter<Document, RecyclerView.ViewHolder>(BookListDiffCallback()) {

    companion object {
        const val TEXT_VIEW_TYPE = 1
        const val IMAGE_VIEW_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TEXT_VIEW_TYPE -> BookTextTypeViewHolder.from(parent, bookClickListener)
            IMAGE_VIEW_TYPE -> BookImageTypeViewHolder.from(parent, bookClickListener)
            else -> BookTextTypeViewHolder.from(parent, bookClickListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BookTextTypeViewHolder -> {
                holder.bind(getItem(position))
            }

            is BookImageTypeViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemViewType
    }

}