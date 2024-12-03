package org.example.repository;
import org.example.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface Repository extends JpaRepository<GroupEntity, Long> {
    /**
     * ����� ��� ������ ������ �� �����.
     * @param name ��� ������.
     * @return ������ GroupEntity, ���� ������ �������.
     */
    Optional<GroupEntity> findByName(String name);
}
