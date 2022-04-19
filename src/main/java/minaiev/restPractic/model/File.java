package minaiev.restPractic.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_name")
    private String fileName;



    @Column(name = "file_size")
    private Long fileSize;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "event")
    private Event event;

}
