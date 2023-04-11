package minaiev.restPractic.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;


}
