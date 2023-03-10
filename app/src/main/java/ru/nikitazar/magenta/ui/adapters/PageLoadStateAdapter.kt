package ru.nikitazar.magenta.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.nikitazar.magenta.databinding.CardPageLoadStateBinding

class PageLoadStateAdapter : LoadStateAdapter<PageLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PageLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PageLoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardPageLoadStateBinding.inflate(inflater, parent, false)
        return PageLoadStateViewHolder(binding)
    }
}

class PageLoadStateViewHolder(
    private val binding: CardPageLoadStateBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }
}