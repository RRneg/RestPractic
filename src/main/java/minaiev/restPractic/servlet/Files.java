package minaiev.restPractic.servlet;

import minaiev.restPractic.convert.ConvertFile;
import minaiev.restPractic.dto.FileDTO;
import minaiev.restPractic.model.File;
import minaiev.restPractic.repository.SQLRepository.SQLRepositoryImpl.EventRepositoryImpl;
import minaiev.restPractic.repository.SQLRepository.SQLRepositoryImpl.FileRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.annotation.WebServlet;

@WebServlet(value = "/files/*")
public class Files extends HttpServlet {
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FileRepositoryImpl fileRepository = new FileRepositoryImpl();
        File file = fileRepository.getById(request.getIntHeader("fileid"));
        ConvertFile convert = new ConvertFile();
        FileDTO fileDTO = convert.convertToFileDTO(file);
        String json = convert.fileDTOToJSON(fileDTO);
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        pw.write(json);
            }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FileRepositoryImpl fileRepository = new FileRepositoryImpl();
        EventRepositoryImpl eventRepository = new EventRepositoryImpl();
        Integer userId = request.getIntHeader("userid");

        String fileName = request.getHeader("filename");
        String directory = "/";
        String filePath = directory + fileName;

        URL url = new URL(request.getHeader("URIfile"));
        BufferedInputStream bf = new BufferedInputStream(url.openStream());
        FileOutputStream f = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bf.read(buffer, 0, 1024)) != -1) {
            f.write(buffer, 0, count);
        }
        f.close();
        bf.close();

        java.io.File file = new java.io.File(filePath);

        File file1 = new File();
        file1.setFilePath(filePath);
        file1.setFileName(file.getName());
        file1.setFileSize(file.length());
        file1 = fileRepository.save(file1);
        eventRepository.save(file1, userId);

        ConvertFile convert = new ConvertFile();
        FileDTO fileDTO = convert.convertToFileDTO(file1);
        String json = convert.fileDTOToJSON(fileDTO);
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        pw.write(json);


    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        FileRepositoryImpl fileRepository = new FileRepositoryImpl();
        Integer id = request.getIntHeader("fileid");
        fileRepository.deleteById(id);
    }

    public void destroy() {
    }
}
