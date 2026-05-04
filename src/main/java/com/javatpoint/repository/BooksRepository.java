package com.javatpoint.repository;
import org.springframework.data.repository.CrudRepository;

import com.javatpoint.model.Course;
public interface BooksRepository extends CrudRepository<Course, Integer>
{
}
