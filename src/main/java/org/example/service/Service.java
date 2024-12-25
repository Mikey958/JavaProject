package org.example.service;

import org.example.entity.GroupEntity;
import org.example.entity.StudentEntity;
import org.example.mapper.StudentMapper;
import org.example.model.Student;
import org.example.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с группами студентов.
 */
@org.springframework.stereotype.Service
public class Service {

    private final Repository groupRepository;
    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    @Autowired
    public Service(Repository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Находит группу по имени.
     *
     * @param name имя группы
     * @return объект GroupEntity, если группа найдена
     */
    public Optional<GroupEntity> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            logger.warn("Group name is null or empty.");
            return Optional.empty(); // Добавлена проверка на пустое имя группы
        }
        return groupRepository.findByName(name);
    }

    /**
     * Сохраняет группу в базе данных.
     *
     * @param group группа для сохранения
     * @return сохраненная группа
     */
    public GroupEntity save(GroupEntity group) {
        if (group == null) {
            logger.error("Attempt to save null group.");
            throw new IllegalArgumentException("Group cannot be null");
        }
        logger.info("Saving group: {}", group.getName());
        return groupRepository.save(group);
    }

    /**
     * Получение всех групп.
     *
     * @return список всех групп
     */
    public List<GroupEntity> findAll() {
        return groupRepository.findAll();
    }

    /**
     * Сохраняет студентов в соответствующие группы.
     * Если группа не существует, она будет создана.
     *
     * @param students Список студентов для сохранения
     */
    @Transactional
    public void saveStudentsWithGroups(List<Student> students) {
        logger.info("Saving students with their groups.");
        for (Student student : students) {
            GroupEntity group = findOrCreateGroup(student.getGroup());
            StudentEntity studentEntity = StudentMapper.toEntity(student);

            group.getStudents().add(studentEntity);
            studentEntity.setGroup(group);

            // Сохраняем группу только один раз в конце
            if (group.getStudents().size() == 1) {
                save(group); // Группа сохраняется только один раз, если студенты добавляются впервые
            }
        }
    }

    /**
     * Находит группу по имени или создает новую, если группа не найдена.
     *
     * @param groupName Имя группы
     * @return существующая или новая группа
     */
    public GroupEntity findOrCreateGroup(String groupName) {
        return groupRepository.findByName(groupName)
                .orElseGet(() -> {
                    GroupEntity group = new GroupEntity();
                    group.setName(groupName);
                    logger.info("Group with name '{}' not found, creating new group.", groupName);
                    return save(group);
                });
    }

    /**
     * Находит или создает группу, используя computeIfAbsent.
     * Этот метод создает группу только в случае её отсутствия.
     *
     * @param groupName Имя группы
     * @return существующая или новая группа
     */
    public GroupEntity findOrCreateGroupUsingMap(String groupName) {
        if (groupName == null || groupName.trim().isEmpty()) {
            logger.warn("Group name is null or empty.");
            return null; // Возвращаем null, если имя группы пустое
        }
        return groupRepository.findByName(groupName)
                .orElseGet(() -> {
                    GroupEntity group = new GroupEntity();
                    group.setName(groupName);
                    return save(group);
                });
    }
}
