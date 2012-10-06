/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bgpapplication.server.resources

/**
 * A DataReader reads files such that Resources can be gotten from it.
 */
import bgpapi.Resource

private[resources] trait DataReader {
    
    /**
     * Gets an Object of class {@code T} with an id of {@code identifier}.
     * @throws IllegalArgumentException when this DataReader can't read elements 
     * of type {@code T}
     * @throws DataReader.FormatException when there is an error in the format
     * of the data.
     */
    def get[T <: Resource](identifier: String)(implicit man: Manifest[T]): T

}

private[resources] object DataReader {
    /**
     * Will be thrown when the data is unclear formatted
     */
    class FormatException(message: String) extends RuntimeException(message)
}
