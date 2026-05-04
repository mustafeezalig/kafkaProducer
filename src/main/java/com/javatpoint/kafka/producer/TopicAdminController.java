package com.javatpoint.kafka.producer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.TopicDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicAdminController {

	@Autowired
	private KafkaTopicService kafkaTopicService;

	@PostMapping("/craete/topics")
	public void createAllTopics(@RequestBody List<TopicDetail> topicDetail) {
		System.out.println("Kafka topic detail" + topicDetail);
		try {
			kafkaTopicService.createTopics(new HashSet<>(topicDetail));
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

	}

	@GetMapping("/topics")
	public List<String> getAllTopics() {
		System.out.println("Kafka topic detail");
		try {
			return kafkaTopicService.getAllTopicNames();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
		return Arrays.asList("No Topic Found");
	}

	@GetMapping("/desc/topics")
	public Map<String, TopicDescription> getAllTopicsDetails() {
		System.out.println("Kafka topic detail");
		try {
			Map<String, org.apache.kafka.clients.admin.TopicDescription> topicDetails = kafkaTopicService
					.getAllTopicDetails();
			topicDetails.forEach((name, desc) -> {
				System.out.println("\n🔹 Topic: " + name);
				System.out.println("Partitions: " + desc.partitions().size());
				desc.partitions().forEach(p -> System.out.println(
						" - Partition " + p.partition() + ", Leader: " + p.leader() + ", Replicas: " + p.replicas()));
			});
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@DeleteMapping("/delete/topics")
	public void deleteAllTopics(@RequestBody List<String> listTopics) {
		System.out.println("Kafka topic detail" + listTopics);
		try {
			kafkaTopicService.deleteTopics(listTopics);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
