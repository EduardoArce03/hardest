package swagger;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


@WebListener
public class Context implements ServletContextListener{
	
	@Override
    public void contextInitialized(ServletContextEvent sce) {
        Config.initTracer();
    }
	
	 @Override
     public void contextDestroyed(ServletContextEvent sce) {
      //Metodo vacio
	 }
}