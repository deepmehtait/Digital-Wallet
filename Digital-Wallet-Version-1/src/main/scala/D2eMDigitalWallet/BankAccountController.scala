package D2eMDigitalWallet

import javax.validation.Valid

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

import scala.util.control.Breaks

/**
 * Created by Deep on 01-Oct-14.
 */
@RestController
class BankAccountController {

  @RequestMapping(value = Array("api/v1/users/{user_id}/bankaccounts"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  def PostMehtodBank(@Valid @PathVariable user_id: String, @RequestBody bankaccount: BankAccount): BankAccount={
    bankaccount.setBa_id()
    val u = UserController.UserData.get(user_id).asInstanceOf[User]
    u.BankAccounts.add(bankaccount)
    bankaccount
  }
  @RequestMapping(value= Array("api/v1/users/{user_id}/bankaccounts"), method = Array(RequestMethod.GET))
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  def GetMethodBank(@PathVariable user_id: String )= {
    val u = UserController.UserData.get(user_id).asInstanceOf[User]
    if (u.BankAccounts.isEmpty) {
      "{ No user found }"
    }
    else {
      u.BankAccounts

    }
  }
  @RequestMapping(value= Array("api/v1/users/{user_id}/bankaccounts/{ba_id}"), method = Array(RequestMethod.DELETE),consumes = Array("application/json"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ResponseBody
  def DeleteMethodBank(@PathVariable user_id: String,@PathVariable ba_id: String )= {
    val u = UserController.UserData.get(user_id).asInstanceOf[User]
    var loopbreark= new Breaks
    if (u.BankAccounts.size()!= 0)
    {
      var i: Int=0
      loopbreark.breakable {
        for (i <- 0 to u.BankAccounts.size() - 1) {
          if (u.BankAccounts.get(i).ba_id == ba_id) {
            u.BankAccounts.remove(i)
            loopbreark.break()
          }
        }
      }
    }
  }
}
