package org.xjudge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xjudge.entity.Value;

@Repository
public interface ValueRepository extends JpaRepository<Value, Long> {
}
