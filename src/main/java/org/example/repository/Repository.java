package org.example.repository;
import org.example.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface Repository extends JpaRepository<GroupEntity, Long> {
    /**
     * Метод для поиска группы по имени.
     * @param name имя группы.
     * @return объект GroupEntity, если группа найдена.
     */
    Optional<GroupEntity> findByName(String name);
}
