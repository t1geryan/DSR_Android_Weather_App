package com.example.weatherapp.presentation.ui.base_list_screen

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentListBinding
import com.example.weatherapp.presentation.contract.sideeffects.dialogs.SimpleDialogProvider
import com.example.weatherapp.presentation.contract.sideeffects.snakbars.SnackbarProvider
import javax.inject.Inject

abstract class BaseListFragment : Fragment() {

    protected lateinit var binding: FragmentListBinding

    @Inject
    lateinit var dialogProvider: SimpleDialogProvider

    @Inject
    lateinit var snackbarProvider: SnackbarProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        // Add divider between recycler view elements
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(), DividerItemDecoration.VERTICAL
            )
        )

        // Remove default item change animation (reason: annoying blinking)
        val itemAnimator = binding.recyclerView.itemAnimator
        if (itemAnimator is DefaultItemAnimator) itemAnimator.supportsChangeAnimations = false

        return binding.root
    }

    protected fun hideSupportingViews() = with(binding) {
        progressBar.visibility = View.GONE
        emptyListMessageTV.visibility = View.GONE
    }

    protected fun showEmptyListMessage(message: String) = with(binding.emptyListMessageTV) {
        text = message
        visibility = View.VISIBLE
    }

    protected fun showErrorDialog(
        title: String,
        message: String?,
        onClickListener: DialogInterface.OnClickListener
    ) {
        dialogProvider.showSimpleDialog(
            title,
            message ?: getString(R.string.default_exception_message),
            true,
            getString(R.string.refresh),
            null,
            getString(R.string.close),
            onClickListener
        )
    }
}