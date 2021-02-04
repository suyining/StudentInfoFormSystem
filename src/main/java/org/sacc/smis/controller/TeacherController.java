package org.sacc.smis.controller;

import org.sacc.smis.entity.Application;
import org.sacc.smis.model.RestResult;
import org.sacc.smis.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured("ROLE_TEACHER")
public class TeacherController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping("/application/{application_id}")
    public RestResult<Application> getApplication(@PathVariable("application_id") Integer applicationId) {
        return RestResult.success(200, applicationService.getApplicationById(applicationId));
    }

    @PostMapping("/application")
    public RestResult<Boolean> addApplication(@RequestBody Application application) {
        return RestResult.success(200, applicationService.addApplication(application));
    }

    @DeleteMapping("/application/{application_id}")
    public RestResult<Boolean> deleteApplication(@PathVariable("application_id") Integer applicationId) {
        return RestResult.success(200, applicationService.deleteApplication(applicationId));
    }

    @PutMapping("/application")
    public RestResult<Boolean> updateApplication(@RequestBody Application application) {
        return RestResult.success(200, applicationService.updateApplication(application));
    }

    @GetMapping("/application/all")
    public RestResult<List<Application>> findAllApplication() {
        return RestResult.success(200, applicationService.findAllApplications());
    }

}
