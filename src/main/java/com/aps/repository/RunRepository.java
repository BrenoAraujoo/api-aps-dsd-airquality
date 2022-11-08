package com.aps.repository;

import com.aps.model.Run;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RunRepository extends JpaRepository<Run,Long> {

    List<Run> findByUserName(String name);
}
