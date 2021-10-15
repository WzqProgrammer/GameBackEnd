package com.wzqCode.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import javax.servlet.http.HttpServletRequest;

public class ServletUtil {

    public static int getIdByRequest(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token == null)
            return 0;

        DecodedJWT decodedJWT;
        try{
            decodedJWT = JwtUtil.verify(token);
        }catch (Exception e){
            return -1;
        }

        Integer id = decodedJWT.getClaim("id").asInt();
        return id;
    }
}
