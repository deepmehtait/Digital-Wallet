package D2eMDigitalWallet

import java.util
import java.util.{Date, Calendar}
import javax.validation.constraints.NotNull

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.{JsonFormat, JsonIgnore, JsonInclude}
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.annotation.Id

/**
 * Created by Deep on 30-Sep-14.
 */

class User {

  @NotEmpty
  var email: String="";
  @NotEmpty
  var password: String="";

  @Id
  var user_id: String="";
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  var name: String=_;

  var CardID= new util.ArrayList[CardId]
  var WebLogin= new util.ArrayList[WebLogin]
  var BankAccounts= new util.ArrayList[BankAccount]

  // Keep track of last access of data
  @JsonIgnore
  var lastGetTme: Long= _

  @JsonFormat(pattern = "dd/MM/yyyy'T'HH:mm")
  var create_date : Date = _

  @JsonFormat(pattern = "dd/MM/yyyy'T'HH:mm")
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  var update_date : Date = _

  def getUserID(): String ={ return user_id}
  // Generate Unique user id
  def setUserID()={
    val today = "000"+Calendar.getInstance().get(Calendar.MILLISECOND);
    var timestamp= new Date().getTime();
    var uniqueId = today.substring(today.length-3,today.length)+timestamp;
    this.user_id="U-4"+uniqueId}
  def setUserID(userid : String)={
    this.user_id=userid
  }
  def getEmail(): String={ return email}
  def setEmail(email: String)={ this.email=email}
  def getPassword(): String={ return password}
  def setPassword(password: String)={  this.password=password}
  def getName(): String={return name}
  def setName(name: String)={ this.name=name}
  def getCreate_date(): Date= { return create_date}
  def setCreate_date(create_date: Date){ this.create_date=create_date}
  def getUpdate_date(): Date= { return update_date}
  def setUpdate_date(update_date: Date){ this.update_date=update_date}
  def getlastGetTme(): Long={this.lastGetTme}
  // generate last access of data timestamp
  def setlastGetTme()={
    var timest=new Date().getTime().toLong
    this.lastGetTme=timest
  }
  // generate last access of data timestamp from put method
  def setlastGetTme(reste: Long)={
    this.lastGetTme=reste
  }
  // for testing purpose
  override def toString() : String= {
    "UID: %sEmail: %sPassword: %sname: %s".format(user_id, email, password, name) ;
  }
}
