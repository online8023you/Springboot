package com.demo.dao;

import com.demo.entity.SysLogModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dawn
 * @ClassName com.demo.dao.SysLogRepository
 * @Description
 * @date 2019/9/19 9:16
 */


public interface SysLogRepository extends JpaRepository<SysLogModel,Integer> {
}
