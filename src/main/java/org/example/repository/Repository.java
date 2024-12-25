package org.example.repository;

import org.example.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link GroupEntity}.
 * Этот интерфейс использует Spring Data JPA для выполнения стандартных операций с базой данных.
 */
public interface Repository extends JpaRepository<GroupEntity, Long> {

    /**
     * Ищет группу по имени.
     *
     * @param name имя группы, по которому будет произведен поиск.
     * @return {@link Optional} с найденной группой, если такая существует, или {@link Optional#empty()} если группа не найдена.
     */
    Optional<GroupEntity> findByName(String name);
}
