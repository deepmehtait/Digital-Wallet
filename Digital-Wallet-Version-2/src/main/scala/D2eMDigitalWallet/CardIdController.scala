package D2eMDigitalWallet

import java.util.Date
import javax.validation.Valid

import org.springframework.data.mongodb.core.query.{Criteria, Query}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

import scala.util.control.Breaks

/**
 * Created by Deep on 01-Oct-14.
 */
@RestController
class CardIdController {

  @RequestMapping(value = Array("api/v1/users/{user_id}/idcards"), method = Array(RequestMethod.POST), consumes = Array("application/json"))
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  def PostMehtodCard(@Valid @PathVariable user_id: String,@RequestBody cardid: CardId): CardId={
    cardid.setCard_id()
    var d = new Date()
    //val u = UserController.UserData.get(user_id).asInstanceOf[User]
    val getuserData = new Query(Criteria.where("_id").is(user_id))
    val getdata = UserController.mongoOperation.findOne(getuserData, classOf[User])
    getdata.CardID.add(cardid)
    UserController.mongoOperation.save(getdata)
    //u.CardID.add(cardid)

    cardid
  }
  @RequestMapping(value= Array("api/v1/users/{user_id}/idcards"), method = Array(RequestMethod.GET))
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  def GetMethodCard(@PathVariable user_id: String )= {
   // val u = UserController.UserData.get(user_id).asInstanceOf[User]
    val getuserData = new Query(Criteria.where("_id").is(user_id))
    val getdata = UserController.mongoOperation.findOne(getuserData, classOf[User])
    getdata.CardID
    if(getdata.CardID.isEmpty())
    {
        "No Cards for this user"

    }
    else {
      getdata.CardID
    }
  }
  @RequestMapping(value= Array("api/v1/users/{user_id}/idcards/{card_id}"), method = Array(RequestMethod.DELETE),consumes = Array("application/json"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ResponseBody
  def DeleteMethodCard(@PathVariable user_id: String,@PathVariable card_id: String )= {
    //val u = UserController.UserData.get(user_id).asInstanceOf[User]
    //u.CardID.remove(card_id)
   //System.out.print(u.CardID.size() +" amd card id" +card_id)
    val getuserData = new Query(Criteria.where("_id").is(user_id))
    val getdata = UserController.mongoOperation.findOne(getuserData, classOf[User])

    var loopbreark= new Breaks
    if (getdata.CardID.size()!= 0)

    {
      var i: Int=0
loopbreark.breakable {
  for (i <- 0 to getdata.CardID.size() - 1) {
    if (getdata.CardID.get(i).card_id == card_id) {
      getdata.CardID.remove(i)
      loopbreark.break()
    }
  }
}
    }
   UserController.mongoOperation.save(getdata)
  }
}
