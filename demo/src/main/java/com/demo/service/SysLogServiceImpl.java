package com.demo.service;

import com.demo.dao.SysLogRepository;
import com.demo.entity.SysLogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dawn
 * @ClassName com.demo.service.SysLogServiceImpl
 * @Description
 * @date 2019/9/19 9:17
 */

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    SysLogRepository sysLogRepository;

    @Override
    public SysLogModel insertSysLogModel(SysLogModel sysLogModel) {

        return sysLogRepository.save(sysLogModel);
    }
}
