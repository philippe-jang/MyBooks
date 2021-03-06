package com.mybooks.app.ui.search

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mybooks.app.R
import com.mybooks.app.adapter.BookListAdapter
import com.mybooks.app.adapter.callback.OnBookClickListener
import com.mybooks.app.data.Document
import com.mybooks.app.databinding.FragmentSearchBinding
import com.mybooks.app.ui.MainActivity
import com.mybooks.app.ui.base.BaseFragment
import com.mybooks.app.ui.detail.DetailFragment
import com.mybooks.app.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 도서 검색 화면
 * @author philippe
 */
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    companion object {
        private const val DEFAULT_VIEW_TYPE = BookListAdapter.TEXT_VIEW_TYPE

        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    private val searchViewModel: SearchViewModel by viewModel()
    private val detailViewModel: DetailViewModel by sharedViewModel()

    private lateinit var bookListAdapter: BookListAdapter
    private var currentListViewType = DEFAULT_VIEW_TYPE

    private val bookClickListener = object : OnBookClickListener {
        override fun onClickBook(document: Document) {
            // 도서 상세 화면으로 이동
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, DetailFragment.newInstance(document))
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_search

    override fun initData() {
        bookListAdapter = BookListAdapter(DEFAULT_VIEW_TYPE, bookClickListener)
    }

    override fun initView(viewDataBinding: FragmentSearchBinding) {
        viewDataBinding.viewModel = searchViewModel
        initBookListView(viewDataBinding.rvBookList, DEFAULT_VIEW_TYPE, bookListAdapter)
        initBookListScrollListener(viewDataBinding.rvBookList)

        searchViewModel.bookListViewType.observe(this@SearchFragment, {
            currentListViewType = it
            setListViewType(viewDataBinding.rvBookList, it)
        })

        searchViewModel.mainBookList.observe(this, {
            hideKeyboard(viewDataBinding.layoutToolbarSearch.etSearchKeyword)
            bookListAdapter.submitList(it)
            searchViewModel.setEmptyBookList(it.isEmpty())
        })

        searchViewModel.showNetworkError.observe(this, { _ ->
            activity?.let {
                showAlertDialog(it, getString(R.string.message_network_error))
            }
        })

        detailViewModel.sharedDocument.observe(this, {
            updateDocument(it)
        })

        searchViewModel.searchBookList(getString(R.string.default_search_keyword))
    }

    /**
     * 도서 목록 리스트 초기화
     * @param bookListView 도서 리스트뷰
     * @param viewType 리스트뷰 뷰타입 (TEXT, IMAGE)
     * @param bookListAdapter 도서 리스트 어댑터
     */
    private fun initBookListView(bookListView: RecyclerView, viewType: Int, bookListAdapter: BookListAdapter) {
        when (viewType) {
            BookListAdapter.TEXT_VIEW_TYPE -> {
                bookListView.layoutManager = LinearLayoutManager(bookListView.context)
            }
            BookListAdapter.IMAGE_VIEW_TYPE -> {
                bookListView.layoutManager = GridLayoutManager(bookListView.context, 2)
            }
        }

        bookListView.adapter = bookListAdapter
    }

    /**
     * 도서 리스트뷰 스크롤 리스너 초기화
     * @param bookListView 도서 리스트뷰
     */
    private fun initBookListScrollListener(bookListView: RecyclerView) {
        bookListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (searchViewModel.isLoading() || searchViewModel.isEndBookList) {
                    return
                }

                recyclerView.adapter?.let {
                    val layoutManager = when (currentListViewType) {
                        BookListAdapter.TEXT_VIEW_TYPE -> {
                            (bookListView.layoutManager as LinearLayoutManager)
                        }
                        BookListAdapter.IMAGE_VIEW_TYPE -> {
                            (bookListView.layoutManager as GridLayoutManager)
                        }
                        else -> (bookListView.layoutManager as LinearLayoutManager)
                    }

                    if (layoutManager.findLastCompletelyVisibleItemPosition() > it.itemCount - 6) {
                        searchViewModel.searchMoreBookList()
                    }
                }
            }
        })
    }

    /**
     * 특정 도서 정보 갱신 (상세 화면에서 좋아요 선택시)
     */
    private fun updateDocument(document: Document) {
        bookListAdapter.currentList.run {
            for (i in 0 until this.size) {
                val curDocument = this[i]
                if (curDocument.isbn == document.isbn) {
                    curDocument.isFavorite = document.isFavorite
                    bookListAdapter.notifyItemChanged(i)
                    break
                }
            }
        }
    }

    /**
     * 알림창 띄우기
     */
    private fun showAlertDialog(context: Context, msg: String) {
        val alertDialog = AlertDialog.Builder(context).apply {
            setMessage(msg)
            setPositiveButton(R.string.btn_confirm) { dialog, _ ->
                dialog.dismiss()
            }
            create()
        }
        alertDialog.show()
    }

    /**
     * 키보드 숨기기
     * @param focusView 현재 포커스를 가진 뷰 (TextInputEditText)
     */
    private fun hideKeyboard(focusView: View) {
        activity?.let {
            if (it is MainActivity) {
                it.hideKeyboard(focusView)
            }
        }
    }

    /**
     * 도서 리스트 뷰타입 변경
     * @param bookListView 도서 리스트뷰
     * @param viewType 리스트뷰 뷰타입 (TEXT, IMAGE)
     */
    private fun setListViewType(bookListView: RecyclerView, viewType: Int) {
        when (viewType) {
            BookListAdapter.TEXT_VIEW_TYPE -> {
                bookListView.layoutManager = LinearLayoutManager(bookListView.context)
            }
            BookListAdapter.IMAGE_VIEW_TYPE -> {
                bookListView.layoutManager = GridLayoutManager(bookListView.context, 2)
            }
        }

        bookListView.adapter?.let {
            if (it is BookListAdapter) {
                it.itemViewType = viewType
            }
        }
    }

}