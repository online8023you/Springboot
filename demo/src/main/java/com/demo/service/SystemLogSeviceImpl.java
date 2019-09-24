package com.demo.service;

import com.demo.dao.SystemLogRepository;
import com.demo.entity.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Dawn
 * @ClassName com.demo.service.SystemLogSeviceImpl
 * @Description
 * @date 2019/9/17 16:24
 */

@Service
public class SystemLogSeviceImpl implements SystemLogService {

    @Autowired
    SystemLogRepository systemLogRepository;

    @Override
    public SystemLog insertSystemLog(SystemLog systemLog) {
        return systemLogRepository.save(systemLog);
    }
}
