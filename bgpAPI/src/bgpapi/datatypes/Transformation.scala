package bgpapi.datatypes

/**
 * A class used to define transformation that can be done over graphical objects.
 * Using the case classes you can build the transformations.
 * Each Transformation represents a Matrix, which is used for transforming a graphical object..
 * 
 * @author Barnabbas
 *
 */
sealed class Transformation {
  /**
   * Creates a Chain consisting of the this Transformation followed by {@code transformation}.
   */
  def ::(transformation: Transformation): Transformation = {
    // turns the order around, because :: is called on the second element...
    Transformation.Chain(transformation, this)
  }
}

object Transformation {
  
  /* The normal case classes used for 3D transformations */
  
  /**
   * Rotates the object over the axes {@code (x, y, z)} with the given angle (in degrees) counter clockwise
   */
  case class Rotate(angle: Float, x: Float, y: Float, z: Float) extends Transformation
  /**
   * Scales each axis by the given value
   */
  case class Scale(x: Float, y: Float, z: Float) extends Transformation
  /**
   * Moves the whole object over the given vector
   */
  case class Translate(x: Float, y: Float, z: Float) extends Transformation
  
  /**
   * Makes a chain of Transformations. Such that the first Transformation will be done first
   * followed by the rest of the Transformations in order.
   */
  case class Chain(transformations: Transformation*) extends Transformation
  
  /* 2D transformation auxilary methods */
  /**
   * Rotates the object in 2D spaces, in degrees, counter clockwise.
   */
  def Rotate(angle: Float): Transformation = Rotate(angle, 0, 0, 1)
  /**
   * Scales each axis by the given value
   */
  def Scale(x: Float, y: Float): Transformation = Scale(x, y, 1)
  /**
   * Moves the whole object over the given vector
   */
  def Translate(x: Float, y: Float): Transformation = Translate(x, y, 0)
  
}