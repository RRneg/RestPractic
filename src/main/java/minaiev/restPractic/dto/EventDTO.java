package minaiev.restPractic.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonAutoDetect
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("eventStatus")
    private String eventStatus;

    @JsonProperty("updated")
    private String updated;

    @JsonProperty("created")
    private String created;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("fileName")
    private String fileName;



}
