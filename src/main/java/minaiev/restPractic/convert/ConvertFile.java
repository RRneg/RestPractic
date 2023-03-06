package minaiev.restPractic.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import minaiev.restPractic.dto.FileDTO;
import minaiev.restPractic.model.File;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertFile {

    private FileDTO convertToFileDTO(File file) {
        FileDTO fileDTO = FileDTO.builder()
                .id(file.getId())
                .filePath(file.getFilePath())
                .fileName(file.getFileName())
                .fileSize(file.getFileSize()).build();
        return fileDTO;
    }

    private List<FileDTO> convertToListFileDTO(List<File> files) {
        return files.stream().map(file -> convertToFileDTO(file)).collect(Collectors.toList());
    }

    public String fileoJSON(File file){
        FileDTO fileDTO = convertToFileDTO(file);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(fileDTO);
            return json;
            } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String listFilesToJSON(List<File> files){
        List<FileDTO> filesDTOS =  convertToListFileDTO(files);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(filesDTOS);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
