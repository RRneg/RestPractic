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
import java.net.http.HttpRequest;
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



    public File save(File file, Integer userId) throws IOException {

        File file1 = fileRepository.save(file);

        Event event = Event.builder()
                .id(null)
                .eventStatus(EventStatus.ACTIVE)
                .updated(new Date())
                .created(new Date())
                .userId(userId)
                .fileId(file1.getId()).build();

        eventService.save(event);

        return file1;
    }

}
