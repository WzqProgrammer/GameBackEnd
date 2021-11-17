package com.wzqCode.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.wzqCode.exception.gameException.TokenErrorException;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {

    public static Integer getAccountIdByRequest(HttpServletRequest request){
        return getIdByRequest(request, JwtUtil.ACCOUNT_ID_NAME);
    }

    public static Integer getPlayerIdByRequest(HttpServletRequest request){
        return getIdByRequest(request, JwtUtil.PLAYER_ID_NAME);
    }


    private static Integer getIdByRequest(HttpServletRequest request, String idName){
        String token = request.getHeader("token");
        if(token == null)
            throw new TokenErrorException();

        DecodedJWT decodedJWT;
        try{
            decodedJWT = JwtUtil.verify(token);
        }catch (Exception e){
            throw new TokenErrorException();
        }

        Integer id = decodedJWT.getClaim(idName).asInt();
        if (id == null) {
            throw new TokenErrorException();
        }

        return id;
    }
}
