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
abstract class Game(objects: List[ViewObject]) {
    
    /**
     * The ViewObjects this Game uses
     */
    val viewObjects = objects
    
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
    
    /**
     * The view of this Game. Will be filled in at {@link #start}
     */
    private var viewOption = Option.empty[View]
    
    def start(view: View) = {
        viewOption = Option(view)
        lastTime = System.currentTimeMillis
        timer.schedule(timerTask, Game.scheduleTime)
    }
    def stop() = timer.cancel
    
    /**
     * Will be called when this Game is started.
     */
    def init(): Unit
    /**
     * Will regulary be called and should be used to update the View and the 
     * game logic.
     */
    def update(time: Long): Unit
    
    /**
     * Gets the View of this Game.<br>
     * The Game must be started before you can get the View.
     * @throws IllegalStateException if this Game is not started yet
     */
    protected def view = {
        if (viewOption.isEmpty){
            throw new IllegalStateException("The Game must be started before " +
                                            "you can get the view")
        } else {
            viewOption.get
        }
    }

}

private object Game {
    val scheduleTime = 100
}

