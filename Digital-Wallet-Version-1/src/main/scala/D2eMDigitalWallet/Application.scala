package D2eMDigitalWallet

/**
 * Created by Deep on 30-Sep-14.
 */

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan
@EnableAutoConfiguration
class Application
object Application {
  def main(args: Array[String]) {
    SpringApplication.run(classOf[Application])
  }
}