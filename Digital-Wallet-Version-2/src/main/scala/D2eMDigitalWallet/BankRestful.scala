package D2eMDigitalWallet

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonSerialize

/**
 * Created by Deep on 26-Oct-14.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
class BankRestful {

  var customer_name : String = _
  var code : Int = _
  var rn : String = _

  def getCode() : Int = {this.code}
  def setCode(code : Int) = {
    this.code=code
  }
  def getCustomer_name() : String = {this.customer_name}
  def setCustomer_name(customer_name : String )= { this.customer_name=customer_name}
  def getRn() : String = {this.rn}
  def setRn(rn : String )= { this.rn=rn}
}
