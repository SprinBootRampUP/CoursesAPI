package org.course.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Hello World!");
        log.info("Sample Log");
//
//         try{
//
//             while (true){
//                 Runnable r =() -> {
//                     while (true) {
//                     }
//                 };
//                     new Thread(r).start();
//                     Thread.sleep(5000);
//                 }
//
//         } catch (Exception e){
//             return;
//         }

    }
}