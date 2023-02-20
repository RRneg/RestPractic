package minaiev.restPractic.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@JsonAutoDetect
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {

    private Integer id;
    private String eventStatus;
    private String updated;
    private String created;
    private String userName;
    private String fileName;
    private String filePath;


}
