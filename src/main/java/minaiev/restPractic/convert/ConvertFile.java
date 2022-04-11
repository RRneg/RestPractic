package minaiev.restPractic.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import minaiev.restPractic.dto.FileDTO;
import minaiev.restPractic.model.File;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertFile {

    public FileDTO convertToFileDTO(File file) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(file.getId());
        fileDTO.setFileName(file.getFileName());
        fileDTO.setFilePath(file.getFilePath());
        fileDTO.setFileSize(file.getFileSize());
        return fileDTO;
    }

    public List<FileDTO> convertToListFileDTO(List<File> files) {
        return files.stream().map(file -> convertToFileDTO(file)).collect(Collectors.toList());
    }

    public String fileDTOToJSON(FileDTO fileDTO){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(fileDTO);
            return json;
            } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String listFilesDTOToJSON(List<FileDTO> filesDTOS){
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
