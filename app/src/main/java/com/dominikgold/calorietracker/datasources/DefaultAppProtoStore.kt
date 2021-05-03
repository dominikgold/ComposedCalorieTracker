package com.dominikgold.calorietracker.datasources

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.dominikgold.calorietracker.datasources.models.ProtoCalorieGoal
import com.dominikgold.calorietracker.di.ApplicationContext
import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.entities.MacroGoals
import com.dominikgold.calorietracker.repositories.AppProtoStore
import kotlinx.coroutines.flow.firstOrNull
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

private val Context.calorieGoalStore by dataStore(
    fileName = "calorie_tracker_data_store",
    serializer = ProtoCalorieGoalSerializer,
)

@Singleton
class DefaultAppProtoStore @Inject constructor(@ApplicationContext private val context: Context) : AppProtoStore {

    override suspend fun getCalorieGoal(): CalorieGoal? {
        return context.calorieGoalStore.data.firstOrNull()?.takeIf { it != ProtoCalorieGoal.getDefaultInstance() }?.toEntity()
    }

    override suspend fun saveCalorieGoal(calorieGoal: CalorieGoal) {
        context.calorieGoalStore.updateData {
            ProtoCalorieGoal.newBuilder().setCalories(calorieGoal.totalCalories)
                .setCarbohydrates(calorieGoal.macroGoals.carbohydrates ?: -1)
                .setProtein(calorieGoal.macroGoals.protein ?: -1).setFat(calorieGoal.macroGoals.fat ?: -1).build()
        }
    }

    private fun ProtoCalorieGoal.toEntity() = CalorieGoal(
        totalCalories = this.calories,
        macroGoals = MacroGoals(
            carbohydrates = this.carbohydrates.takeIf { it > 0 },
            protein = this.protein.takeIf { it > 0 },
            fat = this.fat.takeIf { it > 0 },
        ),
    )

}

object ProtoCalorieGoalSerializer : Serializer<ProtoCalorieGoal> {

    override suspend fun readFrom(input: InputStream): ProtoCalorieGoal {
        try {
            return ProtoCalorieGoal.parseFrom(input)
        } catch (exception: Exception) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: ProtoCalorieGoal,
        output: OutputStream,
    ) = t.writeTo(output)

    override val defaultValue: ProtoCalorieGoal
        get() = ProtoCalorieGoal.getDefaultInstance()
}