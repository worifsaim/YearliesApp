package de.jeremiasloos.yearlies.presentation.add

data class AddState(
    val day: String = "",
    val month: String = "",
    val year: String = "",
    val name: String = "",
    val note: String = "",
    val isHintVisible: String = ""
)
