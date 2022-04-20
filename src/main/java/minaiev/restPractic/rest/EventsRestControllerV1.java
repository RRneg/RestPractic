package minaiev.restPractic.rest;


import minaiev.restPractic.convert.ConvertEvent;
import minaiev.restPractic.dto.EventDTO;
import minaiev.restPractic.model.Event;
import minaiev.restPractic.repository.SQLRepository.EventRepository;
import minaiev.restPractic.repository.SQLRepository.hibernate.HibernateEventRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;

@WebServlet(value = "/api/v1/events/*")
public class EventsRestControllerV1 extends HttpServlet {

    private final EventRepository eventRepository = new HibernateEventRepositoryImpl();
    private final ConvertEvent convert = new ConvertEvent();

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        String[] substring = url.split("/");
        int size = substring.length;
        if (substring[size - 1].equals("user")) {
            List<Event> events = eventRepository.getEventByUserId(request.getIntHeader("userid"));
            if (events != null) {
                List<EventDTO> eventsDTO = convert.convertToListEventDTO(events);
                String json = convert.convertListEventDTOToJSON(eventsDTO);
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write(json);

            } else {
                response.setStatus(500);
            }
        }
    }



    public void destroy() {
    }

}
