package minaiev.restPractic.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;


@JsonAutoDetect
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private Integer id;
    private String name;


}
