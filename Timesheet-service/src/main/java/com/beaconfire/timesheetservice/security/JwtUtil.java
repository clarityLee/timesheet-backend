package com.beaconfire.timesheetservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.bson.types.ObjectId;

import javax.servlet.http.HttpServletRequest;

public class JwtUtil {

    public static String getSubject(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey){
        String token = CookieUtil.getValue(httpServletRequest, jwtTokenCookieName);
        if(token == null) return null;
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
    }

//    public static String getClaim(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey) {
//        String token = CookieUtil.getValue(httpServletRequest, jwtTokenCookieName);
//        Claims claims = Jwts.parser()
//                .setSigningKey(signingKey)
//                .parseClaimsJws(token)
//                .getBody();
//        String id = (String) claims.get("id");
//        return id;
//    }
}
