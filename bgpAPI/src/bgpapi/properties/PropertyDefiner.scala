package bgpapi.properties

/**
 * This defines the properties for a certain PropertyHolder.<br>
 * It also allows to get properties by their name. Every Property must have an unique name for a Definer.
 * @author Barnabbas
 */
trait PropertyDefiner[P <: Property[_]] {
	/**
	 * Gets the Property named {@code name}.<br>
	 * @return a Property such that its name is equal to {@code name}.
	 */
	def property(name: String): P
	
	/**
	 * Gets all properties for this Definer.
	 */
	def properties: Set[P]
}