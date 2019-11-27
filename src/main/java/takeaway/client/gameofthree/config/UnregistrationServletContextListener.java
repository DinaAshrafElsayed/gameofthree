package takeaway.client.gameofthree.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;

import takeaway.client.gameofthree.service.CommunicationService;

public class UnregistrationServletContextListener implements ServletContextListener {

	@Autowired
	private CommunicationService communicationService;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		String jwt = (String) event.getServletContext().getAttribute("token");
		if (jwt != null)
			communicationService.unregister(jwt);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		/* no special handling is needed here */
	}

}
