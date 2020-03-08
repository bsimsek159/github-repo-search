package com.bsimsek.githubreposearch.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.bsimsek.githubreposearch.core.createProgress
import com.bsimsek.githubreposearch.data.network.DataHolder
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<V : BaseViewModel<DataHolder>> : Fragment() {
    @LayoutRes
    abstract fun getLayoutRes(): Int
    lateinit var mViewModel: V
    abstract fun getViewModel(): V

    private var dialog: Dialog? = null
    private var progressDialogCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun showBlockingPane() {
        if (progressDialogCount == 0) {
            if (dialog == null) {
                dialog = activity?.createProgress()
            }

            dialog?.let {
                if (!it.isShowing) {
                    it.show()
                }
            }
        }
        progressDialogCount += progressDialogCount
    }

    fun hideBlockingPane() {
        progressDialogCount -= progressDialogCount
        if (progressDialogCount == 0) {
            dialog?.dismiss()
            dialog = null
        }
    }

    open fun initView() {}

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}