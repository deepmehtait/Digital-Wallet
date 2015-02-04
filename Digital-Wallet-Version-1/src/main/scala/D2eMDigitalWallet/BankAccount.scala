package D2eMDigitalWallet

import java.util.{Date, Calendar}
import javax.validation.constraints.NotNull

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.hibernate.validator.constraints.NotEmpty

/**
 * Created by Deep on 01-Oct-14.
 */
class BankAccount {

  var ba_id: String= _

  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  var account_name: String= _

  @NotEmpty
  var routing_number: String= _
  @NotEmpty
  var account_number: String= _

  def getRouting_number(): String={this.routing_number}
  def setRouting_name(routing_number: String)={this.routing_number=routing_number}
  def getAccount_number(): String={this.account_name}
  def setAccount_number(account_number: String)={this.account_number=account_name}
  def getAccount_name(): String={this.account_name}
  def setAccount_name(account_name: String)={this.account_name=account_name}
  def getBa_id():String={this.ba_id}
  // generate unique bank id
  def setBa_id()={
    val today = "000"+Calendar.getInstance().get(Calendar.MILLISECOND);
    var timestamp= new Date().getTime();
    var temp ="000"+timestamp
    var uniqueId = temp.substring(8,13);
    this.ba_id="b-"+uniqueId
  }

}
