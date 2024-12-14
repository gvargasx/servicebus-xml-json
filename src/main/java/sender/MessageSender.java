package sender;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import config.ServiceBusConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

    public static void main(String[] args) {
        logger.info("Initializing Service Bus Sender...");

        try (ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(ServiceBusConfig.CONNECTION_STRING)
                .sender()
                .queueName(ServiceBusConfig.QUEUE_NAME)
                .buildClient()) {
            String jsonMessage = "{\"type\":\"json\", \"content\":\"This is a JSON message\"}";
            String xmlMessage = "<message><type>xml</type><content>This is an XML message</content></message>";

            senderClient.sendMessage(new ServiceBusMessage(jsonMessage));
            logger.info("JSON message sent.");

            senderClient.sendMessage(new ServiceBusMessage(xmlMessage));
            logger.info("XML message sent.");
        } catch (Exception e) {
            logger.error("Failed to send messages", e);
        } finally {
            logger.info("Sender client closed.");
        }
    }
}