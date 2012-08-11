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
import javax.swing.border.Border
import javax.swing.border.LineBorder
import javax.swing.filechooser.FileFilter
import scala.swing._

object ControllerFrame extends SimpleSwingApplication {
    
    val debug = new Debug("ControllerFrame")
    
    override def top = new MainFrame{
        title = "BigGamesProject!!!!!"
        
        
        
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
            
            val list = new ListView(Debug.debuggers)
            
            
            new FlowPanel(debugButton, list)
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
