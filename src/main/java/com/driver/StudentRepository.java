package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StudentRepository {
    Map<String, Student> studentDb = new HashMap<>();

    Map<String, Teacher> teacherDb = new HashMap<>();

    Map<String, List<String>> teacherStudentDb = new HashMap<>();

    public void addStudent(Student student) {
        studentDb.put(student.getName(), student);
    }

    public void addTeacher(Teacher teacher) {
        teacherDb.put(teacher.getName(), teacher);
    }

    public void addStudentTeacherPair(String student, String teacher) {
        if(!studentDb.containsKey(student) || !teacherDb.containsKey(teacher))
            return;
        List<String> studentsList = new ArrayList<>();
        if(teacherStudentDb.containsKey(teacher))
            studentsList = teacherStudentDb.get(teacher);
//        if(!studentsList.contains(student))
        studentsList.add(student);
        teacherStudentDb.put(teacher, studentsList);
    }

    public Student getStudentByName(String name) {
//        if(!studentDb.containsKey(name))
//            return null;
        return studentDb.get(name);
    }

    public Teacher getTeacherByName(String name) {
//        if(!teacherDb.containsKey(name))
//            return null;
        return teacherDb.get(name);
    }

    public List<String> getStudentsByTeacherName(String teacher) {
        if(!teacherDb.containsKey(teacher))
            return new ArrayList<>();
        return teacherStudentDb.get(teacher);
    }

    public List<String> getAllStudents() {
        List<String> studentList = new ArrayList<>();
        for(String currStudent : studentDb.keySet()){
            studentList.add(currStudent);
        }
        return studentList;
    }

    public void deleteTeacherByName(String teacher) {
        List<String> studentList = new ArrayList<>();
        if(teacherStudentDb.containsKey(teacher))
            studentList = teacherStudentDb.get(teacher);

        for(String currStudent : studentList){
            if(studentDb.containsKey(currStudent))
                studentDb.remove(currStudent);
        }

        teacherDb.remove(teacher);
        teacherStudentDb.remove(teacher);
    }

    public void deleteAllTeachers() {
        HashSet<String> studentsSet = new HashSet<>();
        for(String currTeacher : teacherStudentDb.keySet()){
            List<String> studentsList = teacherStudentDb.get(currTeacher);
            for(String currStudent : studentsList){
                studentsSet.add(currStudent);
            }
        }
        for(String currStudent : studentsSet){
            if(studentDb.containsKey(currStudent))
                studentDb.remove(currStudent);
        }

        teacherStudentDb.clear();
        teacherDb.clear();
    }
}
