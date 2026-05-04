package com.javatpoint.kafka.producer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.stereotype.Service;

@Service
public class KafkaTopicService {

	private final AdminClient adminClient;

	public KafkaTopicService(AdminClient adminClient) {
		this.adminClient = adminClient;
	}

	public List<String> getAllTopicNames() throws ExecutionException, InterruptedException {
		return new ArrayList<>(adminClient.listTopics().names().get());
	}

	public Map<String, TopicDescription> getAllTopicDetails() throws ExecutionException, InterruptedException {
		Set<String> topicNames = adminClient.listTopics().names().get();
		return adminClient.describeTopics(topicNames).all().get();
	}

	public void createTopics(Set<TopicDetail> topicDetail) throws ExecutionException, InterruptedException {
		// ✅ Define multiple topics

		Set<NewTopic> setTopics = new HashSet<>();
		for (TopicDetail st : topicDetail) {
			setTopics.add(new NewTopic(st.getName(), st.getPartition(), (short) st.getReplicate()));
		}

		// ✅ Optional: add topic-level configurations
		//Map<String, String> config = new HashMap<>();
		//config.put(TopicConfig.RETENTION_MS_CONFIG, "604800000"); // 7 days
		//topic1.configs(config);
		//topic2.configs(config);
		// ✅ Create all topics together
		adminClient.createTopics(setTopics).all().get();
		System.out.println("✅ Topics created successfully!");
	}
	
	public String deleteTopics(List<String> topicNames) {
        try {
            adminClient.deleteTopics(topicNames).all().get();
            return "🗑️ Successfully deleted topics: " + topicNames;
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return "❌ Failed to delete topics: " + e.getMessage();
        }
    }

}
