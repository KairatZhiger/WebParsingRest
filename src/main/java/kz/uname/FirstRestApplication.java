package kz.uname;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created By Kairat Zhiger
 * at 13.06.2023
 */
@SpringBootApplication
@RequiredArgsConstructor
public class FirstRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstRestApplication.class, args);
    }
}
