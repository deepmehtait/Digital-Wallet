package D2eMDigitalWallet

import java.util.{Date, Calendar}

import org.hibernate.validator.constraints.NotEmpty

/**
 * Created by Deep on 01-Oct-14.
 */
class WebLogin {

  @NotEmpty
  var url: String= _
  @NotEmpty
  var login: String= _
  @NotEmpty
  var password: String= _

  var login_id: String= ""

  def getUrl():String = {this.url}
  def setUrl(url: String)={this.url=url}
  def getLogin():String = {this.login}
  def setLogin(login: String)={this.login=login}
  def getPassword():String={ this.password }
  def setPassword(password: String)={this.password=password}
  def getLogin_id(): String={this.login_id}
  // generate unique login id
  def setLogin_id()={
    val today = "000"+Calendar.getInstance().get(Calendar.MILLISECOND);
    var timestamp= new Date().getTime();
    var temp ="000"+timestamp
    var uniqueId = temp.substring(7,13);
    this.login_id="l-"+uniqueId
  }




}
