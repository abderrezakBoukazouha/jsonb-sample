package org.winside.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.winside.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
