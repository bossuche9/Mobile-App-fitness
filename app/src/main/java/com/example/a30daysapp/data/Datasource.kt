package com.example.a30daysapp.data

import com.example.a30daysapp.R
import com.example.a30daysapp.model.Exercise


class Datasource() {
    fun loadExercises(): List<Exercise> {
        return listOf<Exercise>(
            Exercise(R.string.exercise_1, R.drawable.exercise_1,R.string.day_1, R.string.exercise1_description),
            Exercise(R.string.exercise_2, R.drawable.exercise_2,R.string.day_2, R.string.exercise2_description),
            Exercise(R.string.exercise_3, R.drawable.exercise_3, R.string.day_3, R.string.exercise3_description)

        )
    }
}