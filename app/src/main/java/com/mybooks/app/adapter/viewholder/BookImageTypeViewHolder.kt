package com.mybooks.app.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mybooks.app.adapter.callback.OnBookClickListener
import com.mybooks.app.data.Document
import com.mybooks.app.databinding.ItemImageTypeBookBinding

/**
 * 이미지 타입 도서 뷰홀더
 * @author philippe
 */
class BookImageTypeViewHolder(private val binding: ItemImageTypeBookBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(document: Document) {
        binding.document = document
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup, bookClickListener: OnBookClickListener): BookImageTypeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemImageTypeBookBinding.inflate(layoutInflater, parent, false)
            binding.bookClickListener = bookClickListener
            return BookImageTypeViewHolder(binding)
        }
    }
}