package bgpapi.properties

/**
 * A Property is a pointer to a value.<br>
 * Those pointers are used inside a Resource like the ViewObject to get data from.
 * @author Barnabbas
 */
class Property protected(n: String) extends Serializable {
  /**
   * What this Property is called. Should be unique for each Property that is used in a Resource.
   */
  val name: String = n
  
  override def toString = name
  
  // todo: do we really want it like this?
  override def equals(a: Any) = a match{
    case p: Property => p.name == name
    case _ => false
  }
  
  override def hashCode = name.hashCode
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
  trait Value extends Serializable
}
