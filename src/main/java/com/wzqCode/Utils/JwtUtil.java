package com.wzqCode.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

public class JwtUtil {
    //秘钥
    private static final String JWT_SECRET = "vcf&*sgh&ff";

    //根据用户id和账号名称生成token
    public static String createToken(int id, String account){
        //签发时间
        Calendar ins = Calendar.getInstance();
        //有效时长
        ins.add(Calendar.SECOND, 60*60*240);
        //秘钥加密方式
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);

        //token生成
        String token = JWT.create()
                .withClaim("id", id)
                .withClaim("account", account)
                .withExpiresAt(ins.getTime())   //token的失效时长
                .sign(algorithm);
        return token;
    }

    //获取token信息
    public static DecodedJWT verify(String token){
        //解密token
        return JWT.require(Algorithm.HMAC256(JWT_SECRET)).build().verify(token);
    }
}
