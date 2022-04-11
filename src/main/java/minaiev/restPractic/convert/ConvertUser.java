package minaiev.restPractic.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import minaiev.restPractic.dto.UserDTO;
import minaiev.restPractic.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertUser {

    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getUserName());
        return userDTO;
    }

    public List<UserDTO> convertToListUserDTO(List<User> users) {
        return users.stream().map(user -> convertToUserDTO(user)).collect(Collectors.toList());
    }

    public String convertUserDTOToJSON(UserDTO userDTO){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(userDTO);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String convertListUsersDTOToJSON(List<UserDTO> usersDTO){
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
