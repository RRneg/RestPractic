package minaiev.restPractic.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import minaiev.restPractic.dto.UserDTO;
import minaiev.restPractic.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertUser {

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .name(user.getUserName()).build();
        return userDTO;
    }

    private List<UserDTO> convertToListUserDTO(List<User> users) {
        return users.stream().map(user -> convertToUserDTO(user)).collect(Collectors.toList());
    }

    public String convertUserToJSON(User user){
        UserDTO userDTO = convertToUserDTO(user);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(userDTO);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String convertListUsersToJSON(List<User> users){
        List<UserDTO> usersDTO = convertToListUserDTO(users);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(usersDTO);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
