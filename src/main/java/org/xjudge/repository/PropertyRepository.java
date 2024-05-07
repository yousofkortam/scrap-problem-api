package org.xjudge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xjudge.entity.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
