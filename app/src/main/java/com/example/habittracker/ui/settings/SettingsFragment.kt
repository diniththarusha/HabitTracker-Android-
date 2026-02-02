package com.example.habittracker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.habittracker.data.PrefsStorage
import com.example.habittracker.databinding.FragmentSettingsBinding
import com.example.habittracker.work.HydrationWorker
import java.util.concurrent.TimeUnit

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var storage: PrefsStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storage = PrefsStorage(requireContext())

        val current = storage.getHydrationIntervalMinutes()
        binding.numberPicker.minValue = 1
        binding.numberPicker.maxValue = 480
        binding.numberPicker.value = current
        binding.numberPicker.wrapSelectorWheel = false

        binding.buttonSaveInterval.setOnClickListener {
            val minutes = binding.numberPicker.value
            storage.setHydrationIntervalMinutes(minutes)
            scheduleHydrationWorker(minutes)
        }
    }

    private fun scheduleHydrationWorker(minutes: Int) {
        val request = PeriodicWorkRequestBuilder<HydrationWorker>(minutes.toLong(), TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            HydrationWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


