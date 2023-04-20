package minaiev.restPractic.service;

import minaiev.restPractic.model.Event;
import minaiev.restPractic.model.EventStatus;
import minaiev.restPractic.model.File;
import minaiev.restPractic.repository.hibernateRepository.hibernate.HibernateFileRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@MultipartConfig(location = "C:\\Users\\User\\Desktop\\RestPractic1\\RestPractic (1)\\RestPractic\\src\\main\\resources\\uploads")
public class FileService {

    private final HibernateFileRepositoryImpl fileRepository = new HibernateFileRepositoryImpl();
    private final UserService userService = new UserService();
    private final EventService eventService = new EventService();



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



    public List<File> save(HttpServletRequest request) throws IOException, ServletException {
        int userId = request.getIntHeader("userid");
        String fileName;
        long fileSize;
        List<File> files = new ArrayList<>();

        for (Part part : request.getParts()) {
                fileName = part.getName();
                fileSize = part.getSize();
                part.write(fileName);

                File file = File.builder()
                        .id(null)
                        .filePath("src/main/resources/uploads")
                        .fileName(fileName)
                        .fileSize(fileSize)
                        .build();
                File createdFile = fileRepository.save(file);
                files.add(createdFile);


                Event event = Event.builder()
                    .id(null)
                    .eventStatus(EventStatus.ACTIVE)
                    .updated(new Date())
                    .created(new Date())
                    .userId(userService.getById(userId))
                    .fileId(file).build();

            eventService.save(event);

            }
        return files;
    }

}
