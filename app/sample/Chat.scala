package sample

import org.atmosphere.cache.DefaultBroadcasterCache
import org.atmosphere.config.service.{Disconnect, ManagedService, Ready}
import org.atmosphere.cpr.{AtmosphereResource, AtmosphereResourceEvent}
import org.atmosphere.interceptor.{AtmosphereResourceLifecycleInterceptor, CacheHeadersInterceptor, CorsInterceptor, SuspendTrackerInterceptor}
import play.api.Logger

import org.atmosphere.config.managed.Decoder
import org.atmosphere.config.managed.Encoder
import com.fasterxml.jackson.databind.ObjectMapper

@ManagedService(path = "/chat",
  interceptors = Array(classOf[AtmosphereResourceLifecycleInterceptor], classOf[SuspendTrackerInterceptor], classOf[CorsInterceptor], classOf[CacheHeadersInterceptor]),
  broadcasterCache = classOf[DefaultBroadcasterCache])
class Chat {
  val logger = Logger.apply(classOf[Chat])

  @Ready
  def onReady(r: AtmosphereResource): Unit = {
    logger.info(s"Browser ${r.uuid()} connected.")
  }

  @Disconnect
  def onDisconnect(event: AtmosphereResourceEvent): Unit = {
    if (event.isCancelled) logger.info(s"Browser ${event.getResource.uuid} unexpectedly disconnected")
    else if (event.isClosedByClient) logger.info(s"Browser $event.getResource.uuid closed the connection")
  }


  @org.atmosphere.config.service.Message(encoders = Array(classOf[JacksonEncoder]), decoders = Array(classOf[JacksonDecoder]))
  def onMessage(message: Message): Message = {
    logger.info(s"${message.getAuthor} just send $message.getMessage")
    message
  }
}

case class JacksonEncoder() extends Encoder[Message, String] {
  val mapper: ObjectMapper = new ObjectMapper()
  override def encode(s: Message): String = mapper.writeValueAsString(s)
}

case class JacksonDecoder() extends Decoder[String, Message] {
  val mapper: ObjectMapper = new ObjectMapper()
  override def decode(s: String): Message = mapper.readValue(s, classOf[Message])

}