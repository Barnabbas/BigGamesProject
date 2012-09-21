
package bgpapi

/**
 * This package contains classes to define and work with properties.
 *
 * Properties can be divided into two subclasses: Variables and Settings.
 * Variables are some sort of pointers (for example for inside the ViewEntities) that point to something that can change.
 * Settings are also some sort of pointers that point to something that is either raw data or it points to a variable.
 */

package object properties {
	
	// warning this object and the whole package can have some unreadable code, because of the extensive use of generics

	implicit def propTuple2Tuple[T, P <: Property[T], V <: Property.Value[T]](tuple: Property.Tuple[T, P, V]): (P, V)
		= (tuple.property, tuple.value)
	
	implicit def tuple2SetTuple[T](tuple: (Setting[T], Setting.Value[T])): Setting.Tuple[T] =
		new Setting.Tuple[T](tuple._1, tuple._2)
		
	implicit def tuple2VarTuple[T](tuple: (Variable[T], Variable.Value[T])): Variable.Tuple[T] =
		new Variable.Tuple[T](tuple._1, tuple._2)
		
	implicit def any2VarValue[T](data: T): Variable.Value[T] = Variable.Value[T](data)
}