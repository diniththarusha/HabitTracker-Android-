package com.example.habittracker.ui.habits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.data.model.Habit
import com.example.habittracker.databinding.ItemHabitBinding

class HabitsListAdapter(
    private val onToggleToday: (Habit) -> Unit,
    private val onDelete: (Habit) -> Unit,
    private val onEdit: (Habit, String) -> Unit
) : ListAdapter<Habit, HabitsListAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem
    }

    inner class VH(val binding: ItemHabitBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.binding.textTitle.text = item.title
        holder.binding.checkboxToday.isChecked = item.completedDates.contains(com.example.habittracker.util.DateUtils.todayKey())
        holder.binding.checkboxToday.setOnClickListener { onToggleToday(item) }
        holder.binding.buttonDelete.setOnClickListener { onDelete(item) }
        holder.binding.buttonEdit.setOnClickListener {
            val newTitle = holder.binding.editTitle.text?.toString()?.trim().orEmpty()
            if (newTitle.isNotEmpty() && newTitle != item.title) {
                onEdit(item, newTitle)
            }
        }
    }
}


