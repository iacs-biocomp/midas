package es.aragon.midas.process;

import es.aragon.midas.config.Constants;
import es.aragon.midas.config.EnvProperties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public final class ProcessContextListener implements ServletContextListener {

    
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String info = context.getServerInfo();
        System.out.println("Arrancado servidor " + info);

        /* Arrancamos el motor Activiti */
        if (EnvProperties.getProperty(Constants.PROC_ENGINE).equals("true")) {
            ProcessEngineManager.startEngine();
        }

        /* Arrancamos el motor de reglas */
        if (EnvProperties.getProperty(Constants.RULES_ENGINE).equals("true")) {
            
        }
        
        //ConnectionFactory.getMidasEMF();
    }

    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String info = context.getServerInfo();
        System.out.println("Parando servidor " + info);

        // ConnectionFactory.shutdown();
    }

}
