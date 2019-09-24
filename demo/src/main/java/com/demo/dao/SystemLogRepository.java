package com.demo.dao;

import com.demo.entity.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dawn
 * @ClassName com.demo.dao.SystemLogRepository
 * @Description
 * @date 2019/9/17 16:17
 */


public interface SystemLogRepository extends JpaRepository<SystemLog,Integer> {
}
