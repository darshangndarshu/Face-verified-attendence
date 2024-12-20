package com.Face_detection.Face_detection.Repository;


import com.Face_detection.Face_detection.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long>{
    static Optional<Student> findByStudentId(String studentId) {
        return Optional.empty();
    }
}





















































//import com.Face_detection.Face_detection.Model.Student;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.repository.query.FluentQuery;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//public class StudentRepo implements MongoRepository<Student, String> {
//
//    @Override
//    public <S extends Student> S insert(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Student> List<S> insert(Iterable<S> entities) {
//        return List.of();
//    }
//
//    @Override
//    public <S extends Student> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends Student> List<S> findAll(Example<S> example) {
//        return List.of();
//    }
//
//    @Override
//    public <S extends Student> List<S> findAll(Example<S> example, Sort sort) {
//        return List.of();
//    }
//
//    @Override
//    public <S extends Student> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends Student> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends Student> boolean exists(Example<S> example) {
//        return false;
//    }
//
//    @Override
//    public <S extends Student, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return null;
//    }
//
//    @Override
//    public <S extends Student> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Student> List<S> saveAll(Iterable<S> entities) {
//        return List.of();
//    }
//
//    @Override
//    public Optional<Student> findById(String s) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(String s) {
//        return false;
//    }
//
//    @Override
//    public List<Student> findAll() {
//        return List.of();
//    }
//
//    @Override
//    public List<Student> findAllById(Iterable<String> strings) {
//        return List.of();
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(String s) {
//
//    }
//
//    @Override
//    public void delete(Student entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends String> strings) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Student> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public List<Student> findAll(Sort sort) {
//        return List.of();
//    }
//
//    @Override
//    public Page<Student> findAll(Pageable pageable) {
//        return null;
//    }
//}
