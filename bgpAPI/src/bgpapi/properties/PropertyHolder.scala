package bgpapi.properties

/**
 * A PropertyHolder is a class that has multiple Property with values.
 * @author s100866
 *
 */
trait PropertyHolder[P <: Property, V <: Property.Value] {
	/**
	 * Gets the value of property {@code p}
	 */
	def apply(prop: P): V
	
	/**
	 * The PropertyDefiner that defines the Properties this PropertyHolder uses.
	 */
	def definer: PropertyDefiner[P]
}