package minaiev.restPractic.servlet;


import minaiev.restPractic.convert.ConvertEvent;
import minaiev.restPractic.dto.EventDTO;
import minaiev.restPractic.model.Event;
import minaiev.restPractic.repository.SQLRepository.SQLRepositoryImpl.EventRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;

@WebServlet(value = "/events/*")
public class Events extends HttpServlet {
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EventRepositoryImpl eventRepository = new EventRepositoryImpl();
        String url = request.getRequestURI();
        String[] substring = url.split("/");
        int size = substring.length;
        if (substring[size - 1].equals("user")) {
            List<Event> events = eventRepository.getEventByUserId(request.getIntHeader("userid"));
            if (events != null) {
                ConvertEvent convert = new ConvertEvent();
                List<EventDTO> eventsDTO = convert.convertToListEventDTO(events);
                String json = convert.convertListEventDTOToJSON(eventsDTO);
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write(json);

            } else
                response.sendError(404, "Not Found");

        } else if (substring[size - 1].equals("file")) {
            Event event = eventRepository.getEventByFileId(request.getIntHeader("fileid"));
            if (event != null) {
                ConvertEvent convert = new ConvertEvent();
                EventDTO eventDTO = convert.convertToEventDTO(event);
                String json = convert.convertEventDTOToJSON(eventDTO);
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write(json);
            }

        } else
            response.sendError(404, "Not Found");


    }

    public void destroy() {
    }

}
