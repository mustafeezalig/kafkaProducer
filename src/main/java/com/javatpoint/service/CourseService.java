package com.javatpoint.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javatpoint.model.Course;
import com.javatpoint.repository.BooksRepository;

//defining the business logic
@Service
public class CourseService {
	@Autowired
	BooksRepository booksRepository;

//getting all books record by using the method findaAll() of CrudRepository
	public List<Course> getAllBooks() {
		List<Course> courses = new ArrayList<Course>();
		booksRepository.findAll().forEach(course -> courses.add(course));
		return courses;
	}

//getting a specific record by using the method findById() of CrudRepository
	public Course getBooksById(int id) {
		return booksRepository.findById(id).get();
	}

//saving a specific record by using the method save() of CrudRepository
	public void saveOrUpdate(Course course) {
		booksRepository.save(course);
	}

//deleting a specific record by using the method deleteById() of CrudRepository
	public void delete(int id) {
		booksRepository.deleteById(id);
	}

//updating a record
	public void update(Course course, int coursId) {
		booksRepository.save(course);
	}
}