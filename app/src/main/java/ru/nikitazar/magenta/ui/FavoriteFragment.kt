package ru.nikitazar.magenta.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.nikitazar.magenta.R
import ru.nikitazar.magenta.databinding.FragmentFavoriteBinding
import ru.nikitazar.magenta.databinding.FragmentImageListBinding
import ru.nikitazar.magenta.viewModel.ImageListViewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: ImageListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

}