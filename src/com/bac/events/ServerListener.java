package com.bac.events;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * @author nhatn
 */
@WebListener
public class ServerListener implements ServletContextListener {

    public ServerListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        String realPathImages = sce.getServletContext().getRealPath("") + "shared\\images";
        File theDir = new File(realPathImages);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }
}
