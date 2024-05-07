package org.xjudge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xjudge.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
}
