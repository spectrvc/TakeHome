package com.createfuture.takehome.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.createfuture.takehome.ui.factory.ComposeHomeViewModelFactory
import com.createfuture.takehome.ui.home.screen.ComposeHomeScreen
import com.createfuture.takehome.ui.home.screen.ComposeHomeViewModel
import com.createfuture.takehome.ui.theme.Typography

class ComposeHomeFragment : Fragment() {
    private lateinit var viewModel: ComposeHomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): ComposeView = ComposeView(requireContext()).apply {
        viewModel = ViewModelProvider(
            requireActivity(),
            ComposeHomeViewModelFactory()
        )[ComposeHomeViewModel::class.java]
        setContent {
            MaterialTheme(
                typography = Typography,
                content = { ComposeHomeScreen(viewModel) }
            )
        }
    }
}
