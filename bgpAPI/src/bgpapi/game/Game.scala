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
 * @param view the View to use
 * @param theme GameTheme
 */
abstract class Game(val theme: GameTheme) {
    
    private val timer = new Timer("Game Timer")
    private var lastTime: Long = 0
    /**
     * The view of this Game. Will be filled in at {@link #start}
     */
    private var view: View = _
    
    // initializing code
    
    private val timerTask = new TimerTask{
        override def run = {
            assume(view != null, "The TimerTask is running before the Game is started")
            val currentTime = System.currentTimeMillis
            val deltaTime = currentTime - lastTime
            
            update(deltaTime, view)
            
            lastTime = currentTime
        }
    }
    
    
    def start(view: View) = {
        this.view = view
        lastTime = System.currentTimeMillis
        timer.scheduleAtFixedRate(timerTask, Game.scheduleTime, Game.scheduleTime)
        init(view)
    }
    def stop() = timer.cancel
    
    /**
     * Will be called when this Game is started.
     * @param view the View that is used to draw in
     */
    def init(view: View): Unit
    /**
     * Will regulary be called and should be used to update the View and the 
     * game logic.
     * @param time the time that elapsed since last update. In Miliseconds.
     * @param view the View that is used to draw in
     */
    def update(time: Long, view: View): Unit

}

private object Game {
    val scheduleTime = 100
}

