/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.test

import bgpapi.game._
import bgpapi.view._

private class TestGame(vObjects: List[ViewObject]) extends Game(vObjects) {
    override def init(){
        println("init")
    }
    
    override def update(time: Long){
        println("update:" + time)
    }
}

object TestGame extends GameFactory{
    override val identifier = "Barnabbas.test"
    
    val helloWorld = new ViewObject{
        override val viewType = ViewType.text
        override val identifier = "Barnabbas.test.helloWorld"
        override val variables = Set.empty[Property]
        override def apply(prop: Property) = {
            Option("Hello World!")
        }
    }
    
    override def createGame: Game = {
        new TestGame(List(helloWorld))
    }
}
