package models

trait Aggregate[ID <: EntityId] extends Entity[ID] {
  val id: ID
}

trait Entity[ID <: EntityId] {
  val id: ID

  override def equals(obj: Any): Boolean =
    obj match {
      case that: Entity[_] =>
        this.getClass == that.getClass && this.id == that.id
      case _ => false
    }

  override def hashCode(): Int = 31 + id.hashCode
}

trait EntityId extends Any with Value[Long]

trait DomainEvent

trait Value[T] extends Any {
  def value: T
}
