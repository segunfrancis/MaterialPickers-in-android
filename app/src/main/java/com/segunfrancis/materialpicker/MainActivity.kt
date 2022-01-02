package com.segunfrancis.materialpicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.util.Pair
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.segunfrancis.materialpicker.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Date Picker
        binding.dateField.setOnClickListener {
            val today = MaterialDatePicker.todayInUtcMilliseconds()
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = today
            calendar[Calendar.YEAR] = 1950
            val startDate = calendar.timeInMillis

            calendar.timeInMillis = today
            calendar[Calendar.YEAR] = 2003
            val endDate = calendar.timeInMillis

            val constraints: CalendarConstraints = CalendarConstraints.Builder()
                .setOpenAt(endDate)
                .setStart(startDate)
                .setEnd(endDate)
                .build()

            val datePickerBuilder: MaterialDatePicker.Builder<Long> = MaterialDatePicker
                .Builder
                .datePicker()
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .setTitleText("Select date of birth")
                .setCalendarConstraints(constraints)
            val datePicker = datePickerBuilder.build()
            datePicker.show(supportFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener {
                val date = sdf.format(it)
                binding.dateField.setText(date)
            }
        }

        // Time Picker
        binding.timeField.setOnClickListener {
            val timePickerBuilder: MaterialTimePicker.Builder = MaterialTimePicker
                .Builder()
                .setTitleText("Select a time")
                .setHour(2)
                .setMinute(30)
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            val timePicker = timePickerBuilder.build()
            timePicker.show(supportFragmentManager, "TIME_PICKER")

            timePicker.addOnPositiveButtonClickListener {
                binding.timeField.setText(
                    timeConverter(
                        hour = timePicker.hour,
                        minute = timePicker.minute
                    )
                )
            }
        }

        // Date Range Picker
        binding.dateStartRangeField.setOnClickListener {
            val constraints = CalendarConstraints.Builder()
                .setStart(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .setValidator(DateValidatorPointForward.now())
                .build()
            val datePickerBuilder: MaterialDatePicker.Builder<Pair<Long, Long>> = MaterialDatePicker
                .Builder
                .dateRangePicker()
                .setTitleText("Select a date")
                .setCalendarConstraints(constraints)
            val datePicker = datePickerBuilder.build()
            datePicker.show(supportFragmentManager, "DATE_PICKER_RANGE")

            datePicker.addOnPositiveButtonClickListener {
                val startDate = sdf.format(it.first)
                val endDate = sdf.format(it.second)
                binding.dateStartRangeField.setText(startDate)
                binding.dateEndRangeField.setText(endDate)
            }
        }

        // Previous Format Date Picker
        binding.oldFormatDateField.setOnClickListener {
            DatePickerFragment(onDateSet = {
                binding.oldFormatDateField.setText(it)
            }).show(supportFragmentManager, "OLD_DATE_PICKER")
        }
    }
}
