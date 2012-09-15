/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.view

/**
 * A Renderer is used to display ViewObjects
 */
import bgpapi.view._
import bgpapplication.util.Debug

trait Renderer {
    
    /**
     * Displays this Renderer with additional data from {@code viewEntity}
     */
    def display(viewEntity: ViewEntity)
    
    /**
     * Tests whether this Renderer can display {@code viewEntity}. This function
     * checks whether it has the required properties.
     */
    def test(viewEntity: ViewEntity) = true
    
}


object Renderer {
    
    private val debug = Debug("Renderer")
    
    private val renderers = Map(new ViewType('text, List('text)) -> TextRenderer)
    debug("Renderers for: " + renderers.keySet)
    
    /**
     * Creates a Renderer for {@code viewObject}.
     * @param viewObject the ViewObject to get a Renderer of.
     */
    def apply(viewObject: ViewObject) = {
        val vType = viewObject.viewType
        renderers(vType).apply(viewObject)
    }
    
    /**
     * Test whether {@code viewObject} can be used to create a Renderer
     */
    def test(viewObject: ViewObject): Boolean = {
        val vType = viewObject.viewType
        renderers(vType).test(viewObject)
    }
    
    
    /**
     * Determines whether {@code viewEntity} is defined for the variable of {@code viewObject}
     */
    private def hasProperties(viewEntity: ViewEntity, viewObject: ViewObject) = 
        viewObject.definition.variables.forall(p => viewEntity(p).isDefined)
    
    
    /**
     * Determines whether {@code viewObject} is defined for {@code properties}
     */
    private def hasProperties(viewObject: ViewObject, properties: Property*) =
        properties.forall((p: Property) => viewObject(p).isDefined)
    
    /**
     * Returns the value of property {@code property}. Based on the ViewObject and
     * ViewEntity.
     */
    private def value(viewObject: ViewObject, viewEntity: ViewEntity, property: Property) = {
        import PropertyValue._
        viewObject(property).get match {
            case Data(data) => data
            case Variable(variable) => viewEntity(variable).get
        }
    }
    
    
    /* --- RendererFactory stuff  --- */
    
    private trait RendererFactory{
        def apply(obj: ViewObject): Renderer
        def test(obj: ViewObject): Boolean
    }
    
    /**
     * A Factory that will create Renderer to render text
     */
    private object TextRenderer extends RendererFactory{
        def apply(obj: ViewObject) = new Renderer(){
            override def display(entity: ViewEntity) = 
                println(value(obj, entity, 'text))
            override def test(entity: ViewEntity)= hasProperties(entity, obj)
        }
        def test(obj: ViewObject) = hasProperties(obj, 'text)
    }
    
}

