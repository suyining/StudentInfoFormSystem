package org.sacc.smis.service.impl;

import org.sacc.smis.entity.Application;
import org.sacc.smis.entity.ApplicationItem;
import org.sacc.smis.entity.User;
import org.sacc.smis.mapper.ApplicationMapper;
import org.sacc.smis.service.ApplicationService;
import org.sacc.smis.util.GetNullPropertyNamesUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationMapper applicationMapper;


    @Override
    public boolean addApplication(Application application) {
        applicationMapper.save(application);
        return true;
    }

    @Override
    public boolean deleteApplication(Integer applicationId) {
        applicationMapper.deleteById(applicationId);
        return true;
    }

    @Override
    public boolean updateApplication(Application application) {
//        if (applicationMapper.existsById(application.getId())){
//            Application oldApplication = applicationMapper.getOne(application.getId());
//            oldApplication.setName(application.getName());
//            oldApplication.setUpdatedAt(application.getUpdatedAt());
//            oldApplication.setUserId(application.getUserId());
//        }
        Application a = applicationMapper.findByPrimaryKey(application.getId());
        BeanUtils.copyProperties(application, a, GetNullPropertyNamesUtil.getNullPropertyNames(application));
        applicationMapper.save(a);
        return true;
    }

    @Override
    public List<Application> findAllApplications() {
        return applicationMapper.findAll();
    }

    @Override
    public Application getApplicationById(Integer applicationId) {
        return applicationMapper.getOne(applicationId);
    }
}
