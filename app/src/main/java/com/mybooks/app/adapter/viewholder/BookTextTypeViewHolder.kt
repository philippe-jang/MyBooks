package com.mybooks.app.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mybooks.app.adapter.callback.OnBookClickListener
import com.mybooks.app.data.Document
import com.mybooks.app.databinding.ItemTextTypeBookBinding

/**
 * 텍스트 타입 도서 뷰홀더
 * @author philippe
 */
class BookTextTypeViewHolder(private val binding: ItemTextTypeBookBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(document: Document) {
        binding.document = document
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup, bookClickListener: OnBookClickListener): BookTextTypeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemTextTypeBookBinding.inflate(layoutInflater, parent, false)
            binding.bookClickListener = bookClickListener
            return BookTextTypeViewHolder(binding)
        }
    }
}