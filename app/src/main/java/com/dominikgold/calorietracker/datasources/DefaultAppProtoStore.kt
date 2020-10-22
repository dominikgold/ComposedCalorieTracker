package com.dominikgold.calorietracker.datasources

import android.content.Context
import androidx.datastore.CorruptionException
import androidx.datastore.DataStore
import androidx.datastore.Serializer
import androidx.datastore.createDataStore
import com.dominikgold.calorietracker.datasources.models.ProtoCalorieGoal
import com.dominikgold.calorietracker.di.ApplicationContext
import com.dominikgold.calorietracker.entities.CalorieGoal
import com.dominikgold.calorietracker.entities.MacroSplit
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
        return calorieGoalStore.data.firstOrNull()?.toEntity()
    }

    override suspend fun saveCalorieGoal(calorieGoal: CalorieGoal) {
        calorieGoalStore.updateData {
            ProtoCalorieGoal.newBuilder()
                .setCalories(calorieGoal.totalCalories)
                .setMacroSplit(calorieGoal.macroSplit.toProtoModel())
                .build()
        }
    }

    private fun MacroSplit.toProtoModel() = when (this) {
        MacroSplit.HIGH_CARB -> ProtoCalorieGoal.ProtoMacroSplit.HIGH_CARB
        MacroSplit.BALANCED -> ProtoCalorieGoal.ProtoMacroSplit.BALANCED
        MacroSplit.HIGH_FAT -> ProtoCalorieGoal.ProtoMacroSplit.HIGH_FAT
        MacroSplit.VERY_HIGH_FAT -> ProtoCalorieGoal.ProtoMacroSplit.VERY_HIGH_FAT
    }

    private fun ProtoCalorieGoal.ProtoMacroSplit.toEntity() = when (this) {
        ProtoCalorieGoal.ProtoMacroSplit.HIGH_CARB -> MacroSplit.HIGH_CARB
        ProtoCalorieGoal.ProtoMacroSplit.BALANCED -> MacroSplit.BALANCED
        ProtoCalorieGoal.ProtoMacroSplit.HIGH_FAT -> MacroSplit.HIGH_FAT
        ProtoCalorieGoal.ProtoMacroSplit.VERY_HIGH_FAT -> MacroSplit.VERY_HIGH_FAT
        ProtoCalorieGoal.ProtoMacroSplit.UNRECOGNIZED -> MacroSplit.BALANCED
    }

    private fun ProtoCalorieGoal.toEntity() = CalorieGoal(this.calories, this.macroSplit.toEntity())

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
}