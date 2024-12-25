package org.example.repository;

import org.example.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * ����������� ��� ������ � ���������� {@link GroupEntity}.
 * ���� ��������� ���������� Spring Data JPA ��� ���������� ����������� �������� � ����� ������.
 */
public interface Repository extends JpaRepository<GroupEntity, Long> {

    /**
     * ���� ������ �� �����.
     *
     * @param name ��� ������, �� �������� ����� ���������� �����.
     * @return {@link Optional} � ��������� �������, ���� ����� ����������, ��� {@link Optional#empty()} ���� ������ �� �������.
     */
    Optional<GroupEntity> findByName(String name);
}
