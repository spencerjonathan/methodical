package uk.co.methodical.config;

import javax.servlet.ServletContext;  
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;  
  
import org.springframework.web.WebApplicationInitializer;  
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;  
import org.springframework.web.servlet.DispatcherServlet;  
  

/*public class WebAppInitializer implements 
WebApplicationInitializer {

  public void onStartup(ServletContext container) {
    ServletRegistration.Dynamic registration = 
      container.addServlet("dispatcher", new DispatcherServlet());
    registration.setLoadOnStartup(1);
    registration.addMapping("/");
  }
}*/

public class WebAppInitializer implements WebApplicationInitializer {
	public void onStartup(ServletContext servletContext) throws ServletException {  
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();  
        ctx.register(AppConfig.class);  
        ctx.setServletContext(servletContext);    
        Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));  
        dynamic.addMapping("/ws/*");  
        dynamic.setLoadOnStartup(1);  
   }  
}
