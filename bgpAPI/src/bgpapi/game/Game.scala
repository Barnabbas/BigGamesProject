/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapi.game

import bgpapi.view.View
import bgpapi.view.ViewObject
import java.util.Timer
import java.util.TimerTask

/**
 * A Game is an instance of a GameFactory that can be used to run 
 * @param view the View to use
 * @param objects the ViewObject to use
 */
abstract class Game(view: View, objects: ViewObject*) {
    
    /**
     * The ViewObjects this Game uses
     */
    val viewObjects = List(objects)
    
    private val timer = new Timer("Game Timer")
    private var lastTime: Long = 0
    
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

