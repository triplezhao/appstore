package com.potato.library.util.encrypt;


import android.text.TextUtils;
public class PrivateUtil {

    public static final String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJz/J1OgkpF3ONcB7kHcWoJzqVMNs/cc" + "\r"
            + "aao79tPodAGXlN/K2jub2rliZnjIKxSIuAcM4PK4HpznXRPpNGjHplECAwEAAQ==" + "\r";

    public static final String privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAnP8nU6CSkXc41wHu" + "\r"
            + "QdxagnOpUw2z9xxpqjv20+h0AZeU38raO5vauWJmeMgrFIi4Bwzg8rgenOddE+k0" + "\r"
            + "aMemUQIDAQABAkAYqEZP7xc1+4iU1DvMYkRg2rOMVMmgFPlPgE166LZXjW0c3Fbg" + "\r"
            + "JUD1MtNaZpy2WKLwRDiPtzAZLCy2RmNtTi2xAiEAywvgExwpoGEzidgsYnyRMPew" + "\r"
            + "MRDJSZPEdjPGIKQMVy0CIQDF8NTF93mP9wqPmG0/IZIriUCP2ZUZ646/HrxKqsBC" + "\r"
            + "NQIgc99bCIzR1Iyj9M5AxhOAaAlxqw6BUFPbkfkJ4Ca+RCECIQCHbgemi3Q49CXd" + "\r"
            + "qcTVdPq1nur1gUFqwqigSz85NyrkIQIgLeUNfPLdNDuIWJi7IpPFII1HN+Y5DSPC" + "\r" + "Do+1Oth8Pyk=" + "\r";

    public static String decryptContent(String contentStr, String keyStr) {

        if (TextUtils.isEmpty(contentStr)) {
            return null;
        }
        if (TextUtils.isEmpty(keyStr)) {
            return null;
        }
        try {
            String keyAndMd5 = SecurityUtil.rsaDecode(keyStr, privateKey);
            // 截取自定义数据
            String key = keyAndMd5.substring(0, keyAndMd5.length() - 16);
            String content = "";
            content = SecurityUtil.desDecode(contentStr, key);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
