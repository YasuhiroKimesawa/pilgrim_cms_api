package userinterface.user

import io.circe.generic.auto._
import io.finch._
import io.finch.circe._
import io.finch.syntax._
import shapeless.:+:
import shapeless.CNil

case class User(id: Long, email: String, screenName: String)

class UserController {
  val userJson: Endpoint[Seq[User]] = jsonBody[Seq[User]]

  val listUser: Endpoint[User] = get("users") {
    Ok(User(1, "aaa", "bbb"))
  }

  val showUser: Endpoint[User] = get("users" :: path[Long]) { id: Long =>
    Ok(User(id, "aaa", "bbb"))
  }

  // 配列jsonも出来るかな
  val createUserByJson: Endpoint[User] = post("usersjson" :: userJson) {
    p: Seq[User] =>
      Ok(User(1, "aaa", "bbb"))
  }

  val userService
    : Endpoint[:+:[User, :+:[User, :+:[User, CNil]]]] = listUser :+: showUser :+: createUserByJson
}
