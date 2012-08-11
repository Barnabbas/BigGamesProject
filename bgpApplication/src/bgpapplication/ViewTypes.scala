/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication

/**
 * The ViewTypes that are used for this Application. Both for the server and the
 * Client
 */
import bgpapi.view.ViewType

object ViewTypes {
    
    val text = new ViewType('text, List('text))
    
    val types = Map('text -> text)
    
    def apply(name: Symbol) = types(name)
    def apply(name: String): ViewType = apply(Symbol(name))

}
