package minaiev.restPractic.rest;

import minaiev.restPractic.convert.ConvertFile;
import minaiev.restPractic.model.Event;
import minaiev.restPractic.model.EventStatus;
import minaiev.restPractic.model.File;
import minaiev.restPractic.model.User;
import minaiev.restPractic.repository.hibernateRepository.EventRepository;
import minaiev.restPractic.repository.hibernateRepository.FileRepository;
import minaiev.restPractic.repository.hibernateRepository.UserRepository;
import minaiev.restPractic.repository.hibernateRepository.hibernate.HibernateEventRepositoryImpl;
import minaiev.restPractic.repository.hibernateRepository.hibernate.HibernateFileRepositoryImpl;
import minaiev.restPractic.repository.hibernateRepository.hibernate.HibernateUserRepositoryImpl;
import minaiev.restPractic.util.URISubstring;
import org.hibernate.SessionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;

@WebServlet(value = "/api/v1/files/*")
public class FilesRestControllerV1 extends HttpServlet {

    private final UserRepository userRepository = new HibernateUserRepositoryImpl();
    private final FileRepository fileRepository = new HibernateFileRepositoryImpl();
    private final EventRepository eventRepository = new HibernateEventRepositoryImpl();
    private final URISubstring uriSubstring = new URISubstring();

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer header = request.getIntHeader("userId");
        if (header == null) {
            String ending = uriSubstring.uriSubstring(request);
            File file = fileRepository.getById(Integer.valueOf(ending));
            if(file != null) {
                ConvertFile convert = new ConvertFile();
                String json = convert.fileoJSON(file);
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write(json);
            }
            else{response.setStatus(500);}
        }
        else {
            HibernateFileRepositoryImpl.setUser_Id(header);
            List<File> files = fileRepository.getAll();
            if (files != null){
                ConvertFile convert = new ConvertFile();
                String json = convert.listFilesToJSON(files);
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write(json);
            }
            else {response.setStatus(500);}
        }
            }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = request.getIntHeader("userid");
        String fileName = uriSubstring.uriSubstring(request);
        String directory = "/";
        String filePath = directory + fileName;
        URL url = new URL(request.getHeader("URIfile"));

        try (BufferedInputStream bf = new BufferedInputStream(url.openStream());
             FileOutputStream f = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = bf.read(buffer, 0, 1024)) != -1) {
                f.write(buffer, 0, count);
            }

            java.io.File fileIO = new java.io.File(filePath);

            File file = File.builder()
                    .filePath(filePath)
                    .fileName(fileIO.getName())
                    .fileSize(fileIO.length()).build();
            file = fileRepository.save(file);

            Event event = Event.builder()
                    .id(null)
                    .eventStatus(EventStatus.ACTIVE)
                    .updated(new Date())
                    .created(new Date())
                    .user((User) userRepository.getById(userId))
                    .file(file).build();

            eventRepository.save(event);

            ConvertFile convert = new ConvertFile();
            String json = convert.fileoJSON(file);
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.write(json);
        }
        catch (IOException e){
            response.setStatus(500);
        }


    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.valueOf(uriSubstring.uriSubstring(request));
        try {
            fileRepository.deleteById(id);
        }
        catch (SessionException e){
            response.setStatus(500);
        }
    }

    public void destroy() {
    }
}
