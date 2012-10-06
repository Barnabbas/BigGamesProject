package bgpapi.properties

/**
 * A Variable points to some raw data that can still change
 * @author Barnabbas
 */
class Variable(name: String) extends Property(name)

/**
 * The additional classes to work with Variable.
 */
object Variable {

	/**
	 * The Variable Value. This can only hold data.
	 */
	final case class Value(data: Any) extends Property.Value
}
