package main.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * #InetAddress
 * IP 주소를 다루기 위한 클래스
 * <p>
 * #API
 * getAddress() => byte[] :: ip => byte
 * getAllbyName(String host) => static InetAddress[] :: domain => ips
 * getByAddress(byte[] addr) => static InetAddress :: byte[] => ip
 * getByName(String host) => static InetAddress :: domain => ip
 * getCanonicalHostname => String :: full domain name
 * getHostAddress() => String :: hostIP
 * getHostName() => String :: host 이름
 * getLocalHost() => static InetAddress :: 지역호스트의 ip
 * isMulticatAddress() => boolean :: ip주소가 멀티캐스트인지
 * isLoopbackAddress() => boolean :: ip주소가 loopback 주소 ( 127.0.0.1 ) 인지 알려준다. ( 호스트자신을 가지키는주소 )
 */

public class Doc_InetAddress {

    public static void main(String[] args) {

        InetAddress ip = null;
        InetAddress[] ipArr = null;

        try {
            ip = InetAddress.getByName("www.naver.com");
            System.out.println("getHostName() : " + ip.getHostName());
            System.out.println("getHostAddress() : " + ip.getHostAddress());
            System.out.println("toString() : " + ip.toString());

            byte[] ipAddr = ip.getAddress();
            System.out.println("getHostAddress() : " + Arrays.toString(ipAddr));

            String result = "";
            for (int i = 0; i < ipAddr.length; i++) {
                result += (ipAddr[i] < 0) ? ipAddr[i] + 256 : ipAddr[i];
                result += ".";
            } // for

            System.out.println("getAddress()+256 : " + result);
            System.out.println();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } // try - catch

        try {
            ip = InetAddress.getLocalHost();
            System.out.println("getHostName() : " + ip.getHostName());
            System.out.println("getHostAddress() : " + ip.getHostAddress());
            System.out.println("toString() : " + ip.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } // try - catch

        try {
            ipArr = InetAddress.getAllByName("www.naver.com");

            System.out.println();
            for (int i = 0; i < ipArr.length; i++) {
                System.out.println("ipArr[" + i + "] : " + ipArr[i]);
            } // for
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } // try - catch

        /*
         *
         * 결과
         *
         * getHostName() : www.naver.com
         * getHostAddress() : 202.131.30.12
         * toString() : www.naver.com/202.131.30.12
         * getHostAddress() : [-54, -125, 30, 12]
         * getAddress()+256 : 202.131.30.12.
         *
         * getHostName() : Gz
         * getHostAddress() : 192.168.0.37
         * toString() : Gz/192.168.0.37

         * ipArr[0] : www.naver.com/202.131.30.12
         * ipArr[1] : www.naver.com/220.95.233.172
         *
         */

    } // main
} // Doc_InetAddress
