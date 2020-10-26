package com.mybooks.app.ui.detail

import android.os.Bundle
import android.view.View
import com.mybooks.app.R
import com.mybooks.app.data.Document
import com.mybooks.app.databinding.FragmentDetailBinding
import com.mybooks.app.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * 도서 상세 정보 화면
 * @author philippe
 */
class DetailFragment : BaseFragment<FragmentDetailBinding>(), View.OnClickListener {

    companion object {

        private const val DETAIL_INFO_KEY = "detail_info"

        @JvmStatic
        fun newInstance(document: Document) = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(DETAIL_INFO_KEY, document)
            }
        }
    }

    private val detailViewModel: DetailViewModel by sharedViewModel()

    private var document: Document? = null

    override fun getLayoutRes(): Int = R.layout.fragment_detail

    override fun initData() {
        arguments?.let {
            document = it.getParcelable(DETAIL_INFO_KEY)
            document?.let { document ->
                detailViewModel.setDocument(document)
            }
        }
    }

    override fun initView(viewDataBinding: FragmentDetailBinding) {
        viewDataBinding.viewModel = detailViewModel
        viewDataBinding.clickListener = this
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_back -> {
                activity?.onBackPressed()
            }
        }
    }

}