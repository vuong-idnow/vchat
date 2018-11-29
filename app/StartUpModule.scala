import com.google.inject.AbstractModule
import util.AtmosphereHelper

class StartUpModule extends AbstractModule {
  override def configure(): Unit = super.bind(classOf[AtmosphereHelper]) asEagerSingleton()

}
