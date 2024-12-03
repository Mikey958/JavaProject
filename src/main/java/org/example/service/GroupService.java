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
     * Сохраняет студентов в соответствующие группы.
     * Каждый студент будет добавлен в свою группу. Если группа не существует, она будет создана.
     * @param students Список студентов.
     */
    @Transactional
    public void saveStudentsWithGroups(List<Student> students) {
        for (Student student : students) {
            GroupEntity group = findOrCreateGroup(student.getGroup());

            StudentEntity studentEntity = StudentMapper.toEntity(student);

            group.getStudents().add(studentEntity);
            studentEntity.setGroup(group);

            // Группа сохраняется только один раз в конце
            if (!group.getStudents().isEmpty()) {
                save(group);
            }
        }
    }

    /**
     * Находит группу по имени или создает новую, если группа не найдена.
     * @param groupName Имя группы.
     * @return Существующая или новая группа.
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
     * Пример улучшения с использованием computeIfAbsent.
     * Этот метод гарантирует, что группа будет создана только в случае её отсутствия.
     * Сохраняет или создает группу, если ее еще нет.
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
