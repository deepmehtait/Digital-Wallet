package D2eMDigitalWallet

import java.net.URI
import java.util
import java.util.Date
import javax.validation.Valid

import com.justinsb.etcd.{EtcdClient, EtcdResult}
import com.mongodb.DBObject
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{AnnotationConfigApplicationContext, ComponentScan}
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.{HttpHeaders, ResponseEntity, HttpStatus}
import org.springframework.web.bind.annotation._

/**
 * Created by Deep on 30-Sep-14.
 */

object UserController{
  var UserData = new util.HashMap[String, User]()
  val ctx= new AnnotationConfigApplicationContext(classOf[MongoOperationSet])
  val mongoOperation = ctx.getBean("mongoTemplate").asInstanceOf[MongoOperations]

}
@RestController
class UserController {

  val client : EtcdClient = new EtcdClient(URI.create("HTTP://54.183.233.67:4001/"))
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
    UserController.mongoOperation.save(users)
    users
  }
  @RequestMapping(value= Array("api/v1/users/{user_id}"), method = Array(RequestMethod.GET))
  @ResponseBody
  def GetMethodUser(@PathVariable user_id: String )= {

    val u = UserController.UserData.get(user_id).asInstanceOf[User]
   val getuserData = new Query(Criteria.where("_id").is(user_id))
    val getdata = UserController.mongoOperation.findOne(getuserData, classOf[User])
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
        getdata.setlastGetTme()
        // Retun user data
       new ResponseEntity[User](getdata, httpResponseHeader, HttpStatus.OK)
      }
      else {
        // Return NOT_MODIFED
        //new ResponseEntity[User](null, httpResponseHeader, HttpStatus.NOT_MODIFIED)
        new ResponseEntity[User](getdata, httpResponseHeader, HttpStatus.OK)
      }
    }
    else {
      "{ No user found }"
    }
  }
  @RequestMapping(value = Array("api/v1/users/{user_id}"), method = Array(RequestMethod.PUT), consumes = Array("application/json"))
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  def PutMehtodUser(@PathVariable(value = "user_id") user_id: String,@RequestBody user: User): Any={

    //val u1 = UserController.UserData.get(user_id).asInstanceOf[User]
    val getuserData = new Query(Criteria.where("_id").is(user_id))
    val getdata = UserController.mongoOperation.findOne(getuserData, classOf[User])
    getdata.setEmail(user.getEmail())
    getdata.setPassword(user.getPassword())
    getdata.setName(user.getName())
   var d = new Date()
    getdata.setlastGetTme(0)
    getdata.setUpdate_date(d)
    UserController.mongoOperation.save(getdata)
    getdata
  }
  @RequestMapping(value= Array("api/v1/counter"), method = Array(RequestMethod.GET))
  @ResponseBody
  def GetCounter() : Any= {
    val secreteKey = "/010011768/counter"
    var result: EtcdResult = null
    result =this.client.get(secreteKey)
    var intCount = result.node.value.toInt
    var finalCount = intCount + 1
    var update = this.client.set(secreteKey, finalCount.toString)
    result = this.client.get(secreteKey)
    return result.node.value
  }
  @RequestMapping(value= Array("api/v1/health"), method = Array(RequestMethod.GET))
  @ResponseBody
  def GetHealth() : String= {
  "null"
  }

}
