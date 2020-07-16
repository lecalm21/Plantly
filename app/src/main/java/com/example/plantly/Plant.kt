package com.example.plantly

data class Plant(val id: Int, val photoPath: String, val plantName: String, val daysTillWater: Int) {
    companion object {
        val TAG = "Plant"
    }
}
