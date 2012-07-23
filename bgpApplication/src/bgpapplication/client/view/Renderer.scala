/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.client.view

/**
 * A Renderer is used to display ViewObjects
 */
import bgpapi.view._

trait Renderer {
    
    /**
     * Displays {@code viewObject} with additional data from {@code viewEntity}
     */
    def display(viewObject: ViewObject, viewEntity: ViewEntity)
    
    /**
     * Tests whether this Renderer can display {@code viewObject}. This function
     * checks whether it has the required properties.
     */
    def test(viewObject: ViewObject) = true
    
    /**
     * Tests whether this Renderer can display {@code viewEntity}. This function
     * checks whether it has the required properties.
     */
    def test(viewEntity: ViewEntity) = true
    
    
    /**
     * Determines whether {@code viewObject} is defined for {@code properties}
     */
    protected def hasProperties(viewObject: ViewObject, properties: Property*) =
        properties.forall((p: Property) => viewObject(p).isDefined)
    /**
     * Determines whether {@code viewEntity} is defined for {@code properties}
     */
    protected def hasProperties(viewEntity: ViewEntity, properties: Property*) =
        properties.forall((p: Property) => viewEntity(p).isDefined)

}


object Renderer {
    
    val renderers = Map(ViewType.text -> TextRenderer)
    
    /**
     * Gets a Renderer that can render objects of a certain ViewType.
     * @param viewType the ViewType that should become rendered
     */
    def get(viewType: ViewType) = renderers(viewType)
    
    // the Renderers
    
    /**
     * Renders text
     */
    object TextRenderer extends Renderer {
        override def display(viewObject: ViewObject, viewEntity: ViewEntity) = {
            System.out.println(viewObject('text).get)
        }

        override def test(obj: ViewObject) =
            hasProperties(obj, 'text)
    }
    
}

