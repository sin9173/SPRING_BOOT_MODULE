package com.util.modules.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class RequestUtil {

    //현재 요청에 대한 Request 가져오기
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    //요청한 클라이언트의 아이피 가져오기
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARD_For");
        if(ip == null) ip = request.getHeader("Proxy-Client-IP");
        if(ip == null) ip = request.getHeader("WL-Proxy-Client-IP");
        if(ip == null) ip = request.getHeader("HTTP_Client_IP");
        if(ip == null) ip = request.getHeader("HTTP_X_FORWARD_FOR");
        if(ip == null) ip = request.getRemoteAddr();
        return ip;
    }

    public static List<String> getLocalIpList() {
        List<String> inets = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while(nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    inets.add(address.getHostAddress());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inets;
    }

    //RequestBody 로 부터 파라미터로그를 추출
    public static String getRequestBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = null;
        String line = "";
        try {
            InputStream inputStream = request.getInputStream();
            if(inputStream!=null) {
                br = new BufferedReader(new InputStreamReader(inputStream));
                while((line=br.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } else {
                stringBuilder.append("파라미터 없음");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    //Args 로 부터 파라미터를 로그를 추출
    public static String getRequestArgs(Object[] args) {
        List<String> collect = Arrays.stream(args)
                .map(o -> o.toString())
                .collect(Collectors.toList());
        return collect.size()==0?"":collect.get(0);
    }


    //Object의 파라미터 중 null인 파라미터가 있을 경우 true
    public static boolean isParamNull(Object vo) throws Exception {
        Field[] fields = vo.getClass().getDeclaredFields();
        Method method = null;
        for(Field f : fields) {
            if(String.valueOf(f.getType()).equals("boolean")) {
                method = vo.getClass().getMethod(f.getName());
            } else {
                method = vo.getClass().getMethod("get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1));
            }
            String value = String.valueOf(method.invoke(vo));
            if(value==null || value.equals("null")) return true;
        }
        return false;
    }
}
