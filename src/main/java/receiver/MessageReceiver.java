package receiver;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import config.MessageValidator;
import config.ServiceBusConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceiver {
    private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    public static void main(String[] args) {
        logger.info("Initializing Service Bus Processor...");

        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
                .connectionString(ServiceBusConfig.CONNECTION_STRING)
                .processor()
                .queueName(ServiceBusConfig.QUEUE_NAME)
                .processMessage(context -> {
                    String messageBody = context.getMessage().getBody().toString();

                    if (MessageValidator.isJson(messageBody)) {
                        logger.info("Received JSON message: " + messageBody);
                    } else if (MessageValidator.isXml(messageBody)) {
                        logger.info("Received XML message: {0}" + messageBody);
                    } else {
                        logger.warn("Received unknown format message: {}" + messageBody);
                    }

                    context.complete(); // Confirma o processamento da mensagem
                })
                .processError(context ->
                        logger.error("Error occurred during message processing", context.getException()))
                .buildProcessorClient();

        processorClient.start();
        logger.info("Waiting for messages...");
    }
}
