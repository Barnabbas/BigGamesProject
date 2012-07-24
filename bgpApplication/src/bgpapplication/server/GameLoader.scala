/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server

/**
 * This object loads GameFactory from {@code .jar} files.<br>
 * To load GameFactories from a {@code .jar} files it is required that the 
 * {@code .jar} contains a text file named {@code games.txt} containing lines
 * with the name and location of the classes where the GameFactories are.<br>
 * At The moment this will only load the first GameFactory that is on the list.
 */
import bgpapi.game.GameFactory
import java.io.File
import java.net.URLClassLoader
import java.util.Scanner

object GameLoader {
    
    /**
     * Determines whether {@code file} can be loaded to get GameFactories from.
     */
    def canLoad(file: File): Boolean = {
        if (!file.getName.endsWith(".jar")) false
        else {
            val classLoader = new URLClassLoader(Array(file.toURI.toURL))
            val stream = classLoader.getResourceAsStream("games.txt")
            return stream != null
        }
    }
    
    /**
     * Loads the first GameFactory that is on {@code games.txt} inside {@code file}.
     * Requires {@code canLoad(file)} and that the GameFactories of {@code games.txt}
     * are real GameFactory classes.
     */
    def load(file: File): GameFactory = {
        require(canLoad(file), "Can not load File: " + file)
        
        val classLoader = new URLClassLoader(Array(file.toURI.toURL))
        
        val names = getNames(classLoader)
        require(names.size >= 1, "games.txt is empty.") 
        
        // get the GameFactory from an scala object
        def getObjectFactory(name: String)(implicit man: Manifest[GameFactory]) = {
            val clazz = Class.forName(name, true, classLoader)
            clazz.getField("MODULE$").get(man.erasure).asInstanceOf[GameFactory]
        }
        
        // try to get object class first
        val name = names(0)
        if (name.endsWith("$")){ // this is an object class
            getObjectFactory(name)
        } else { // the Factory is a standalone class
            val clazz = Class.forName(names(0), true, classLoader)
            clazz.newInstance.asInstanceOf[GameFactory]
        }
    }
    
    
    /**
     * Get the all the names of the games.txt file
     */
    private def getNames(loader: URLClassLoader) = {
        val stream = loader.getResourceAsStream("games.txt")
        val scanner = new Scanner(stream)
        var names = List.empty[String]
        while (scanner.hasNext){
            names ::= scanner.nextLine
        }
        names
    }

}
