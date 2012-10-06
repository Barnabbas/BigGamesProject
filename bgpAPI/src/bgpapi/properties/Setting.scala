package bgpapi.properties

/**
 * A Setting is a Property that points to a SettingValue.
 * @author Barnabbas
 *
 */
class Setting(name: String) extends Property(name)

/**
 * The additional classes to work with Setting.
 */
object Setting {

	/**
	 * The Value of a Setting. This is the value that is filled in at a Setting
	 * at the ViewObject classes.<br>
	 * A Setting Value can either be the data for the value or a pointer to a variable.
	 */
	sealed trait Value extends Property.Value

	object Value {

		/**
		 * A SettingsValue containing raw Data.
		 * The value is immediatly the Data.
		 */
		case class Data(data: Any) extends Value

		/**
		 * A SettingsValue pointing to a variable.
		 * The value is found in the Entity at the Variable {@code variable}.
		 */
		case class Variable(variable: bgpapi.properties.Variable) extends Value
	}
}
