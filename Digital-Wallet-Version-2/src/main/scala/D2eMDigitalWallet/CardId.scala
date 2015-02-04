package D2eMDigitalWallet

import java.util.{Calendar, Date}

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.validator.constraints.NotEmpty

/**
 * Created by Deep on 01-Oct-14.
 */
class CardId {

  @NotEmpty
  var card_name: String=""
  @NotEmpty
  var card_number: String=""
// date format month - day - year
  @JsonFormat(pattern = "MM-dd-yyyy")
  var expiration_date: Date= _
  var card_id: String=""

  def getCard_name(): String={this.card_name}
  def setCard_name(card_name: String)={this.card_name=card_name}
  def getCard_number(): String={this.card_number}
  def setCard_number(card_number: String)={this.card_number=card_number}
  def getExpiration_date(): Date={this.expiration_date}
  def setExpiration_date(expiration_date: Date)={this.expiration_date=expiration_date}
  def getCard_Id(): String={this.card_id}
  // generate unique card id
  def setCard_id()={
    val today = "000"+Calendar.getInstance().get(Calendar.MILLISECOND);
    var timestamp= new Date().getTime();
    var temp ="000"+timestamp
    var uniqueId = temp.substring(7,13);
    this.card_id="c-"+uniqueId
  }

}
