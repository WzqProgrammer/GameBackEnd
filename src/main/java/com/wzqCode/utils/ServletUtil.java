package com.wzqCode.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.wzqCode.exception.gameException.TokenErrorException;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {

    public static int getIdByRequest(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token == null)
            throw new TokenErrorException();

        DecodedJWT decodedJWT;
        try{
            decodedJWT = JwtUtil.verify(token);
        }catch (Exception e){
            throw new TokenErrorException();
        }

        Integer id = decodedJWT.getClaim("id").asInt();
        return id;
    }
}
