package org.example.controller;

import org.example.entity.GroupEntity;
import org.example.model.Group;
import org.example.model.Student;
import org.example.service.Service;
import org.example.parser.parserCSV;
import org.example.drawer.Drawer;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final Service groupService;
    private final ModelMapper modelMapper;

    public GroupController(Service groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    /**
     * Получение всех групп.
     *
     * @return Список групп
     */
    @GetMapping
    public List<Group> getGroups() {
        return groupService.findAll().stream().map(this::convertToGroup).toList();
    }

    /**
     * Генерация диаграммы по всем группам.
     *
     * @return Ресурс диаграммы
     */
    @GetMapping("/theme")
    public ResponseEntity<Resource> getBarChart() {
        try {
            Drawer drawer = new Drawer("Scores by Group");
            File chartFile = drawer.createChartFile(getGroups(), "theme.png");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"theme.png\"")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new FileSystemResource(chartFile));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Генерация диаграммы для конкретной группы.
     *
     * @param id ID группы
     * @return Ресурс диаграммы
     */
    @GetMapping("/theme/{id}")
    public ResponseEntity<Resource> getBarChartForChapter(@PathVariable("id") String id) {
        try {
            Drawer drawer = new Drawer("Scores by Group");
            File chartFile = drawer.createChartFile(getGroups(), id, "chart.png");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"chart.png\"")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new FileSystemResource(chartFile));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Преобразует объект GroupEntity в модель Group.
     *
     * @param groupEntity Сущность группы
     * @return Модель группы
     */
    public Group convertToGroup(GroupEntity groupEntity) {
        Group group = modelMapper.map(groupEntity, Group.class);
        group.setName(groupEntity.getName().replace("Программирование на Java ЛБ, ", ""));
        group.getStudents().forEach(student -> student.setGroup(group.getName()));
        return group;
    }

    /**
     * Загрузка студентов из CSV-файла и сохранение их в базу данных.
     *
     * @return Ответ о успешном сохранении
     */
    @PostMapping("/upload")
    public ResponseEntity<String> saveStudents() {
        List<Student> students = parserCSV.readCSVFile("java-rtf.csv");
        groupService.saveStudentsWithGroups(students);
        return ResponseEntity.ok("Students saved successfully");
    }
}
