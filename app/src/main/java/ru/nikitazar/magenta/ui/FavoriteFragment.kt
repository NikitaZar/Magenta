package ru.nikitazar.magenta.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.nikitazar.domain.models.Image
import ru.nikitazar.magenta.R
import ru.nikitazar.magenta.databinding.FragmentFavoriteBinding
import ru.nikitazar.magenta.ui.adapters.ImageAdapter
import ru.nikitazar.magenta.ui.adapters.ImageOnInteractionListener
import ru.nikitazar.magenta.viewModel.ImageListViewModel

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: ImageListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        viewModel.getFavorite()
        setupList()
        onBackPressed()

        return binding.root
    }

    private fun setupList() {
        val adapter = ImageAdapter(
            object : ImageOnInteractionListener {
                override fun onLike(image: Image) {
                    when (image.isFavorite) {
                        true -> viewModel.dislike(image.id)
                        false -> viewModel.like(image.id)
                    }
                }
            }
        )
        binding.list.adapter = adapter
        observeImages(adapter)
    }

    private fun observeImages(adapter: ImageAdapter) {
        viewModel.favoriteData.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            setupEmptyText(list.isEmpty())
        }
    }

    private fun onBackPressed() {
        activity
            ?.onBackPressedDispatcher
            ?.addCallback(viewLifecycleOwner) {
                activity?.finish()
            }
    }

    private fun setupEmptyText(isVisible: Boolean) {
        binding.emptyText.apply {
            text = getString(R.string.favorite_list_is_empty_text)

            visibility = when (isVisible) {
                true -> View.VISIBLE
                false -> View.GONE
            }
        }
    }
}


