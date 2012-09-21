package properties

/**
 * A Variable points to some raw data that can still change
 * @author Barnabbas
 */
class Variable[T](name: String) extends Property[T](name)

/**
 * The additional classes to work with Setting.
 */
object Variable {

	/**
	 * The Variable Value. This can only hold data.
	 */
	final case class Value[T](data: T) extends Property.Value[T]

	class Tuple[T](variable: Variable[T], value: Value[T])
		extends Property.Tuple[T, Variable[T], Value[T]](variable, value)
}
