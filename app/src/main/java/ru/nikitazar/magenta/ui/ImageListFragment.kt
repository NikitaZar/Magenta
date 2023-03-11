package ru.nikitazar.magenta.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.nikitazar.domain.models.Image
import ru.nikitazar.magenta.R
import ru.nikitazar.magenta.databinding.FragmentImageListBinding
import ru.nikitazar.magenta.ui.adapters.PageImageAdapter
import ru.nikitazar.magenta.ui.adapters.ImageOnInteractionListener
import ru.nikitazar.magenta.ui.adapters.PageLoadStateAdapter
import ru.nikitazar.magenta.viewModel.ImageListViewModel

@AndroidEntryPoint
class ImageListFragment : Fragment() {

    private lateinit var binding: FragmentImageListBinding
    private val viewModel: ImageListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageListBinding.inflate(inflater, container, false)

        setupList()
        onBackPressed()

        return binding.root
    }

    private fun setupList() {
        val adapter = PageImageAdapter(object : ImageOnInteractionListener {
            override fun onLike(image: Image) {
                when (image.isFavorite) {
                    true -> viewModel.dislike(image.id)
                    false -> viewModel.like(image.id)
                }
            }
        })
        val footerAdapter = PageLoadStateAdapter()
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
        binding.list.adapter = adapterWithLoadState
        observeImages(adapter)
        setupSwipeToRefresh(adapter)
        observeErrors(adapter)
    }

    private fun observeImages(adapter: PageImageAdapter) = lifecycleScope.launchWhenCreated {
        viewModel.pageDataFlow.collectLatest { pagingData ->
            adapter.submitData(lifecycle, pagingData)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun observeErrors(adapter: PageImageAdapter) {
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error) {
                val errText = getString(R.string.err_req_mes)
                val btText = getString(R.string.err_bt_snack_bar_text)
                Snackbar.make(binding.root, errText, Snackbar.LENGTH_INDEFINITE)
                    .setAction(btText) { adapter.refresh() }
                    .show()
            }
        }
    }

    private fun setupSwipeToRefresh(adapter: PageImageAdapter) {
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun onBackPressed() {
        activity
            ?.onBackPressedDispatcher
            ?.addCallback(viewLifecycleOwner) {
                activity?.finish()
            }
    }
}