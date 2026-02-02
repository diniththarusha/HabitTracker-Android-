package com.example.habittracker.ui.mood

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker.data.PrefsStorage
import com.example.habittracker.data.model.MoodEntry
import com.example.habittracker.databinding.FragmentMoodBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.Calendar
import java.util.UUID

class MoodFragment : Fragment() {
    private var _binding: FragmentMoodBinding? = null
    private val binding get() = _binding!!

    private lateinit var storage: PrefsStorage
    private lateinit var adapter: MoodListAdapter
    private var selectedEmoji: String = "🙂"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storage = PrefsStorage(requireContext())

        setupEmojiPicker()

        adapter = MoodListAdapter()
        binding.recyclerMoods.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMoods.adapter = adapter

        binding.buttonLogMood.setOnClickListener {
            val note = binding.inputNote.text?.toString().orEmpty()
            val moods = storage.getMoods()
            moods.add(
                MoodEntry(
                    id = UUID.randomUUID().toString(),
                    timestampMillis = System.currentTimeMillis(),
                    emoji = selectedEmoji,
                    note = note
                )
            )
            storage.saveMoods(moods)
            binding.inputNote.setText("")
            refresh()
        }

        binding.buttonShareSummary.setOnClickListener {
            val summary = buildWeeklySummary()
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, summary)
            }
            startActivity(Intent.createChooser(intent, "Share mood summary"))
        }

        refresh()
    }

    private fun setupEmojiPicker() {
        val emojis = listOf("😀","🙂","😐","🙁","😢","😡","😴","😌","🤒","🤗")
        binding.emojiContainer.removeAllViews()
        emojis.forEach { emoji ->
            val tv = TextView(requireContext())
            tv.text = emoji
            tv.textSize = 24f
            tv.setPadding(16)
            tv.setOnClickListener {
                selectedEmoji = emoji
            }
            binding.emojiContainer.addView(tv)
        }
    }

    private fun refresh() {
        val moods = storage.getMoods().sortedByDescending { it.timestampMillis }
        adapter.submitList(moods)
        updateChart()
    }

    private fun updateChart() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -6)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val start = cal.timeInMillis

        val moods = storage.getMoods()
        val dayToScore = mutableMapOf<Long, Float>()
        for (i in 0..6) {
            val day = start + i * 24 * 60 * 60 * 1000L
            dayToScore[day] = 0f
        }
        moods.forEach { m ->
            val day = ((m.timestampMillis - start) / (24 * 60 * 60 * 1000L)).coerceIn(0, 6)
            val key = start + day * 24 * 60 * 60 * 1000L
            dayToScore[key] = (dayToScore[key] ?: 0f) + emojiScore(m.emoji)
        }
        val entries = dayToScore.toSortedMap().entries.mapIndexed { idx, e -> Entry(idx.toFloat(), e.value) }
        val dataSet = LineDataSet(entries, "Mood Score").apply {
            setDrawCircles(true)
            lineWidth = 2f
        }
        binding.chartMood.data = LineData(dataSet)
        binding.chartMood.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chartMood.axisRight.isEnabled = false
        binding.chartMood.description.isEnabled = false
        binding.chartMood.invalidate()
    }

    private fun emojiScore(emoji: String): Float {
        return when (emoji) {
            "😀" -> 5f
            "🙂" -> 4f
            "😐" -> 3f
            "🙁" -> 2f
            "😢" -> 1f
            else -> 3f
        }
    }

    private fun buildWeeklySummary(): String {
        val moods = storage.getMoods().sortedByDescending { it.timestampMillis }.take(7)
        val lines = moods.map { "${it.emoji} - ${it.note}".trim() }
        return "My week in moods:\n" + lines.joinToString("\n")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


