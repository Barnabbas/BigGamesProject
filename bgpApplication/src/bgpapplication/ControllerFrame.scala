/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication

import bgpapplication.server.Server
import bgpapplication.client.Client
import bgpapplication.server.resources.ResourceLoader
import bgpapplication.util.Debug
import java.awt.Color
import java.io.File
import javax.swing.border.LineBorder
import javax.swing.filechooser.FileFilter
import scala.swing._
import scala.swing.Swing._
import scala.swing.event.Event
import scala.swing.event.FocusGained
import scala.swing.event.MousePressed

object ControllerFrame extends SimpleSwingApplication {
    
    val debug = Debug("ControllerFrame")
    
    override def top = new MainFrame{
        title = "BigGamesProject!!!!!"
        preferredSize = 400 -> 200
        location = 100 -> 100
        
        /**
         * The Panel used for the Controller
         */
        val controllerPanel = {
            
            val clientStarter = {
                
                val textField = new TextField("localhost"){
                    columns = 8 // enough for most ip-addresses
                }
                
                // the actions
                val startClient = new Action("Start Client"){
                    override def apply = {
                        Controller.startClient(textField.text)
                        enabled = false
                        addClientPanel()
                    }
                }
                
                new FlowPanel(textField, new Button(startClient))
            }
            
            val startServer = new Action("Start Server"){
                override def apply() = {
                    val resourceLoader = getResourceLoader
                    
                    if (resourceLoader.isDefined){
                        enabled = false
                    
                        Controller.startServer(resourceLoader.get)
                    
                        addServerPanel()
                    }
                }
                
                /**
                 * Gets the ResourceLoader for the Server by using a FileChooser
                 */
                private def getResourceLoader = {
                    val chooser = new FileChooser(new File(".")){
                        title = "Choose Game"
                        fileFilter = new FileFilter(){
                            override def accept(f: File) = ResourceLoader.canLoad(f) || f.isDirectory
                            override def getDescription = "BigGamesProject GameThemes"
                        }
                    }
                    
                    val value = chooser.showOpenDialog(null)
                    
                    if (value == FileChooser.Result.Approve){
                        Option(new ResourceLoader(chooser.selectedFile))
                    } else Option.empty
                }
            }
        
            // the panel
            new BoxPanel(Orientation.Vertical){
                contents += new Button(startServer)
                contents += clientStarter
            }
        }
        
        /**
         * The Panel used for debug settings
         */
        val debugPanel = {
            val debugButton = new CheckBox("Debug"){
                action = Action("use debugging"){
                    Debug.isDebugging = !Debug.isDebugging
                }
            }
            
            // the list with the enabled debuggers
            val list = new ListView[String]
            
            // modifying the list
            
            val addComboBox: ComboBox[String] = new ComboBox(List.empty[String]){
                makeEditable()
            }
            
            /** updates {@code list} and {@code addComboBox.
             * Have to be called when the list is modified */
            def update() = {
                list.listData = Debug.active
                addComboBox.peer.setModel(ComboBox.newConstantModel("" :: Debug.passive))
            }
            
            val removeItem = new Action("Remove debugger"){
                override def enabled = !list.selection.items.isEmpty
                override def apply() = {
                    val selected = list.selection.items
                    Debug.active = Debug.active diff selected
                    update()
                }
            }
            
            val addItem = Action("Add debugger"){
                val selected = addComboBox.selection.item
                Debug.active ::= selected
                update()
            }
            
            val updateAction = Action("refresh"){update()}
            
            new FlowPanel(debugButton, list, new Button(removeItem),
                          addComboBox, new Button(addItem), new Button(updateAction))
        }
        
        val tabs = new TabbedPane{
            import TabbedPane._
            pages += new Page("Main", controllerPanel)
            pages += new Page("Debug", debugPanel)
            tabLayoutPolicy = Layout.Scroll
        }
        
        contents = tabs
        
        /**
         * Adds the server Panel to the tabs
         */
        def addServerPanel(): Unit = {
            
            val serverPanel = {
                
                val playerList = new ListView[String]()
                Server.onPlayerChange = () => {
                    debug("OnPlayerChange has been called")
                    playerList.listData = Server.players
                }
                playerList.border = new LineBorder(Color.DARK_GRAY)
                
                val gameStarter = {
                    val label = new Label("")
                    val starter = new Action("Start Game"){
                        override def apply() = {
                            if (Server.canStart){
                                enabled = false
                                Server.startGame()
                                label.text = "Game is started."
                            } else {
                                label.text = "Can not start the Game yet..."
                            }
                        }
                    }
                    new BoxPanel(Orientation.Horizontal){
                        contents += new Button(starter)
                        contents += label
                    }
                }

                new BoxPanel(Orientation.Vertical){
                    contents += new Label("Players:")
                    contents += playerList
                    contents += gameStarter
                }
            }
            
            val page = new TabbedPane.Page("Server", serverPanel)
            tabs.pages += page
            tabs.selection.page = page
        }
        
        def addClientPanel(): Unit = {
            
            val runView = Action("Run View"){
                Client.view.run()
            }
            
            val clientPanel = new FlowPanel(new Button(runView))
            
            val page = new TabbedPane.Page("Client", clientPanel)
            tabs.pages += page
            tabs.selection.page = page
        }
        
    }

}
