package com.mybooks.app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mybooks.app.data.Document
import com.mybooks.app.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 도서 상세정보 뷰모델
 * @author philippe
 */
class DetailViewModel : BaseViewModel() {

    private val _document = MutableLiveData<Document>()
    val document: LiveData<Document> = _document

    private val _sharedDocument = MutableLiveData<Document>()
    val sharedDocument: LiveData<Document> = _sharedDocument

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    /**
     * 도서 정보 세팅
     * @param document 도서 정보
     */
    fun setDocument(document: Document) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _document.value = document
                _isFavorite.value = document.isFavorite
            }
        }
    }

    /**
     * 도서 좋아요(On/Off) 선택
     */
    fun onClickFavorite() {
        _isFavorite.value?.let { isFavorite ->
            _isFavorite.value = !isFavorite

            _document.value?.let { document ->
                document.isFavorite = !isFavorite
                _sharedDocument.value = document
            }
        }
    }

}