/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.game

import bgpapi.view.View
import java.util.Timer
import java.util.TimerTask

/**
 * A Game is an instance of a GameFactory that can be used to run 
 */
abstract class Game(view: View) {
    
    private val timer = new Timer("Game Timer")
    var lastTime: Long = 0
    
    // initializing code
    
    private val timerTask = new TimerTask{
        override def run = {
            val currentTime = System.currentTimeMillis
            val deltaTime = currentTime - lastTime
            
            update(deltaTime)
            
            lastTime = currentTime
        }
    }
    
    def start = {
        lastTime = System.currentTimeMillis
        timer.schedule(timerTask, Game.scheduleTime)
    }
    def stop = timer.cancel
    
    def update(time: Long): Unit

}

private object Game {
    val scheduleTime = 100
}

