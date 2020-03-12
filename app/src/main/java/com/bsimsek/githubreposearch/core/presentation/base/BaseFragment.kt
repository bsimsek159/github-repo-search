package com.bsimsek.githubreposearch.core.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bsimsek.githubreposearch.core.extension.createProgress
import com.bsimsek.githubreposearch.core.extension.lazyThreadSafetyNone
import dagger.android.support.AndroidSupportInjection
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<V : ViewModel> : Fragment() {
    @get:LayoutRes
    abstract val layoutRes: Int
    private var dialog: Dialog? = null
    private var progressDialogCount = 0

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Suppress("UNCHECKED_CAST")
    protected open val viewModel by lazyThreadSafetyNone {
        val persistentViewModelClass = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<V>
        return@lazyThreadSafetyNone ViewModelProviders.of(this, viewModelFactory)
            .get(persistentViewModelClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun showProgress() {
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

    fun hideProgress() {
        progressDialogCount -= progressDialogCount
        if (progressDialogCount == 0) {
            dialog?.dismiss()
            dialog = null
        }
    }

    open fun initView() {}
}