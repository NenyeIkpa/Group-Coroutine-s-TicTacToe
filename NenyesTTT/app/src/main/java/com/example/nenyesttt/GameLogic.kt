package com.example.nenyesttt

import android.view.View
import android.widget.Button
import android.widget.TextView

class GameLogic{
    private var player: Int = 1
    fun getPlayer() = player
    fun setPlayer(player:Int){
        this.player = player
    }

    private lateinit var playAgainBTN:Button
    fun getPlayAgainBTN() = player
    fun setPlayAgainBTN(playAgain:Button){
        this.playAgainBTN = playAgain
    }

    private lateinit var homeButton:Button
    fun getHomeBTN() = homeButton
    fun setHomeBTN(homeButton:Button){
        this.homeButton = homeButton
    }

    private lateinit var playerTurn:TextView
    fun getPlayerTurn() = playerTurn
    fun setPlayerTurn(playerTurn:TextView){
        this.playerTurn = playerTurn
    }

    private var playerName:Array<String> = arrayOf("Player 1","Player 2")
    fun getPlayerName() = playerName
    fun setPlayerName(playerName:Array<String>){
        this.playerName = playerName
    }

    private var  rowColArray:Array<IntArray> = Array(3){IntArray(3)}

    init {
        for(r in 0..2){
            for (c in 0..2){
                        rowColArray[r][c] = 0
                    }
                }
            }
    //first element is row, second element is col and third element is line type
    private var winType = arrayOf(-1,-1,-1)
    fun getWinType() = winType


  fun getRowColArray():Array<IntArray>{
      return rowColArray
  }
    fun updateGameBoard(row:Int,col:Int):Boolean{
        if (rowColArray[row-1][col-1] == 0){
            rowColArray[row-1][col-1] = player

            if (player == 1){
                "${playerName[1]}'s turn".also { playerTurn.text = it }
            }else{
                "${playerName[0]}'s turn".also { playerTurn.text = it }
            }
            return true
        }else{
            return false
        }
    }
    fun winnerCheck():Boolean{
        var isWinner = false
        for (r in 0..2){
            //horizontal check, win type 1
            if (
                rowColArray[r][0] == rowColArray[r][1] &&
                rowColArray[r][0] == rowColArray[r][2] &&
                rowColArray[r][0] != 0
            ){
                isWinner = true
                winType = arrayOf(r,0,1)
            }
        }
        //vertical check, win type 2
        for (c in 0..2){
            if (
                rowColArray[0][c] == rowColArray[1][c] &&
                rowColArray[0][c] == rowColArray[2][c] &&
                rowColArray[0][c] != 0
            ){
                isWinner = true
                winType = arrayOf(0,c,2)
            }
        }
        //negative diagonal check, win type 3
        if (
            rowColArray[0][0] == rowColArray[1][1] &&
            rowColArray[0][0] == rowColArray[2][2] &&
            rowColArray[0][0] != 0
        ){
            isWinner = true
            winType = arrayOf(0,2,3)
        }
        //positive diagonal check, win type 4
        if (
            rowColArray[2][0] == rowColArray[1][1] &&
            rowColArray[2][0] == rowColArray[0][2] &&
            rowColArray[2][0] != 0
        ){
            isWinner = true
            winType = arrayOf(2,2,4)
        }
        var boardCount = 0
        for(r in 0..2){
            for (c in 0..2){
               if (rowColArray[r][c] != 0) boardCount++
            }
        }

        when {
            isWinner -> {
                playAgainBTN.visibility = View.VISIBLE
                homeButton.visibility = View.VISIBLE
                "${playerName[player -1]} won!!!".also { playerTurn.text = it }
                return true
            }
            boardCount == 9 -> {
                playAgainBTN.visibility = View.VISIBLE
                homeButton.visibility = View.VISIBLE
                "Draw!!".also { playerTurn.text = it }
                return true
            }
            else -> {
                return false
            }
        }
    }
    fun resetGame(){
        for(r in 0..2){
            for (c in 0..2){
                rowColArray[r][c] = 0
            }
        }
        player = 1
        playAgainBTN.visibility = View.GONE
        homeButton.visibility = View.GONE
        "${playerName[0]}'s turn".also { playerTurn.text =it }
    }


}
