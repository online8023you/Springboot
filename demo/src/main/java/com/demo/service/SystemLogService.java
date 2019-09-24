package com.demo.service;

import com.demo.entity.SystemLog;

import java.util.Date;

/**
 * @author Dawn
 * @ClassName com.demo.service.SystemLogService
 * @Description
 * @date 2019/9/17 16:17
 */


public interface SystemLogService {
    SystemLog insertSystemLog(SystemLog systemLog);
}
