package bgpapi.properties

/**
 * A Property is a pointer to a value.<br>
 * Those pointers are used inside a Resource like the ViewObject to get data from.
 * @author Barnabbas
 */
class Property[T](n: String) extends Serializable {
	/**
	 * What this Property is called. Should be unique for each Property that is used in a Resource.
	 */
	val name: String = n
}

/**
 * This object contains some classes that are required to work with the Properties API.
 */
object Property {

	/**
	 * This is a class that holds a Value.<br>
	 * Can be implemented in different ways, most of the time with case classes.
	 *
	 */
	trait Value[T] extends Serializable

	class Tuple[T, P <: Property[T], V <: Value[T]](val property: P, val value: V)
}
