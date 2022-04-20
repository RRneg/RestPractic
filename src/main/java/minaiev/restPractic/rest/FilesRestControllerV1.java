package minaiev.restPractic.rest;

import minaiev.restPractic.convert.ConvertFile;
import minaiev.restPractic.dto.FileDTO;
import minaiev.restPractic.model.Event;
import minaiev.restPractic.model.EventStatus;
import minaiev.restPractic.model.File;
import minaiev.restPractic.model.User;
import minaiev.restPractic.repository.SQLRepository.EventRepository;
import minaiev.restPractic.repository.SQLRepository.FileRepository;
import minaiev.restPractic.repository.SQLRepository.UserRepository;
import minaiev.restPractic.repository.SQLRepository.hibernate.HibernateEventRepositoryImpl;
import minaiev.restPractic.repository.SQLRepository.hibernate.HibernateFileRepositoryImpl;
import minaiev.restPractic.repository.SQLRepository.hibernate.HibernateUserRepositoryImpl;
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
        String ending = uriSubstring.uriSubstring(request);
        File file = fileRepository.getById(Integer.valueOf(ending));
        ConvertFile convert = new ConvertFile();
        FileDTO fileDTO = convert.convertToFileDTO(file);
        String json = convert.fileDTOToJSON(fileDTO);
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        pw.write(json);
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

            java.io.File file = new java.io.File(filePath);

            File file1 = File.builder()
                    .filePath(filePath)
                    .fileName(file.getName())
                    .fileSize(file.length()).build();
            file1 = fileRepository.save(file1);

            Event event = Event.builder()
                    .id(null)
                    .eventStatus(EventStatus.ACTIVE)
                    .updated(new Date())
                    .created(new Date())
                    .user((User) userRepository.getById(userId))
                    .file(file1).build();

            eventRepository.save(event);

            ConvertFile convert = new ConvertFile();
            FileDTO fileDTO = convert.convertToFileDTO(file1);
            String json = convert.fileDTOToJSON(fileDTO);
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
