package minaiev.restPractic.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO {
    private Integer id;
    private String filePath;
    private String fileName;
    private Long fileSize;

}
