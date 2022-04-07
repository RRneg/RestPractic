package minaiev.restPractic.servlet;

import minaiev.restPractic.model.User;
import minaiev.restPractic.repository.SQLRepository.GenericRepository;
import minaiev.restPractic.repository.SQLRepository.SQLRepositoryImpl.UserRepositoryImpl;
import minaiev.restPractic.repository.SQLRepository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Users extends HttpServlet {


    public void init() throws ServletException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        String url = request.getRequestURI();
        String[] substring  = url.split("/");
        int size = substring.length;
        if (substring[size-1].equals("users")){
            List<User> users = userRepository.getAll();
//перевести юзера в джейсон и отправить в теле

        }
        else {
            try {

                User user = userRepository.getById(Integer.valueOf(substring[size]));
                //перевести юзера в джейсон и отправить в теле
            }
            catch (ClassCastException e){
                response.sendError(404, "Not Found");
            }
            }

    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        User user = new User();
        user.setUserName(request.getHeader("username"));
        user.setId(userRepository.save(user).getId());
        response.addIntHeader("userid", user.getId());
        // перевести юзера в джейсон и отправить в теле

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        userRepository.update(new User(request.getIntHeader("userid"),request.getHeader("username")));
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        userRepository.deleteById(request.getIntHeader("userid"));
    }


    public void destroy() {

    }
}
