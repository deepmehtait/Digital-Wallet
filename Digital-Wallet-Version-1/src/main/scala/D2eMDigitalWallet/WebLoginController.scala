package D2eMDigitalWallet

import java.util.Date
import javax.validation.Valid

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

import scala.util.control.Breaks

/**
 * Created by Deep on 01-Oct-14.
 */
@RestController
class WebLoginController {

  @RequestMapping(value = Array("api/v1/users/{user_id}/weblogins"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  def PostMehtodWeb(@Valid @PathVariable user_id: String, @RequestBody weblogin: WebLogin): WebLogin={
    weblogin.setLogin_id()
    val u = UserController.UserData.get(user_id).asInstanceOf[User]
    u.WebLogin.add(weblogin)
    weblogin
  }
  @RequestMapping(value= Array("api/v1/users/{user_id}/weblogins"), method = Array(RequestMethod.GET))
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  def GetMethodWeb(@PathVariable user_id: String )= {
    val u = UserController.UserData.get(user_id).asInstanceOf[User]

    if (u.WebLogin.isEmpty) {
      "{ No user found }"

    }
    else {
      u.WebLogin
    }
  }
  @RequestMapping(value= Array("api/v1/users/{user_id}/weblogins/{login_id}"), method = Array(RequestMethod.DELETE),consumes = Array("application/json"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ResponseBody
  def DeleteMethodWeb(@PathVariable user_id: String,@PathVariable login_id: String )= {
    val u = UserController.UserData.get(user_id).asInstanceOf[User]
    var loopbreark= new Breaks
    if (u.WebLogin.size()!= 0)
    {
      var i: Int=0
      loopbreark.breakable {
        for (i <- 0 to u.WebLogin.size() - 1) {
          if (u.WebLogin.get(i).login_id == login_id) {
            u.WebLogin.remove(i)
            loopbreark.break()
          }
        }
      }
    }
  }
}
