package org.example.controller;

import org.example.entity.GroupEntity;
import org.example.model.Group;
import org.example.model.Student;
import org.example.service.GroupService;
import org.example.parser.parserCSV;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    // Внедрение зависимостей через конструктор
    public GroupController(GroupService groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    /**
     * Получение списка всех групп с их студентами.
     */
    @GetMapping
    public List<Group> getStudents() {
        return groupService.findAll().stream()
                .map(this::convertToGroup)
                .toList();
    }

    /**
     * Преобразование GroupEntity в Group (DTO).
     */
    private Group convertToGroup(GroupEntity groupEntity) {
        Group group = modelMapper.map(groupEntity, Group.class);
        group.getStudents().forEach(student -> student.setGroup(group.getName()));
        return group;
    }

    /**
     * Загрузка студентов из CSV файла.
     * Для загрузки можно указать файл как параметр запроса.
     * Пример: POST /groups/upload?fileName=java-rtf.csv
     */
    @PostMapping("/upload")
    public ResponseEntity<String> saveStudents(@RequestParam(name = "fileName", required = false, defaultValue = "java-rtf.csv") String fileName) {
        try {
            List<Student> students = parserCSV.readCSVFile(fileName);

            if (students.isEmpty()) {
                logger.warn("No students found in CSV file: {}", fileName);
                return ResponseEntity.badRequest().body("No students found in the file.");
            }

            groupService.saveStudentsWithGroups(students);
            logger.info("Successfully saved {} students from file: {}", students.size(), fileName);

            return ResponseEntity.ok("Students saved successfully");
        } catch (Exception e) {
            logger.error("Error processing the CSV file: {}", fileName, e);
            return ResponseEntity.status(500).body("An error occurred while processing the file.");
        }
    }
}
