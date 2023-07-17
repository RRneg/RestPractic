package minaiev.restPractic.rest;

import minaiev.restPractic.convert.ConvertFile;
import minaiev.restPractic.model.File;
import minaiev.restPractic.service.FileService;
import minaiev.restPractic.util.URISubstring;
import org.hibernate.SessionException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;



@WebServlet(value = "/api/v1/files/*")
public class FilesRestControllerV1 extends HttpServlet {

    private final FileService fileService = new FileService();
    private final URISubstring uriSubstring = new URISubstring();
    private final ConvertFile convert = new ConvertFile();



    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer header = request.getIntHeader("userId");
        if (header == null) {
            String ending = uriSubstring.uriSubstring(request);
            File file = fileService.getById(Integer.valueOf(ending));
            if (file != null) {
                ConvertFile convert = new ConvertFile();
                String json = convert.convertFileToJSON(file);
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write(json);
            } else {
                response.setStatus(500);
            }
        } else {
            List<File> files = fileService.getAllFiles(header);
            if (files != null) {
                ConvertFile convert = new ConvertFile();
                String json = convert.convertListFilesToJSON(files);
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write(json);
            } else {
                response.setStatus(500);
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
        File file = fileService.saveFiles(request);
        String json = convert.convertFileToJSON(file);
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write(json);
            }

        catch(IOException e) {
            response.setStatus(500);
        }
    }



    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.valueOf(uriSubstring.uriSubstring(request));
        try {
            fileService.deleteFileById(id);
        } catch (SessionException e) {
            response.setStatus(500);
        }
    }

    public void destroy() {
    }



}
