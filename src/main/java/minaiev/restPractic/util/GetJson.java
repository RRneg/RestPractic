package minaiev.restPractic.util;

import minaiev.restPractic.convert.ConvertFile;
import minaiev.restPractic.convert.ConvertUser;
import minaiev.restPractic.model.File;
import minaiev.restPractic.model.User;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class GetJson {

    private final ConvertUser convertUser = new ConvertUser();
    private final ConvertFile convertFile = new ConvertFile();



    public User getUserJSON(HttpServletRequest request){
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                sb.append(line);
            return convertUser.convertJSONToUser(sb.toString());
        }
        catch (IOException e){
            return null;
        }
    }

    public File getFileJson(HttpServletRequest request){
        StringBuffer sb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                sb.append(line);
            return convertFile.convertJsonToFile(sb.toString());
        }
        catch (IOException e){
            return null;
        }
    }
}
