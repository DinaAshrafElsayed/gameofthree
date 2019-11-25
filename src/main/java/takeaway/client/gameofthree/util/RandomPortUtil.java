package takeaway.client.gameofthree.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SocketUtils;
import org.springframework.util.StringUtils;

public class RandomPortUtil {
	private static final String PROPERTY_NAME_IS_SERVER_PORT = "server.port";
	private static final String ENVIRONMENT_VARIABLE_SERVER_PORT = "SERVER_PORT";
    final static Logger log = LoggerFactory.getLogger(RandomPortUtil.class);

    public static void setRandomPort(int minPort, int maxPort) {
        try {
            String userDefinedPort = System.getProperty(PROPERTY_NAME_IS_SERVER_PORT, System.getenv(ENVIRONMENT_VARIABLE_SERVER_PORT));
            if(StringUtils.isEmpty(userDefinedPort)) {
                int port = SocketUtils.findAvailableTcpPort(minPort, maxPort);    
                System.setProperty(PROPERTY_NAME_IS_SERVER_PORT, String.valueOf(port));
                log.info("Server port set to {}.", port);
            }
        } catch( IllegalStateException e) {
            log.warn("No port available in range 5000-5100. Default embedded server configuration will be used.");
        }
    }

    public static void setRandomPort() {
        setRandomPort(5000, 5200);
    }
}