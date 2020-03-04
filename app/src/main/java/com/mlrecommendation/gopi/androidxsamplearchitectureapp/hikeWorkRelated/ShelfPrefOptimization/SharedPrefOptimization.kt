package com.mlrecommendation.gopi.androidxsamplearchitectureapp.hikeWorkRelated.ShelfPrefOptimization

class SharedPrefOptimization {
    private val mModifiedMap: HashMap<String, Any?> = HashMap()
    init {
        mModifiedMap["aa"] = 1
        mModifiedMap["ab"] = "abValue"
        mModifiedMap["ac"] = 3.0
        mModifiedMap["ad"] = false
        mModifiedMap["ae"] = 3999L

        mModifiedMap["ba"] = null
        mModifiedMap["bb"] = null
        mModifiedMap["bc"] = null
        mModifiedMap["bd"] = null
        mModifiedMap["be"] = null
    }

    fun doCostlyOperation(typeText:String){
        println(" doing costly operation for ${typeText}")
    }

    fun ignore(typeText: String){
        println("Ignoring for ${typeText}")
    }

    fun saveData(key:String,  value: String?){
        val curentValue = mModifiedMap[key]
        if ((curentValue == null && value == null) || curentValue == value) {
            ignore(value ?: "nullStr")
        } else {
            doCostlyOperation(value ?: "nullStr")
            mModifiedMap[key] = value
        }
    }

    fun saveData(key:String,  value: Int?){
        val curentValue = mModifiedMap[key]
        if ((curentValue == null && value == null) || curentValue == value) {
            ignore(value?.toString() ?: "nullInt")
        } else {
            doCostlyOperation(value?.toString() ?: "nullInt")
            mModifiedMap[key] = value
        }
    }
    fun saveData(key:String,  value: Long?){

        val curentValue = mModifiedMap[key]
        if ((curentValue == null && value == null) || curentValue == value) {
            ignore(value?.toString() ?: "nullLong")
        } else {
            doCostlyOperation(value?.toString() ?: "nullLong")
            mModifiedMap[key] = value
        }
    }
    fun saveData(key:String,  value: Float?){
        val curentValue = mModifiedMap[key]
        if ((curentValue == null && value == null) || curentValue == value) {
            ignore(value?.toString() ?: "nullFloat")
        } else {
            doCostlyOperation(value?.toString() ?: "nullFloat")
            mModifiedMap[key] = value
        }
    }
    fun saveData(key:String,  value: Boolean?){
        val curentValue = mModifiedMap[key]
        if ((curentValue == null && value == null) || curentValue == value) {
            ignore(value?.toString() ?: "nullBoolean")
        } else {
            doCostlyOperation(value?.toString() ?: "nullBoolean")
            mModifiedMap[key] = value
        }
    }
}

fun main() {
    val obj = SharedPrefOptimization()

    obj.saveData("aa", 10L)
    obj.saveData("aa", (10 as? Int))
    obj.saveData("aa", (10 as? Int))
    obj.saveData("aa", "wrew")
    val nullStr: String? = null
    val nullInt: Int? = null
    obj.saveData("aa", "wrew")
    obj.saveData("aa", nullStr)
    obj.saveData("aa", nullInt)
    obj.saveData("aa", false)
    obj.saveData("aa", 10.9F)
    obj.saveData("aa", nullInt)

    println("****")

    val DEFAULT_INT_VALUE = Int.MIN_VALUE + 10
    val DEFAULT_LONG_VALUE = Long.MIN_VALUE + 10
    val DEFAULT_FLOAT_VALUE = Float.MIN_VALUE * 100

    println(" intValMin ${Int.MIN_VALUE} & intValueDefault is ${DEFAULT_INT_VALUE}")
    println(" longValMin ${Long.MIN_VALUE} & longValueDefault is ${DEFAULT_LONG_VALUE}")
    println(" floatValMin ${Float.MIN_VALUE} & floatValueDefault is ${DEFAULT_FLOAT_VALUE}")
}