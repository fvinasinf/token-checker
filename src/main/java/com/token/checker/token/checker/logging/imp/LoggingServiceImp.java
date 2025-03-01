package com.token.checker.token.checker.logging.imp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.token.checker.token.checker.logging.LoggingService;
import com.token.checker.token.checker.logging.enu.Level;

@Service
public class LoggingServiceImp implements LoggingService {

    @Value("${logging.maxlevel:ERROR}")
    private String maxLevel;
    @Value("${logging.path}")
    private String path;
    @Value("${logging.filename}")
    private String fileName;
    @Value("${logging.format}")
    private String format;

    @Override
    public void error(String message){
        log(Level.ERROR, message);
    }

    @Override
    public void warn(String message){
        log(Level.WARN, message);
    }

    @Override
    public void info(String message){
        log(Level.INFO, message);
    }

    @Override
    public void debug(String message){
        log(Level.DEBUG, message);
    }

    @Override
    public void log(Level level, String message) {
        if (Level.checkLevel(level, Level.valueOf(maxLevel))) {
            return;
        }
        try {
            Files.createDirectories(Path.of(path, ""));
            Files.writeString(
                Path.of(path, fileName), 
                String.format(
                    format,
                    LocalDateTime.now().toString(),
                    level.name(),
                    message
                ), 
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
            Files.writeString(
                Path.of(path, fileName), 
                System.lineSeparator(), 
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
