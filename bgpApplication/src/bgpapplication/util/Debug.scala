/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util

/**
 * A class that can be used for debugging.<br>
 * Will only print when isDebugging is set.
 */
import scala.collection.GenSet

class Debug private(private val name: String) {
    
    def debug(body: => Unit) = {
        if (isDebugging){
            body
        }
    }
    
    def print(line: => String) = {
        if (isDebugging){
            println(name + ": " + line)
        }
    }
    
    def apply(line: => String) = print(line)
    
    private def isDebugging = Debug.isDebugging && Debug.active.contains(name)
}

/**
 * An Utility class for inserting Debug code
 */
object Debug {
    
    var isDebugging = false
    
    
    /**
     * All the Debuggers
     */
    private var debuggers = List.empty[Debug]
    
    def apply(name: String) = {
        val debug = new Debug(name)
        debuggers ::= debug
        debug
    }
    
    /**
     * The debuggers that are active
     */
    var active = List.empty[String]
    
    /**
     * The debuggers that are not active
     */
    def passive = debuggers.map(d => d.name) diff active
    
    
    /**
     * Will run {@code body} when {@code isDebuggin}
     */
    def debug(body: => Unit) = {
        if (isDebugging) body
    }
    
    /**
     * Will print {@code body} when {@code isDebuggin}
     */
    def print(line: => String) ={
        if (isDebugging) println(line)
    }
}
