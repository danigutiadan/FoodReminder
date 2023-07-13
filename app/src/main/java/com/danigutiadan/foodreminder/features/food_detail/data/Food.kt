package com.danigutiadan.foodreminder.features.food_detail.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
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

    @ColumnInfo(name = "food_image", typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray? = null,

    var foodStatus: FoodStatus? = null

) {

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
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Food

        if (id != other.id) return false
        if (name != other.name) return false
        if (quantity != other.quantity) return false
        if (expiryDate != other.expiryDate) return false
        if (daysBeforeExpirationNotification != other.daysBeforeExpirationNotification) return false
        if (foodType != other.foodType) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (foodStatus != other.foodStatus) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + quantity
        result = 31 * result + expiryDate.hashCode()
        result = 31 * result + daysBeforeExpirationNotification
        result = 31 * result + foodType
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + (foodStatus?.hashCode() ?: 0)
        return result
    }
}

data class FoodWithFoodType(
    @Embedded
    val food: Food,
    @Relation(parentColumn = "food_type_Id", entityColumn = "id")
    val foodType: FoodType
)

enum class FoodStatus(val value: Int) {
    FRESH(1),
    ABOUT_TO_EXPIRE(2),
    ALMOST_EXPIRED(3)
}