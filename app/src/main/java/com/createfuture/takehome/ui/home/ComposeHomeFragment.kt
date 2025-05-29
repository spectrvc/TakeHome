package com.createfuture.takehome.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.createfuture.data.ApiCharacter
import com.createfuture.takehome.ui.factory.ComposeHomeViewModelFactory
import com.createfuture.takehome.ui.home.screen.ComposeHomeScreen
import com.createfuture.takehome.ui.home.screen.ComposeHomeViewModel
import com.createfuture.takehome.ui.theme.Typography
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

class ComposeHomeFragment : Fragment() {
    private lateinit var viewModel: ComposeHomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): ComposeView = ComposeView(requireContext()).apply {
        val retrofit =
            Retrofit.Builder().baseUrl("https://yj8ke8qonl.execute-api.eu-west-1.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(OkHttpClient.Builder().build()).build()
        val service = retrofit.create(Service::class.java)
        val charactersBody = mutableStateOf<List<ApiCharacter>?>(null)
        viewLifecycleOwner.lifecycleScope.launch {
            val _characters = service.getCharacters("Bearer 754t!si@glcE2qmOFEcN")
            charactersBody.value = _characters.body()!!
        }
        viewModel = ViewModelProvider(
            requireActivity(),
            ComposeHomeViewModelFactory()
        )[ComposeHomeViewModel::class.java]
        setContent {
            MaterialTheme(
                typography = Typography,
                content = { ComposeHomeScreen(viewModel, charactersBody.value) }
            )
        }
    }
}

interface Service {
    @GET("/characters")
    suspend fun getCharacters(@Header("Authorization") token: String): Response<List<ApiCharacter>>
}