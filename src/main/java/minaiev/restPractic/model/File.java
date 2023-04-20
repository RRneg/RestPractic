package minaiev.restPractic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;




@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id")
    private Integer id;

    @Column(name = "file_path")
    @JsonProperty("file_path")
    private String filePath;

    @Column(name = "file_name")
    @JsonProperty("file_name")
    private String fileName;

    @Column(name = "file_size")
    @JsonProperty("file_size")
    private Long fileSize;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "file")
    @ToString.Exclude
    private Event event;


}
