package minaiev.restPractic.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Integer fileSize;

    @OneToMany(mappedBy = "events", fetch = FetchType.LAZY)
    private List<Event> events;

    public File() {
    }

    public File(Integer id, String path, String name, Integer size) {
        this.id = id;
        this.filePath = path;
        this.fileName = name;
        this.fileSize = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", path='" + filePath + '\'' +
                ", name='" + fileName + '\'' +
                ", size=" + fileSize +
                '}';
    }
}
