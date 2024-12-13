package org.example.service;

import org.example.entity.GroupEntity;
import org.example.entity.StudentEntity;
import org.example.mapper.StudentMapper;
import org.example.model.Student;
import org.example.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final Repository groupRepository;
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    public GroupService(Repository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Optional<GroupEntity> findByName(String name) {
        return groupRepository.findByName(name);
    }

    public GroupEntity save(GroupEntity group) {
        return groupRepository.save(group);
    }

    public List<GroupEntity> findAll() {
        return groupRepository.findAll();
    }

    /**
     * ��������� ��������� � ��������������� ������.
     * ������ ������� ����� �������� � ���� ������. ���� ������ �� ����������, ��� ����� �������.
     * @param students ������ ���������.
     */
    @Transactional
    public void saveStudentsWithGroups(List<Student> students) {
        for (Student student : students) {
            GroupEntity group = findOrCreateGroup(student.getGroup());

            StudentEntity studentEntity = StudentMapper.toEntity(student);

            group.getStudents().add(studentEntity);
            studentEntity.setGroup(group);

            // ������ ����������� ������ ���� ��� � �����
            if (!group.getStudents().isEmpty()) {
                save(group);
            }
        }
    }

    /**
     * ������� ������ �� ����� ��� ������� �����, ���� ������ �� �������.
     * @param groupName ��� ������.
     * @return ������������ ��� ����� ������.
     */
    public GroupEntity findOrCreateGroup(String groupName) {
        return this.findByName(groupName)
                .orElseGet(() -> {
                    GroupEntity group = new GroupEntity();
                    group.setName(groupName);
                    logger.info("Group with name '{}' not found, creating new group.", groupName);
                    return this.save(group);
                });
    }

    /**
     * ������ ��������� � �������������� computeIfAbsent.
     * ���� ����� �����������, ��� ������ ����� ������� ������ � ������ � ����������.
     * ��������� ��� ������� ������, ���� �� ��� ���.
     */
    public GroupEntity findOrCreateGroupUsingMap(String groupName) {
        return groupRepository.findByName(groupName)
                .orElseGet(() -> {
                    GroupEntity group = new GroupEntity();
                    group.setName(groupName);
                    return save(group);
                });
    }
}
