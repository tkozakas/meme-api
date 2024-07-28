package org.churk.memeapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.churk.memeapi.configuration.DownloadMediaProperties;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@AllArgsConstructor
public class DownloadService {
    private static final String DEBUG_INFO = "panic";
    private static final String FPS = "12";
    private static final String COMPRESSION_QUALITY = "10";
    private static final int BUFFER_SIZE = 5048 * 1024;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final DownloadMediaProperties downloadMediaProperties;

    public File downloadFile(String url) {
        try {
            String extension = url.substring(url.lastIndexOf("."));
            String fileName = generateUniqueFileName(extension);
            return download(url, fileName);
        } catch (Exception e) {
            log.error("Error while downloading file", e);
            return null;
        }
    }

    public File download(String url, String fileName) throws ExecutionException, InterruptedException {
        return executorService.submit(() -> {
            String downloadPath = downloadMediaProperties.getPath() + File.separator + fileName;
            log.info("Downloading file from {}", url);
            try (InputStream in = new BufferedInputStream(new URL(url).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(downloadPath)) {
                byte[] dataBuffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, BUFFER_SIZE)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                log.info("Downloaded file saved to: {}", downloadPath);
                return new File(downloadPath);
            } catch (IOException e) {
                return null;
            }
        }).get();
    }

    public String generateUniqueFileName(String extension) {
        return UUID.randomUUID() + extension;
    }
}
