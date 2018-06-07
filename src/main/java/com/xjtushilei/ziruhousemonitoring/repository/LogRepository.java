package com.xjtushilei.ziruhousemonitoring.repository;

import com.xjtushilei.ziruhousemonitoring.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author scriptshi
 * 2018/6/6
 */
public interface LogRepository extends JpaRepository<Log, Long> {

    long countByLog(String log);

    long countByType(String type);
}