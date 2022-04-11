package minaiev.restPractic.servlet;

import minaiev.restPractic.convert.ConvertUser;
import minaiev.restPractic.dto.UserDTO;
import minaiev.restPractic.model.User;
import minaiev.restPractic.repository.SQLRepository.SQLRepositoryImpl.UserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.annotation.WebServlet;

@WebServlet(value = "/users/*")
public class Users extends HttpServlet {


    public void init() throws ServletException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        String url = request.getRequestURI();
        String[] substring = url.split("/");
        int size = substring.length;
        if (substring[size - 1].equals("users")) {
            List<User> users = userRepository.getAll();
            ConvertUser convert = new ConvertUser();
            List<UserDTO> usersDTO = convert.convertToListUserDTO(users);
            String json = convert.convertListUsersDTOToJSON(usersDTO);
            response.setContentType("application/json");
            PrintWriter pw = response.getWriter();
            pw.write(json);

        } else {
            try {
                User user = userRepository.getById(Integer.valueOf(substring[size]));
                if (user != null) {
                    ConvertUser convert = new ConvertUser();
                    UserDTO userDTO = convert.convertToUserDTO(user);
                    String json = convert.convertUserDTOToJSON(userDTO);
                    response.setContentType("application/json");
                    PrintWriter pw = response.getWriter();
                    pw.write(json);

                } else
                    response.sendError(404, "Not Found");
            } catch (ClassCastException e) {
                response.sendError(404, "Not Found");
            }
        }

    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        Integer id = request.getIntHeader("userid");
        String userName = request.getHeader("username");
        userRepository.update(new User(id, userName));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User user = new User();
        user.setUserName(request.getHeader("username"));
        user.setId(userRepository.save(user).getId());
        ConvertUser convert = new ConvertUser();
        UserDTO userDTO = convert.convertToUserDTO(user);
        String json = convert.convertUserDTOToJSON(userDTO);
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        pw.write(json);


    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        userRepository.deleteById(request.getIntHeader("userid"));
    }


    public void destroy() {

    }
}
