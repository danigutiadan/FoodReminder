package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import java.io.Serializable
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date

@Entity(
    tableName = "food",
    foreignKeys = [ForeignKey(
        entity = FoodType::class,
        parentColumns = ["id"],
        childColumns = ["food_type_Id"]
    )]
)
data class Food(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "food_id")
    var id: Int? = null,

    @ColumnInfo(name = "food_name", index = true)
    val name: String,

    @ColumnInfo(name = "food_quantity")
    val quantity: Int,

    @ColumnInfo(name = "food_expiry_date")
    val expiryDate: Date,

    @ColumnInfo(name = "food_days_before_expiration_notification")
    val daysBeforeExpirationNotification: Int,

    @ColumnInfo(name = "food_type_Id", index = true)
    val foodType: Int,

    @ColumnInfo(name = "food_image_url")
    val foodImageUrl: String? = null,

    @ColumnInfo(name = "food_status")
    var foodStatus: FoodStatus? = null,

    @ColumnInfo(name = "food_status_int")
    var foodStatusInt: Int? = null

) : Serializable {

    init {
        val expiryDateCalendar = Calendar.getInstance().apply {
            time = expiryDate
        }

        val todayCalendar = Calendar.getInstance()
        todayCalendar.set(
            todayCalendar.get(Calendar.YEAR),
            todayCalendar.get(Calendar.MONTH),
            todayCalendar.get(Calendar.DAY_OF_MONTH),
            expiryDateCalendar.get(Calendar.HOUR_OF_DAY),
            expiryDateCalendar.get(Calendar.MINUTE),
            expiryDateCalendar.get(Calendar.SECOND),


            )

        val expiryDateInstant =
            expiryDateCalendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val todayDateInstant =
            todayCalendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        val daysBeforeExpired = ChronoUnit.DAYS.between(todayDateInstant, expiryDateInstant)
        foodStatus = when {
            daysBeforeExpired >= 10L -> FoodStatus.FRESH
            daysBeforeExpired in 5L..9L -> FoodStatus.ABOUT_TO_EXPIRE
            else -> FoodStatus.ALMOST_EXPIRED
        }

        foodStatusInt = foodStatus?.value
    }
}

data class FoodInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @Embedded
    val food: Food,
    @Relation(parentColumn = "food_type_Id", entityColumn = "id")
    val foodType: FoodType
) : Serializable

enum class FoodStatus(val value: Int) {
    FRESH(1),
    ABOUT_TO_EXPIRE(2),
    ALMOST_EXPIRED(3)
}

enum class FoodOrder {
    FOOD_STATUS_ASC,
    FOOD_STATUS_DESC
}