package org.sacc.smis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
@EnableJpaAuditing
//@EnableGlobalMethodSecurity(securedEnabled=true)
public class StudentManagementInformationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementInformationSystemApplication.class, args);
    }

}
