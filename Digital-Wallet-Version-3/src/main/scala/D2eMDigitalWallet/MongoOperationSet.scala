package D2eMDigitalWallet

import com.mongodb.{MongoClientURI, MongoClient}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.{MongoTemplate, SimpleMongoDbFactory}

/**
 * Created by Deep on 26-Oct-14.
 */

@Configuration
class MongoOperationSet {

    @Bean
    def mongoDbFactory() : MongoDbFactory = {
      new SimpleMongoDbFactory(new MongoClient(new MongoClientURI("mongodb://digitalwalletadmin:deepmehtait@ds047040.mongolab.com:47040/digital-wallet-app")),"digital-wallet-app")

    }
  @Bean
  def mongoTemplate() : MongoTemplate = {
    val mongoTemplate = new MongoTemplate((mongoDbFactory()))
    mongoTemplate

  }

}
