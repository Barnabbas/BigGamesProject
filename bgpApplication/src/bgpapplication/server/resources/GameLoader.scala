/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server.resources

/**
 * This object loads and creates 
 */
import bgpapi.game.Game
import bgpapi.game.GameFactory
import bgpapplication.util.Debug
import java.io.File
import java.net.URLClassLoader
import java.util.Scanner

object GameLoader {
    
    private val debug = Debug("GameLoader")
    
    /**
     * Creates a Game based on the Data and the Theme found in {@code resourceLoader}
     */
    def createGame(resourceLoader: ResourceLoader): Game = {
        val facData = resourceLoader.factory
        val file = new File(resourceLoader.directory, facData.fileName)
        val factory = load(file, facData.className)
        
        return factory.createGame(resourceLoader.theme)
    }
    
    /**
     * Loads the first GameFactory that is on {@code games.txt} inside {@code file}.
     * Requires {@code canLoad(file)} and that the GameFactories of {@code games.txt}
     * are real GameFactory classes.
     */
    private def load(file: File, className: String): GameFactory = {
        
        debug("loading file " + file)
        
        val classLoader = new URLClassLoader(Array(file.toURI.toURL))
        
        // get the GameFactory from an scala object
        // warning: strange copied code, I have no idea how this works
        def getObjectFactory(name: String)(implicit man: Manifest[GameFactory]) = {
            
            val clazz = Class.forName(name, true, classLoader)
            clazz.getField("MODULE$").get(man.erasure).asInstanceOf[GameFactory]
        }
        
        // try to get object class first
        val name = className
        debug("loading class " + name)
        
        if (name.endsWith("$")){ // this is an object class
            getObjectFactory(name)
        } else { // the Factory is a standalone class
            val clazz = Class.forName(name, true, classLoader)
            clazz.newInstance.asInstanceOf[GameFactory]
        }
    }

}
