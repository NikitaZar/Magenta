package ru.nikitazar.magenta.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.nikitazar.domain.models.Image
import ru.nikitazar.magenta.databinding.CardImageBinding
import ru.nikitazar.magenta.ui.utils.load

interface ImageOnInteractionListener {
    fun onLike(image: Image)
}

class PageImageAdapter(
    private val onInteractionListener: ImageOnInteractionListener
) : PagingDataAdapter<Image, ImageViewHolder>(ImageDiffCallback()) {
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = CardImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding, onInteractionListener)
    }
}

class ImageAdapter(
    private val onInteractionListener: ImageOnInteractionListener
) : ListAdapter<Image, ImageViewHolder>(ImageDiffCallback()) {
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = CardImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding, onInteractionListener)
    }
}

class ImageViewHolder(
    private val binding: CardImageBinding,
    private val onInteractionListener: ImageOnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(image: Image) {
        binding.image.load(image.downloadUrl)
        binding.author.text = image.author
        binding.btLike.isChecked = image.isFavorite

        binding.btLike.setOnClickListener {
            onInteractionListener.onLike(image)
        }
    }
}

class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Image, newItem: Image) = oldItem == newItem
}