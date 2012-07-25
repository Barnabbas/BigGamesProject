/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util

/**
 * A class that can be used for debugging.<br>
 * Will only print when isDebugging is set.
 */
class Debug(name: String) {
    
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
    
    private def isDebugging = Debug.isDebugging && Debug.debuggers.contains(name)
}

/**
 * An Utility class for inserting Debug code
 */
object Debug {
    
    var isDebugging = false
    
    /**
     * The debuggers that are allowed
     */
    var debuggers = List("Server", "GameLoader", "Renderer")
    
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
