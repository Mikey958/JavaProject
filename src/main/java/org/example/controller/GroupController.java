package org.example.controller;

import org.example.entity.GroupEntity;
import org.example.entity.StudentEntity;
import org.example.mapper.ChartDataMapper;
import org.example.model.Group;
import org.example.model.Student;
import org.example.service.GroupService;
import org.example.parser.parserCSV;
import org.example.drawer.Drawer;
import org.jfree.chart.JFreeChart;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    public final ModelMapper modelMapper;

    public GroupController(GroupService groupService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Group> getGroups() {
        return groupService.findAll().stream().map(this::convertToGroup).toList();
    }

    @GetMapping("/chart")
    public ResponseEntity<Resource> getBarChart() {
        try {
            Drawer drawer = new Drawer("Scores by Group");
            File chartFile = drawer.createChartFile(getGroups(), "chart.png");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"chart.png\"")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new FileSystemResource(chartFile));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/chart/{id}")
    public ResponseEntity<Resource> getBarChartForChapter(@PathVariable("id") String id) {
        try {
            Drawer drawer = new Drawer("Scores by Group");
            File chartFile = drawer.createChartFile(getGroups(), id,"chart.png");
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"chart.png\"")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new FileSystemResource(chartFile));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Group convertToGroup(GroupEntity groupEntity) {
        Group group = modelMapper.map(groupEntity, Group.class);
        group.setName(groupEntity.getName().replace("Программирование на Java ЛБ, ", ""));
        group.getStudents().forEach(student -> student.setGroup(group.getName()));
        return group;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> saveStudents() {
        List<Student> students = parserCSV.readCSVFile("java-rtf.csv");
        groupService.saveStudentsWithGroups(students);
        return ResponseEntity.ok("Students saved successfully");
    }
}