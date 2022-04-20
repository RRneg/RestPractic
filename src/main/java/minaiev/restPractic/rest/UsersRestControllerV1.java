package minaiev.restPractic.rest;

import minaiev.restPractic.convert.ConvertUser;
import minaiev.restPractic.dto.UserDTO;
import minaiev.restPractic.model.User;
import minaiev.restPractic.repository.SQLRepository.UserRepository;
import minaiev.restPractic.repository.SQLRepository.hibernate.HibernateUserRepositoryImpl;
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

    private final UserRepository userRepository = new HibernateUserRepositoryImpl();
    private final ConvertUser convert = new ConvertUser();

    public void init() throws ServletException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                String url = request.getRequestURI();
        String[] substring = url.split("/");
        int size = substring.length;
        if (substring[size - 1].equals("users")) {
            List<User> users = userRepository.getAll();
            if(users != null) {
                List<UserDTO> usersDTO = convert.convertToListUserDTO(users);
                String json = convert.convertListUsersDTOToJSON(usersDTO);
                response.setContentType("application/json");
                PrintWriter pw = response.getWriter();
                pw.write(json);
            }
            else {response.setStatus(500);}

        } else {
            try {
                User user = userRepository.getById(Integer.valueOf(substring[size]));
                if (user != null) {
                    UserDTO userDTO = convert.convertToUserDTO(user);
                    String json = convert.convertUserDTOToJSON(userDTO);
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

        Integer id = request.getIntHeader("userid");//где брать новый ид
        String userName = request.getHeader("username");// где брать новое имя
        User user = User.builder()
                .id(id)
                .userName(userName)
                .events(null).build();
        User userUPD = userRepository.update(user);
        if(userUPD.getUserName() !=userName){
            response.setStatus(500);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setUserName(request.getHeader("username"));
        user.setId(userRepository.save(user).getId());
        if(user.getId() != null) {
            UserDTO userDTO = convert.convertToUserDTO(user);
            String json = convert.convertUserDTOToJSON(userDTO);
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
            userRepository.deleteById(request.getIntHeader("userid"));
        }
        catch (SessionException e){
            response.setStatus(500);
        }
    }


    public void destroy() {

    }
}
