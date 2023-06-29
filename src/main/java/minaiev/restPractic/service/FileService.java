package minaiev.restPractic.service;

import minaiev.restPractic.model.Event;
import minaiev.restPractic.model.EventStatus;
import minaiev.restPractic.model.File;
import minaiev.restPractic.repository.hibernateRepository.hibernate.HibernateFileRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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


    public File save(HttpServletRequest request) throws IOException, ServletException {
        String path = "src/main/resources/uploads" +  "/" + getFileName(request);
        int userId = request.getIntHeader("userid");
        java.io.File fileIO = new java.io.File(path);

        FileOutputStream fos = new FileOutputStream(fileIO);
        byte[] buffer = new byte[8096];
        InputStream is = request.getInputStream();
        int len = is.read(buffer);
        while (len != -1) {
            fos.write(buffer, 0, len);
            len = is.read(buffer);
        }
        fos.close();
        is.close();

        File file = File.builder()
                .id(null)
                .filePath(path)
                .fileName(fileIO.getName())
                .fileSize(fileIO.length())
                .build();

        File createdFile = fileRepository.save(file);

        Event event = Event.builder()
                .id(null)
                .eventStatus(EventStatus.ACTIVE)
                .updated(new Date())
                .created(new Date())
                .user(userService.getById(userId))
                .file(file).build();

        eventService.save(event);

        return createdFile;
    }



    private String getFileName(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        String line;
        String line1 = null;



        while ((line = br.readLine()) != null) {
            if (line.contains("Content-Disposition")){
            line1 = line;
            break;
            }
        }

        br.close();



        if (line1 != null) {
            String[] withFileName = line1.split("\"");
            return withFileName[3];
        }
        else
            return line1;

    }

}
