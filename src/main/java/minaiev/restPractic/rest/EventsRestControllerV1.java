package minaiev.restPractic.rest;


import minaiev.restPractic.convert.ConvertEvent;
import minaiev.restPractic.model.Event;
import minaiev.restPractic.repository.hibernateRepository.EventRepository;
import minaiev.restPractic.repository.hibernateRepository.hibernate.HibernateEventRepositoryImpl;
import minaiev.restPractic.service.EventService;
import minaiev.restPractic.util.URISubstring;

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

    private final EventService eventService = new EventService();
    private final EventRepository eventRepository = new HibernateEventRepositoryImpl();
    private final ConvertEvent convert = new ConvertEvent();
    private final URISubstring uriSubstring = new URISubstring();

    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ending = uriSubstring.uriSubstring(request);
        if (ending == "files") {
            List<Event> events = eventService.getEventByUserId(request.getIntHeader("userId"));
            if (events != null) {
                String json = convert.convertListEventToJSON(events);
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write(json);

            }
            else {
                response.setStatus(500);
            }
        }
        else {
            if (ending == "file"){
                Event event = eventService.getEventByFileId(request.getIntHeader("fileId"));
                if(event != null){
                    String json = convert.convertEventToJSON(event);
                    response.setContentType("application/json");
                    PrintWriter pw = response.getWriter();
                    pw.write(json);

                }
                else {
                    response.setStatus(500);
                }
            }
            }
    }



    public void destroy() {
    }

}
