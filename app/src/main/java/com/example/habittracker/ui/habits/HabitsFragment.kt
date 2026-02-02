package com.example.habittracker.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.data.PrefsStorage
import com.example.habittracker.data.model.Habit
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.util.DateUtils
import com.example.habittracker.widget.HabitWidgetProvider
import java.util.UUID

class HabitsFragment : Fragment() {

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!

    private lateinit var storage: PrefsStorage
    private lateinit var adapter: HabitsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storage = PrefsStorage(requireContext())

        adapter = HabitsListAdapter(
            onToggleToday = { habit ->
                val habits = storage.getHabits()
                val idx = habits.indexOfFirst { it.id == habit.id }
                if (idx >= 0) {
                    val today = DateUtils.todayKey()
                    val updated = habits[idx].copy(completedDates = habits[idx].completedDates.toMutableSet().apply {
                        if (contains(today)) remove(today) else add(today)
                    })
                    habits[idx] = updated
                    storage.saveHabits(habits)
                    refresh()
                    HabitWidgetProvider.sendRefreshBroadcast(requireContext())
                }
            },
            onDelete = { habit ->
                val habits = storage.getHabits().filter { it.id != habit.id }
                storage.saveHabits(habits)
                refresh()
                HabitWidgetProvider.sendRefreshBroadcast(requireContext())
            },
            onEdit = { habit, newTitle ->
                val habits = storage.getHabits()
                val idx = habits.indexOfFirst { it.id == habit.id }
                if (idx >= 0) {
                    habits[idx] = habits[idx].copy(title = newTitle)
                    storage.saveHabits(habits)
                    refresh()
                    HabitWidgetProvider.sendRefreshBroadcast(requireContext())
                }
            }
        )

        binding.recyclerHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerHabits.adapter = adapter

        binding.buttonAddHabit.setOnClickListener {
            val title = binding.inputHabitTitle.text?.toString()?.trim().orEmpty()
            if (title.isNotEmpty()) {
                val habits = storage.getHabits()
                habits.add(Habit(id = UUID.randomUUID().toString(), title = title))
                storage.saveHabits(habits)
                binding.inputHabitTitle.setText("")
                refresh()
                HabitWidgetProvider.sendRefreshBroadcast(requireContext())
            }
        }

        refresh()
    }

    private fun refresh() {
        val habits = storage.getHabits()
        adapter.submitList(habits)
        val today = DateUtils.todayKey()
        val total = habits.size.coerceAtLeast(1)
        val completed = habits.count { it.completedDates.contains(today) }
        val percent = (completed * 100) / total
        binding.textProgress.text = "$percent% today"
        binding.progressBar.progress = percent
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


