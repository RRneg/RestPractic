package minaiev.restPractic.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class FileDTO {
    private Integer id;
    private String filePath;
    private String fileName;
    private Long fileSize;

    public FileDTO() {
    }

    public FileDTO(Integer id, String filePath, String fileName, Long fileSize) {
        this.id = id;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileSize = fileSize;
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

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "FileDTO{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
