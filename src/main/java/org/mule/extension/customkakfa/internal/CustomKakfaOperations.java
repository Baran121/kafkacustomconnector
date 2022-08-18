package org.mule.extension.customkakfa.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.*;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class CustomKakfaOperations {

  /**
   * Example of an operation that uses the configuration and a connection instance to perform some action.
   */
  @MediaType(value = ANY, strict = false)
  public String retrieveOffset(@Connection CustomKakfaConnection connection, String topicName, String partition ){

    Properties properties = new Properties();
    properties.put("bootstrap.servers", connection.getConfig().getBootStrapServerURL());
    properties.put("security.protocol", "SASL_SSL");
    properties.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule   required username='"+ connection.getConfig().getUserName()+"'   password='" + connection.getConfig().getPassword() +"';");
    properties.put("sasl.mechanism", "PLAIN");
    properties.put("client.dns.lookup", "use_all_dns_ips");
    properties.put("session.timeout.ms", "45000");    
    properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");  
    properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, connection.getConfig().getGroupId());
    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    
    KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);    
		HashMap<String, String> assignments = connection.getConfig().getAssignments();
		List<TopicPartition> partitions = new ArrayList<TopicPartition>();
    assignments.forEach(
      (key,value) ->
      {
        partitions.add(new TopicPartition(key, Integer.parseInt(value)));
      }
    );
    
		consumer.assign(partitions); 
    TopicPartition lpartition = new TopicPartition(topicName, Integer.parseInt(partition));
    long st = consumer.position(lpartition);

    return String.valueOf(st);
  }

  @MediaType(value = ANY, strict = false)
  public String retrieveLatestOffset(@Connection CustomKakfaConnection connection, String topicName, String partition){

    Properties properties = new Properties();
    properties.put("bootstrap.servers", connection.getConfig().getBootStrapServerURL());
    
    properties.put("security.protocol", "SASL_SSL");
    properties.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule   required username='"+ connection.getConfig().getUserName()+"'   password='" + connection.getConfig().getPassword() +"';");
    properties.put("sasl.mechanism", "PLAIN");
    properties.put("client.dns.lookup", "use_all_dns_ips");
    properties.put("session.timeout.ms", "45000");    
    properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");  
    properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "com.ecolab.mule.customconnector");
    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    
    KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);    
		HashMap<String, String> assignments = connection.getConfig().getAssignments();
		List<TopicPartition> partitions = new ArrayList<TopicPartition>();
    assignments.forEach(
      (key,value) ->
      {
        partitions.add(new TopicPartition(key, Integer.parseInt(value)));
      }
    );
    
		consumer.assign(partitions); 
    TopicPartition lpartition = new TopicPartition(topicName, Integer.parseInt(partition));
    
    consumer.seekToEnd(Arrays.asList(lpartition));
    long st = consumer.position(lpartition);
    return String.valueOf(st);
  }

}
