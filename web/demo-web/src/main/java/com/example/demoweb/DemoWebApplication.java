package com.example.demoweb;

import com.example.demoweb.dto.NewUserDetailDTO;
import com.example.demoweb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StopWatch;

@SpringBootApplication
@AllArgsConstructor
public class DemoWebApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(DemoWebApplication.class, args);
    }


    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("run with jdbc datasource");
        for (int i = 0; i < 100; i++) {
            this.userService.addUserWithJdbcDatasource(
                    NewUserDetailDTO.builder()
                            .age(i)
                            .username("mafei-jdbc-" + i)
                            .build()
            );
        }
        stopWatch.stop();

        stopWatch.start("run with hikari datasource");
        for (int i = 0; i < 100; i++) {
            this.userService.addUserWithHikariDatasource(
                    NewUserDetailDTO.builder()
                            .age(i)
                            .username("mafei-hikari" + i)
                            .build()
            );
        }
        stopWatch.stop();
        for (StopWatch.TaskInfo taskInfo : stopWatch.getTaskInfo()) {
            System.out.println(taskInfo.getTaskName() + ": " + taskInfo.getTimeMillis());
        }
    }
}
