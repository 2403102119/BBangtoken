package bbangtoken.tckkj.com.bbangtoken.Util;

import android.util.Log;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

public class MD5Utils {

    public static String md5Password(String password){
        StringBuffer sb = new StringBuffer(); // 得到一个信息摘要器
        try { MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes()); // 把每一个byte做一个与运算 0xff
            for (byte b : result)
            {// 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) { sb.append("0"); }
                sb.append(str); } }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); }
        return sb.toString();
    }

    /**
     * 获取ip地址
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }

}
