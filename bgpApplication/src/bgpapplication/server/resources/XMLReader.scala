/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server.resources

/**
 * Reads XML-files and create Resources from it, such that they can be used
 * for the ResourceLoader.
 */


import bgpapi.Resource
import bgpapi.game.GameTheme
import bgpapi.view.Property
import bgpapi.view.PropertyValue
import bgpapi.view.ViewDefinition
import bgpapi.view.ViewObject
import bgpapplication.ViewTypes
import bgpapplication.util.Debug
import bgpapplication.util.implementations._
import java.io.File
import java.io.FilenameFilter
import scala.xml._
import scala.xml.XML._
import XMLReader._
import DataReader.FormatException

/**
 * An XMLReader that can read all Resources that can be found in the xml-find
 * that are located in the Directory that is denother by {@code directory} 
 */
private[resources] class XMLReader(directory: File) extends DataReader {
    
    /**
     * A map between the keys and the xml-nodes.
     */
    private val nodes = nodeMap
    /**
     * The Resources that are already loaded.
     */
    private var loadedResources = Map.empty[String, Resource]
    
    /**
     * Gets an Object based on id
     */
    override def get[T <: Resource](id: String): T = {
        debug("getting " + id)
        if (loadedResources contains id){
            val res = loadedResources(id)
            // todo: add something like T.toString
            require(res.isInstanceOf[T], "The resource \"" + id + "\" is not of the requested type")
            res.asInstanceOf[T]
        } else {
            require(nodes contains id, "There is no Resource with id \"" + id + '\"')
            val res = get[T](nodes(id))
            
            loadedResources += (id -> res)
            res
        }
    }
    
    private def get[T <: Resource](node: Node): T = {
        val res: Resource = node.label match{
            case "link" => getLink[T](node)
            case "viewobject" => getViewObject(node)
            case "viewdefinition" => getViewDefinition(node)
            case "game" => getFactoryData(node)
            case "gametheme" => getGameTheme(node)
            case s => throw new FormatException("unknow type found: " + s
                                                + "\n at " + node)
        }
        
        if (res.isInstanceOf[T]) res.asInstanceOf[T]
        else throw new FormatException("Can not be casted to the requested type:" +
                                       "\n" + node)
    }
    
    private def getLink[T <: Resource](node: Node): T = {
        assert(node.label == "link", "Node should be of type Link")
        return get[T](node.id)
    }
    
    private def getViewObject(node: NodeSeq): ViewObject = {
        val id = node.id
        
        // todo: remove debug
        debug("ViewObject: definition = " + (node \@ "definition") + " in node: \n" + node)
        
        val viewDef = get[ViewDefinition](node \@ "definition")
        val vType = ViewTypes(node \ "type" text)
        
        /**
         * Gets a PropertyValue from a node
         */
        def getPropertyValue(node: NodeSeq): PropertyValue = {
            val variable = node \ "variable"
                
            if (!variable.isEmpty){ // there is a variable
                // the property of the variable
                val prop = Property(variable \@ "var")
                formatRequire(viewDef.variables(prop), 
                              prop + " is not defined in the viewDefinition " + viewDef.identifier,
                              node)
                return PropertyValue.Variable(prop)
            } else { // there is no variable (just take raw data)
                return PropertyValue.Data(node.text)
            }
        }
            
        val values = (for (prop <- vType.properties) yield{
                val propNode = node \ (prop.stringValue)
                require(!propNode.isEmpty, "No value found for " + prop)
                (prop -> getPropertyValue(propNode))
            }).toMap[Property, PropertyValue]
        
        debug("Getting the following values: " + values);
        debug("From: " + node)
        
        return new SimpleViewObject(id, viewDef, vType, values)
    }
    
    private def getViewDefinition(node: Node): ViewDefinition = {
        val id = node.id
        val variables = (node \ "variable").map(v => Property(v.id)).toSet
        return new SimpleViewDefinition(id, variables)
    }
    
    private def getFactoryData(node: Node): FactoryData = {
        val id = node.id
        
        val factoryNode = node \ "factory"
        val file = factoryNode \@ "file"
        val clazz = factoryNode \@ "class"
        
        val settings = (for (setting <- node \ "setting") yield {
                setting.id
            }).toList
    
        return new FactoryData(id, file, clazz, settings)
    }
    
    private def getGameTheme(node: Node): GameTheme = {
        val id = node.id
        val factory = get[FactoryData](node \@ "game")
        val values = for (setting <- factory.settings) yield {
            (setting -> get[ViewObject](node.\(setting).child1))
        }
        return new SimpleGameTheme(id, values.toMap)
    }
    
    
    
    
    /**
     * Gets a map for all xml-elements, to be accessed by their ids.
     */
    private def nodeMap: Map[String, Node] = {
        val fileFilter = new FilenameFilter(){
            override def accept(dir: File, name: String) = name.endsWith(".xml")
        }
        val xmlFiles = directory.listFiles(fileFilter)
        
        var map = Map.empty[String, Node]
        for (file <- xmlFiles){
            debug("Loading now: " + file)
            val xml = loadFile(file)
            
            def add(xml: Node) = {
                val id = xml.id
                formatRequire(!map.contains(id), "This id already exists", xml)
                
                map += (id -> xml)
                debug("adding " + id + " with xml: \n" + xml)
            }
            
            // loading the data
            val tags = List("viewobject", "viewdefinition", "game", "gametheme")
            for (tag <- tags){
                (xml \\ tag).foreach(node => add(node))
            }
        }
        
        debug("The map for the nodes contains now: " + map.keySet)
        return map
    }
    
}

private object XMLReader{
    
    val debug = Debug("XMLReader")
    
    def formatRequire(bool: Boolean, message: String, node: NodeSeq){
        if (!bool){
            throw new DataReader.FormatException(message + "\n" + node)
        }
    }
    
    implicit def nodeToRichNode(node: NodeSeq): RichNode = new RichNode(node)
}

/**
 * My special node class that I use to get eassier methods.
 */
private class RichNode(node: NodeSeq){
    
    /**
     * The attribute of this node with the key {@code key}
     */
    def \@ (key: String) = {
        node \ ("@" + key) text
    }
    
    /**
     * Returns the Sequence as a node.<br>
     * This requires that this Sequence is build from only one node
     */
    def toNode = {
        formatRequire(node.length == 1, "There should only be one node here", 
                      node)
        node(0)
    }
    
    /**
     * The id of this Node.
     */
    def id = \@("id")
    
    /**
     * Gets the only child of this Node.<br>
     * This requires that this is a Node with only one child.
     */
    def child1 = {
        val children = toNode.child filterNot (c => c.isInstanceOf[Text])
        formatRequire(children.length == 1, "Only one child is allowed here", node)
        children(0)
    }
    
}
