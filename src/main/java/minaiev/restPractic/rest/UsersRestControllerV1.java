package minaiev.restPractic.rest;

import minaiev.restPractic.convert.ConvertUser;
import minaiev.restPractic.model.User;
import minaiev.restPractic.service.UserService;
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

@WebServlet(value = "/api/v1/users/*")
public class UsersRestControllerV1 extends HttpServlet {

    private final UserService userService = new UserService();
    private final ConvertUser convert = new ConvertUser();
    private final URISubstring uriSubstring = new URISubstring();

    public void init() throws ServletException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ending = uriSubstring.uriSubstring(request);
        if (ending.equals("users")) {
            List<User> users = userService.getAll();
            if(users != null) {
                String json = convert.convertListUsersToJSON(users);
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write(json);
            }
            else {response.setStatus(500);}

        } else {
            try {
                User user = userService.getById(Integer.valueOf(ending));
                if (user != null) {
                    String json = convert.convertUserToJSON(user);
                    response.setContentType("application/json");
                    PrintWriter pw = response.getWriter();
                    pw.write(json);

                } else
                    response.setStatus(404);
            } catch (ClassCastException e) {
                response.setStatus(500);
            }
        }

    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String jsonUser = request.getParameter("user");
       User user = convert.convertJSONToUser(jsonUser);
       User updatingUser = userService.update(user);
        if(updatingUser.getUserName() !=user.getUserName()){
                response.setStatus(500);
             }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonUser = request.getParameter("user");
        User user = convert.convertJSONToUser(jsonUser);
        User savingUser = userService.save(user);

        if(savingUser.getId() != null) {
            String json = convert.convertUserToJSON(savingUser);
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.write(json);
        }
        else {
            response.setStatus(500);
        }


    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            userService.deleteById(request.getIntHeader("userid"));
        }
        catch (SessionException e){
            response.setStatus(500);

        }
    }


    public void destroy() {

    }
}
