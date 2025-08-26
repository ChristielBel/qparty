package com.example.qparty.viewmodel

import androidx.lifecycle.ViewModel
import com.example.qparty.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameSetupViewModel: ViewModel() {
    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players = _players.asStateFlow()

    fun setPlayers(count: Int) {
        _players.value = (1..count).map { Player("Игрок $it") }
    }

    fun rollDiceForPlayer(index: Int) {
        val newList = _players.value.toMutableList()
        newList[index] = newList[index].copy(diceRoll = (1..6).random())
        _players.value = newList
    }

    fun allRolled(): Boolean = _players.value.all { it.diceRoll > 0 }

    fun determineFirstPlayer(): Player? {
        return _players.value.maxByOrNull { it.diceRoll }
    }
}