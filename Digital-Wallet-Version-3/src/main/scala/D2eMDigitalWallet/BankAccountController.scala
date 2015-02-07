package D2eMDigitalWallet

import java.util
import javax.validation.Valid

import org.springframework.cglib.core.Converter
import org.springframework.data.mongodb.core.query.{Criteria, Query}
import org.springframework.http.{ResponseEntity, HttpHeaders, MediaType, HttpStatus}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.bind.annotation._
import org.springframework.web.client.RestTemplate

import scala.util.control.Breaks

/**
 * Created by Deep on 01-Oct-14.
 */
@RestController
class BankAccountController {

  @RequestMapping(value = Array("api/v1/users/{user_id}/bankaccounts"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
  @ResponseBody
  def PostMehtodBank(@Valid @PathVariable user_id: String, @RequestBody bankaccount: BankAccount): Any={
    val httpResponseHeader: HttpHeaders = new HttpHeaders()
    bankaccount.setBa_id()
    //val u = UserController.UserData.get(user_id).asInstanceOf[User]
    val getuserData = new Query(Criteria.where("_id").is(user_id))
    val getdata = UserController.mongoOperation.findOne(getuserData, classOf[User])
    val routingnumb= bankaccount.getRouting_number()
    val RestTemplate = new RestTemplate()

    val converter = new MappingJackson2HttpMessageConverter
    val supportedMediatypes = new util.LinkedList[MediaType](converter.getSupportedMediaTypes)
    val textJavaSriptsMediaTypes = new MediaType("text", "plain", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)
    supportedMediatypes.add(textJavaSriptsMediaTypes)
    converter.setSupportedMediaTypes(supportedMediatypes)
    RestTemplate.getMessageConverters.add(converter)
    val bankrestful = RestTemplate.getForObject("https://routingnumbers.herokuapp.com/api/data.json?rn="+routingnumb,classOf[BankRestful])
    val codecheck=bankrestful.getCode()
    if(codecheck.equals(200))
    {
      bankaccount.setAccount_name(bankrestful.getCustomer_name())
      getdata.BankAccounts.add(bankaccount)
      UserController.mongoOperation.save(getdata)
      bankaccount
      new ResponseEntity[BankAccount](bankaccount, httpResponseHeader, HttpStatus.CREATED)
    }
    else {

      if(codecheck.equals(400))
      {
        new ResponseEntity[BankRestful](bankrestful, httpResponseHeader, HttpStatus.BAD_REQUEST)

      }
      else
      {
        new ResponseEntity[BankRestful](bankrestful, httpResponseHeader, HttpStatus.NOT_FOUND)

      }

    }



  }
  @RequestMapping(value= Array("api/v1/users/{user_id}/bankaccounts"), method = Array(RequestMethod.GET))
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  def GetMethodBank(@PathVariable user_id: String )= {
//    val u = UserController.UserData.get(user_id).asInstanceOf[User]
    val getuserData = new Query(Criteria.where("_id").is(user_id))
    val getdata = UserController.mongoOperation.findOne(getuserData, classOf[User])
    getdata.BankAccounts

    if (getdata.BankAccounts.isEmpty) {
      "{ No user found }"
    }
    else {
      getdata.BankAccounts

    }
  }
  @RequestMapping(value= Array("api/v1/users/{user_id}/bankaccounts/{ba_id}"), method = Array(RequestMethod.DELETE),consumes = Array("application/json"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ResponseBody
  def DeleteMethodBank(@PathVariable user_id: String,@PathVariable ba_id: String )= {
//    val u = UserController.UserData.get(user_id).asInstanceOf[User]
    val getuserData = new Query(Criteria.where("_id").is(user_id))
    val getdata = UserController.mongoOperation.findOne(getuserData, classOf[User])

    var loopbreark= new Breaks
    if (getdata.BankAccounts.size()!= 0)
    {
      var i: Int=0
      loopbreark.breakable {
        for (i <- 0 to getdata.BankAccounts.size() - 1) {
          if (getdata.BankAccounts.get(i).ba_id == ba_id) {
            getdata.BankAccounts.remove(i)
            loopbreark.break()
          }
        }
      }
    }
    UserController.mongoOperation.save(getdata)
  }
}
