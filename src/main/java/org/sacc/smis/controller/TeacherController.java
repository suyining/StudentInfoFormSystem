package org.sacc.smis.controller;

import org.sacc.smis.entity.Application;
import org.sacc.smis.mapper.ApplicationMapper;
import org.sacc.smis.model.RestResult;
import org.sacc.smis.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TeacherController {

    @Autowired
    ApplicationService applicationService;


    @Valid
    @GetMapping("/application/{application_id}")
    public RestResult<Application> getApplication(@PathVariable("application_id") Integer applicationId){
        return RestResult.success(200,applicationService.getApplicationById(applicationId));
    }


    @PostMapping("/application")
    public RestResult<Boolean> addApplication(@RequestBody Application application) {
        //辅导员提交表单
        return RestResult.success(200,applicationService.addApplication(application));
    }

    @DeleteMapping("/application/{application_id}")
    public RestResult<Boolean> deleteApplication(@PathVariable("application_id") Integer applicationId){
        return RestResult.success(200,applicationService.deleteApplication(applicationId));
    }

    @GetMapping("/application/all")
    public RestResult<List<Application>> findAllApplication(){
        return RestResult.success(200,applicationService.findAllApplications());
    }

}
