package fr.m2i.medical.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class StorageServiceImpl implements StorageService{

    @Override
    public void init() {

    }

    @Override
    public String store(MultipartFile fileUploaded , String uploadPath ) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        File file = new File( uploadPath + "\\" + fileUploaded.getOriginalFilename() );
        byte[] data = fileUploaded.getBytes();

        try (FileOutputStream fos = new FileOutputStream(file))
        {
            fos.write(data);
            System.out.println("Successfully written data to the file");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return fileUploaded.getOriginalFilename();
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}