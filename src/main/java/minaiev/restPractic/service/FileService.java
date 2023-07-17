package minaiev.restPractic.service;

import minaiev.restPractic.model.Event;
import minaiev.restPractic.model.EventStatus;
import minaiev.restPractic.model.File;
import minaiev.restPractic.repository.hibernateRepository.hibernate.HibernateFileRepositoryImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.util.Date;
import java.util.List;

public class FileService {

    private final HibernateFileRepositoryImpl fileRepository = new HibernateFileRepositoryImpl();
    private final UserService userService = new UserService();
    private final EventService eventService = new EventService();

    private final static String FILE_UPLOAD_PATH = "src/main/resources/uploads";


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
        String fileName = getFileName(request);
        String path = FILE_UPLOAD_PATH +  "/" + fileName;
        int userId = request.getIntHeader("userid");
        java.io.File fileIO = new java.io.File(path);

        FileOutputStream fos = new FileOutputStream(fileIO);
        byte[] buffer = new byte[8096];
        InputStream fis =request.getInputStream();
        int len = fis.read(buffer);
        while (len != -1) {
            fos.write(buffer, 0, len);
            len = fis.read(buffer);
        }
        fos.close();
        fis.close();

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

    public File saveFiles (HttpServletRequest request) throws IOException, ServletException{
        int userId = request.getIntHeader("userid");

        File createdFile = new File();

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List items = upload.parseRequest(request);
            FileItem item = (FileItem)items.get(0);
            if (!item.isFormField()) {
                java.io.File fileIO = new java.io.File(FILE_UPLOAD_PATH +  "/" + item.getName());
                item.write(fileIO);
                File file = File.builder()
                        .id(null)
                        .filePath(FILE_UPLOAD_PATH +  "/" + item.getName())
                        .fileName(item.getName())
                        .fileSize(item.getSize())
                        .build();

                createdFile = fileRepository.save(file);

                Event event = Event.builder()
                        .id(null)
                        .eventStatus(EventStatus.ACTIVE)
                        .updated(new Date())
                        .created(new Date())
                        .user(userService.getById(userId))
                        .file(file).build();

                eventService.save(event);


            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
