package minaiev.restPractic.service;

import minaiev.restPractic.model.Event;
import minaiev.restPractic.model.EventStatus;
import minaiev.restPractic.model.File;
import minaiev.restPractic.model.User;
import minaiev.restPractic.repository.hibernateRepository.hibernate.HibernateFileRepositoryImpl;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class FileService {

    private final HibernateFileRepositoryImpl fileRepository = new HibernateFileRepositoryImpl();
    private final UserService userService = new UserService();
    private final EventService eventService = new EventService();
    private final String fileDisk = "/";


    public File getById(Integer fileId) {

        return fileRepository.getById(fileId);
    }

    public File updateFile(File file) {
        return file;
    }

    public void deleteFileById(Integer fileId) {
        eventService.setDelStatus(fileId);
    }


    public List<File> getAllFiles(Integer userId) {
        HibernateFileRepositoryImpl.setUser_Id(userId);
        return fileRepository.getAll();
    }

    private void saveFile(String fileName, URL url) throws IOException {
        BufferedInputStream bf = new BufferedInputStream(url.openStream());
        String filePath = fileDisk + fileName;
        FileOutputStream f = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bf.read(buffer, 0, 1024)) != -1) {
            f.write(buffer, 0, count);
        }
    }

    public File save(File file, Integer userId, URL url) throws IOException {
        saveFile(file.getFileName(), url);
        file.setFilePath(fileDisk + file.getFileName());

        Event event = Event.builder()
                .id(null)
                .eventStatus(EventStatus.ACTIVE)
                .updated(new Date())
                .created(new Date())
                .user((User) userService.getById(userId))
                .file(file).build();

        eventService.save(event);

        return fileRepository.save(file);
    }

}
