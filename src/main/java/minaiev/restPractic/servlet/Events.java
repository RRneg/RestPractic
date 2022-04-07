package minaiev.restPractic.servlet;

import minaiev.restPractic.model.Event;
import minaiev.restPractic.repository.SQLRepository.SQLRepositoryImpl.EventRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Events extends HttpServlet {
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        EventRepositoryImpl eventRepository = new EventRepositoryImpl();
        String url = request.getRequestURI();
        String[] substring  = url.split("/");
        int size = substring.length;
        if (substring[size-1].equals("user")){
            List<Event> events = eventRepository.getEventByUserId(request.getIntHeader("userid"));
            //перевести в json и отправить
        }
        else if (substring[size-1].equals("file")){
            Event event = eventRepository.getEventByFileId(request.getIntHeader("fileid"));
//перевести в json и отправить
        }
        else
            response.sendError(404, "Not Found");



    }

    public void destroy() {
    }

}
