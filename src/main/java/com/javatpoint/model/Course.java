package com.javatpoint.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table
public class Course
{
@Id
@Column
private int courseId;
@Override
public String toString() {
	return "Course [courseId=" + courseId + ", title=" + title + ", description=" + description + "]";
}
public int getCourseId() {
	return courseId;
}
public void setCourseId(int courseId) {
	this.courseId = courseId;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
@Column
private String title;
@Column
private String description;

}