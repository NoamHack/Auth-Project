package com.example.authproject.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DatabaseBackupService {
    private static final String BACKUP_DIRECTORY = "backups";

    public static void createBackup() throws InterruptedException, IOException {
        Path backupDirPath = Paths.get(BACKUP_DIRECTORY);
        if (!Files.exists(backupDirPath)) {
            Files.createDirectories(backupDirPath);
        }

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String backupFileName = "backup_" + timestamp + ".sql";
        String backupFilePath = BACKUP_DIRECTORY + "/" + backupFileName;

        ProcessBuilder processBuilder = new ProcessBuilder(
                "mysqldump",
                "--user=root",
                "--password=admin",
                "--all-databases",
                "--result-file=" + backupFilePath
        );
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Database backup created successfully: " + backupFilePath);
        } else {
            System.out.println("Database backup failed");
        }
    }
}
