package D2eMDigitalWallet

import java.util
import java.util.Date
import javax.validation.Valid

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.{HttpHeaders, ResponseEntity, HttpStatus}
import org.springframework.web.bind.annotation._

/**
 * Created by Deep on 30-Sep-14.
 */

object UserController{
  var UserData = new util.HashMap[String, User]()

}
@RestController
class UserController {
  @RequestMapping(value = Array("api/v1/users"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  def PostMehtodUser(@Valid @RequestBody users: User): User={
    System.out.println(users)
    users.setUserID()
    var d = new Date()
    users.setCreate_date(d)
    var useridgen=users.getUserID()
    UserController.UserData.put(useridgen,users)
    users
  }
  @RequestMapping(value= Array("api/v1/users/{user_id}"), method = Array(RequestMethod.GET))
  @ResponseBody
  def GetMethodUser(@PathVariable user_id: String )= {
    val u = UserController.UserData.get(user_id).asInstanceOf[User]
    // generate response header for conditional get
    val httpResponseHeader: HttpHeaders = new HttpHeaders()
    // get last access time for the specific user_id
    var lgtime=u.getlastGetTme()
    // get the current time of system
    var crtime=new Date().getTime().toLong
    // calaulate the time difference
    var frequency=(crtime - lgtime)

    if (u != null) {
      // check frequecy of data accessed by specific user_id
      // if data accessed after 2 min of time interval retun whole data
      // if data accessed in less than 2 min of time interval return NOT_MODIFIED
      if(frequency > 120000)
      {
        u.setlastGetTme()
        // Retun user data
        new ResponseEntity[User](u, httpResponseHeader, HttpStatus.OK)
      }
      else {
        // Return NOT_MODIFED
        new ResponseEntity[User](null, httpResponseHeader, HttpStatus.NOT_MODIFIED)
      }
    }
    else {
      "{ No user found }"
    }
  }
  @RequestMapping(value = Array("api/v1/users/{user_id}"), method = Array(RequestMethod.PUT), consumes = Array("application/json"))
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  def PutMehtodUser(@PathVariable(value = "user_id") user_id: String,@RequestBody user: User): User={

    val u1 = UserController.UserData.get(user_id).asInstanceOf[User]
    u1.setEmail(user.getEmail())
    u1.setPassword(user.getPassword())
    u1.setName(user.getName())
    var d = new Date()
    u1.setlastGetTme(0)
    u1.setUpdate_date(d)
    u1
  }



}
