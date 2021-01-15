package com.dominikgold.calorietracker.datasources

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.createDataStore
import com.dominikgold.calorietracker.datasources.models.ProtoCalorieGoal
import com.dominikgold.calorietracker.di.ApplicationContext
import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.repositories.AppProtoStore
import kotlinx.coroutines.flow.firstOrNull
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAppProtoStore @Inject constructor(@ApplicationContext context: Context) : AppProtoStore {

    private val calorieGoalStore: DataStore<ProtoCalorieGoal> =
        context.createDataStore(fileName = "calorie_tracker_data_store", serializer = ProtoCalorieGoalSerializer)

    override suspend fun getCalorieGoal(): CalorieGoal? {
        return calorieGoalStore.data.firstOrNull()?.takeIf { it != ProtoCalorieGoal.getDefaultInstance() }?.toEntity()
    }

    override suspend fun saveCalorieGoal(calorieGoal: CalorieGoal) {
        calorieGoalStore.updateData {
            ProtoCalorieGoal.newBuilder()
                .setCalories(calorieGoal.totalCalories)
                .setCarbohydrates(calorieGoal.carbohydrates ?: -1)
                .setProtein(calorieGoal.protein ?: -1)
                .setFat(calorieGoal.fat ?: -1)
                .build()
        }
    }

    private fun ProtoCalorieGoal.toEntity() = CalorieGoal(
        totalCalories = this.calories,
        carbohydrates = this.carbohydrates.takeIf { it > 0 },
        protein = this.protein.takeIf { it > 0 },
        fat = this.fat.takeIf { it > 0 },
    )

}

object ProtoCalorieGoalSerializer : Serializer<ProtoCalorieGoal> {

    override fun readFrom(input: InputStream): ProtoCalorieGoal {
        try {
            return ProtoCalorieGoal.parseFrom(input)
        } catch (exception: Exception) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(
        t: ProtoCalorieGoal,
        output: OutputStream,
    ) = t.writeTo(output)

    override val defaultValue: ProtoCalorieGoal
        get() = ProtoCalorieGoal.getDefaultInstance()
}