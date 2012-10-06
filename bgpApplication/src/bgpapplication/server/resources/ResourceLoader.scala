/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server.resources

/**
 * Used to get Resources by the identifiers.<br>
 * To get Resources first a GameTheme must be loaded, this will load all Resources
 * into this.
 */
import bgpapi.Resource
import bgpapi.game.GameTheme
import java.io.File
import scala.xml.XML

/**
 * Creates a new ResourceLoader that will load the files for the GameTheme 
 * File {@code file}.<br>
 * It is required that {@code ResourceLoader.canLoad(file)}.
 * 
 * @param file a xml-file containing information for the GameTheme
 */
class ResourceLoader(file: File) {
    
    require(ResourceLoader.canLoad(file), "Can not load " + file + " for a " +
            "GameTheme")
    
    /**
     * The directory this ResourceLoader is getting his Resources from.
     */
    private[resources] val directory = file.getParentFile
    
    /**
     * The XML node of the theme
     */
    private val themeNode = XML.loadFile(file)
    
    /**
     * The reader to get the data from
     */
    private val reader: DataReader = new XMLReader(directory)
    
    
    
    /**
     * The GameTheme this ResourceLoader loads.
     */
    val theme: GameTheme = {
        val id = (themeNode \ "@id").text
        reader.get[GameTheme](id)
    }
    
    /**
     * The FactoryData of the GameTheme
     */
    private[resources] val factory = {
        val id = (themeNode \ "@game").text
        reader.get[FactoryData](id)
    }
    
    /**
     * Gets all the Resources used in {@code theme} in order such that for all
     * Resources in the list all the requirements of that Resource are before them
     * in the List. The Theme self is not included in the requiredResources
     */
    def requiredResources: List[Resource] = {
        var list = List.empty[Resource]
        
        def scan(res: Resource): Unit = {
            list ::= res
            for (r <- res.requirements){
                scan(r)
            }
        }
        
        scan(theme)
        
        // dropping the theme (has to be the last one, since it is added first)
        list = list.dropRight(1)
        
        // removing all duplicate items, such that the first appearance of it stays
        return list.distinct
    }

}

object ResourceLoader {
    
    /**
     * Determines whether {@code file} can be used for this ResourceLoader.
     */
    def canLoad(file: File): Boolean = 
        file.getName.endsWith(".xml") && XML.loadFile(file).label == "gametheme"
    
}

/**
 * A small class containing the Data needed to load a Game.
 */
private[resources] class FactoryData(override val identifier: String,
                                val fileName: String, val className: String,
                                val settings: List[String]) extends Resource{

    override def requirements = Set.empty
    
}
