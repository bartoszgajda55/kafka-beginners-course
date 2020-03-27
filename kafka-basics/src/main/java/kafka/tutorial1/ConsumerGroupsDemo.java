package kafka.tutorial1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerGroupsDemo {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(ConsumerGroupsDemo.class);

    Properties properties = new Properties();
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "demo_group");
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

    consumer.subscribe(Arrays.asList("first_topic"));

    while (true) {
      //consumer.poll(100); // New in Kafka 2.0.0
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));

      for (ConsumerRecord<String, String> record: records) {
        logger.info("Key: " + record.key() + ", Value: " + record.value());
      }
    }
  }
}
