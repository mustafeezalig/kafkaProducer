package com.javatpoint.kafka.producer;

public class TopicDetail {
	
	@Override
	public String toString() {
		return "TopicDetail [name=" + name + ", partition=" + partition + ", replicate=" + replicate + "]";
	}
	private String name;
	private int partition;
	private int replicate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPartition() {
		return partition;
	}
	public void setPartition(int partition) {
		this.partition = partition;
	}
	public int getReplicate() {
		return replicate;
	}
	public void setReplicate(int replicate) {
		this.replicate = replicate;
	}

}
