/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.util

/**
 * Classes and utilities to easier use LWJGL in the bgp/scala environment.
 */
package object lwjgl{
  
  /**
   * Displays {@code body} in a new LWJGL Display with the given title.
   */
  def display(title: String)(body: => Unit) = BgpDisplay(title)(body)
  
}
