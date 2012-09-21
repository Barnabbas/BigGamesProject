package bgpapi.properties

/**
 * A PropertyHolder is a class that has multiple Property with values.
 * @author s100866
 *
 */
trait PropertyHolder[P <: Property[_], V <: Property.Value[_]] {
	/**
	 * Gets the value of property {@code p}
	 */
	def apply[T](prop: P): V
	
	/**
	 * The PropertyDefiner that defines the Properties this PropertyHolder uses.
	 */
	def definer: PropertyDefiner[P]
}