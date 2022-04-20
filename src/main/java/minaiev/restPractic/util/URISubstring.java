package minaiev.restPractic.util;

import javax.servlet.http.HttpServletRequest;


public class URISubstring {

    public String uriSubstring(HttpServletRequest request){
        String getURI = request.getRequestURI();
        String[] substring = getURI.split("/");
        int size = substring.length;
        String last = substring[size-1];
        return last;
    }

}
