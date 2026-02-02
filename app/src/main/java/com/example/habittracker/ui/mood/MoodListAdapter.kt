package com.example.habittracker.ui.mood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.data.model.MoodEntry
import com.example.habittracker.databinding.ItemMoodBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MoodListAdapter : ListAdapter<MoodEntry, MoodListAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<MoodEntry>() {
        override fun areItemsTheSame(oldItem: MoodEntry, newItem: MoodEntry) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MoodEntry, newItem: MoodEntry) = oldItem == newItem
    }

    inner class VH(val binding: ItemMoodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemMoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.binding.textEmoji.text = item.emoji
        holder.binding.textNote.text = item.note
        holder.binding.textDate.text = dateFormat.format(Date(item.timestampMillis))
    }

    companion object {
        private val dateFormat = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
    }
}


