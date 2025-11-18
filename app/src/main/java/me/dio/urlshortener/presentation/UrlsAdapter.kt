package me.dio.urlshortener.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.dio.urlshortener.domain.ShortenedUrl
import me.dio.urlshortener.databinding.ItemUrlBinding

class UrlsAdapter : ListAdapter<ShortenedUrl, UrlsAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUrlBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemUrlBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shortenedUrl: ShortenedUrl) = binding.run {
            tvUrl.text = shortenedUrl.url
            tvOriginalUrl.text = shortenedUrl.original

            ivCopy.setOnClickListener {
                val context = it.context
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Shortened URL", shortenedUrl.url)
                clipboard.setPrimaryClip(clip)

                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class DiffCallBack : ItemCallback<ShortenedUrl>() {
        override fun areItemsTheSame(oldItem: ShortenedUrl, newItem: ShortenedUrl) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ShortenedUrl, newItem: ShortenedUrl) =
            oldItem == newItem
    }
}