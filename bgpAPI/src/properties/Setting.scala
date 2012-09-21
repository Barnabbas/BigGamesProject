package properties

/**
 * A Setting is a Property that points to a SettingValue.
 * @author Barnabbas
 *
 */
class Setting[T](name: String) extends Property[T](name)

/**
 * The additional classes to work with Setting.
 */
object Setting {

	/**
	 * The Value of a Setting. This is the value that is filled in at a Setting
	 * at the ViewObject classes.<br>
	 * A Setting Value can either be the data for the value or a pointer to a variable.
	 */
	sealed trait Value[T] extends Property.Value[T]

	object Value {

		/**
		 * A SettingsValue containing raw Data.
		 * The value is immediatly the Data.
		 */
		case class Data[T](data: T) extends Value[T]

		/**
		 * A SettingsValue pointing to a variable.
		 * The value is found in the Entity at the Variable {@code variable}.
		 */
		case class Variable[T](variable: Variable[T]) extends Value[T]
	}

	class Tuple[T](setting: Setting[T], value: Value[T])
		extends Property.Tuple[T, Setting[T], Value[T]](setting, value)
}
