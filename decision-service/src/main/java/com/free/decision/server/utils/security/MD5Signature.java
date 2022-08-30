package com.free.decision.server.utils.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;


public class MD5Signature {
    /**
     * 生成签名
     * 
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public static String sign(String content, String key) throws Exception {
        return sign(content, key, "utf-8");
    }

    public static String sign(String content, String key, String charset) throws Exception {

        String tosign = (content == null ? "" : content) + key;

        try {
            return DigestUtils.md5Hex(getContentBytes(tosign, charset));
        } catch (UnsupportedEncodingException e) {
            throw new SignatureException(" MD5 Exception [content = " + content
                                         + "; charset = utf-8" + "]Exception!", e);
        }
    }
    public static String signForWeixin(String content, String key, String charset) throws Exception {

        String tosign = (content == null ? "" : content) + "&key="+key;

        try {
            return DigestUtils.md5Hex(getContentBytes(tosign, charset));
        } catch (UnsupportedEncodingException e) {
            throw new SignatureException(" MD5 Exception [content = " + content
                                         + "; charset = utf-8" + "]Exception!", e);
        }
    }
    /**
     * 取正常md5(32位)的前30位
     * @param content
     * @param key
     * @return
     * @throws Exception 
     */
    public static String signFor19bit(String content, String key) throws Exception {
        String signString = sign(content, key);
        signString = signString.substring(0, 19);
        return signString;

    }

    public static String md5Direct(String content) throws Exception {
        try {
            return DigestUtils.md5Hex(getContentBytes(content, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new SignatureException(" MD5 Exception [content = " + content
                                         + "; charset = utf-8" + "]Exception!", e);
        }
    }

    /**
     * 验证签名
     * 
     * @param content
     * @param sign
     * @param key
     * @return
     * @throws Exception
     */
    public static boolean verify(String content, String sign, String key) throws Exception {
        return verify(content, sign, key, "utf-8");
    }

    /**
     * 对30位的md5值进行验签
     * @param content
     * @param sign
     * @param key
     * @return
     * @throws Exception
     */
    public static boolean verifyFor19Bit(String content, String sign, String key) throws Exception {
        String tosign = (content == null ? "" : content) + key;
        try {
            String mySign = DigestUtils.md5Hex(getContentBytes(tosign, "utf-8"));
            mySign = mySign.substring(0, 19);
            return StringUtils.equals(mySign, sign) ? true : false;
        } catch (UnsupportedEncodingException e) {
            throw new SignatureException("MD5Exception[content = " + content + "; charset ="
                                         + "utf-8" + "; signature = " + sign + "]Exception!", e);
        }
    }

    public static boolean verify(String content, String sign, String key, String charset)
                                                                                         throws Exception {
        String tosign = (content == null ? "" : content) + key;
        try {
            String mySign = DigestUtils.md5Hex(getContentBytes(tosign, charset));
            
            return StringUtils.equals(mySign, sign) ? true : false;
        } catch (UnsupportedEncodingException e) {
            throw new SignatureException("MD5Exception[content = " + content + "; charset ="
                                         + charset + "; signature = " + sign + "]Exception!", e);
        }
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    protected static byte[] getContentBytes(String content, String charset)
                                                                           throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(charset)) {
            return content.getBytes();
        }
        return content.getBytes(charset);
    }

    public static int avg(int d, long m) {
        return d / (int) m;
    }
}
