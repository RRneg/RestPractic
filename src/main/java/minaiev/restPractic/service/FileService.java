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
        int len = request.getInputStream().read(buffer);
        while (len != -1) {
            fos.write(buffer, 0, len);
            len = request.getInputStream().read(buffer);
        }
        fos.close();
        request.getInputStream().close();

        File file = File.builder()
                .id(null)
                .filePath("src/main/resources/uploads")
                .fileName(fileIO.getName())
                .fileSize(fileIO.length())
                .build();

        File createdFile = fileRepository.save(file);

        Event event = Event.builder()
                .id(null)
                .eventStatus(EventStatus.ACTIVE)
                .updated(new Date())
                .created(new Date())
                .userId(userService.getById(userId))
                .fileId(file).build();

        eventService.save(event);

        return createdFile;
    }



    private String getFileName(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        String line;
        String line1 = null;
        ArrayList<String> bodyLines = new ArrayList<>();


        while ((line = br.readLine()) != null) {
            bodyLines.add(line);
        }

        for (String lineIter: bodyLines) {
            if (lineIter.contains("Content-Disposition"))
               line1 = lineIter;
        }

        if (line1 != null) {
            String[] withFileName = line1.substring(0, line1.length() - 1).split("\'");
            return withFileName[withFileName.length-1];
        }
        else
            return line1;

    }

}
