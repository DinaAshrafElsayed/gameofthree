package takeaway.client.gameofthree.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import takeaway.client.gameofthree.service.CommunicationService;

public class UnregistrationServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		String jwt = (String) event.getServletContext().getAttribute("token");
		CommunicationService communicationService = (CommunicationService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(event.getServletContext()).getBean("communicationService");
		if (jwt != null)
			communicationService.unregister(jwt);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		/* no special handling is needed here */
	}

}
