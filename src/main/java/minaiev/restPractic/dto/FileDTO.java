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
public class FileDTO {
    private Integer id;
    private String filePath;
    private String fileName;
    private Long fileSize;

}
