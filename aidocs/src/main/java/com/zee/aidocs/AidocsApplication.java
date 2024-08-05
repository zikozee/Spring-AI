package com.zee.aidocs;

import com.zee.aidocs.config.HintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.shell.command.annotation.CommandScan;

@ImportRuntimeHints(HintsRegistrar.class)
//@CommandScan
@SpringBootApplication
public class AidocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AidocsApplication.class, args);
    }

}
