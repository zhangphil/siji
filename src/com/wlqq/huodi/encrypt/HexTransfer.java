package com.wlqq.huodi.encrypt;

/**
 * author: fangzw
 * date: 13-5-8
 * time: 上午8:59
 */
public class HexTransfer {

    private static final int[] TransferTable = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            2, 3, 4, 5, 6, 7, 8, 9, 0, 0,
            0, 0, 0, 0, 0, 10, 11, 12, 13, 14,
            15, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 10, 11, 12,
            13, 14, 15
    };

    private static final String[] HexTransferTable = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "10", "11", "12", "13",
            "14", "15", "16", "17", "18", "19", "1a", "1b", "1c", "1d",
            "1e", "1f", "20", "21", "22", "23", "24", "25", "26", "27",
            "28", "29", "2a", "2b", "2c", "2d", "2e", "2f", "30", "31",
            "32", "33", "34", "35", "36", "37", "38", "39", "3a", "3b",
            "3c", "3d", "3e", "3f", "40", "41", "42", "43", "44", "45",
            "46", "47", "48", "49", "4a", "4b", "4c", "4d", "4e", "4f",
            "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
            "5a", "5b", "5c", "5d", "5e", "5f", "60", "61", "62", "63",
            "64", "65", "66", "67", "68", "69", "6a", "6b", "6c", "6d",
            "6e", "6f", "70", "71", "72", "73", "74", "75", "76", "77",
            "78", "79", "7a", "7b", "7c", "7d", "7e", "7f", "80", "81",
            "82", "83", "84", "85", "86", "87", "88", "89", "8a", "8b",
            "8c", "8d", "8e", "8f", "90", "91", "92", "93", "94", "95",
            "96", "97", "98", "99", "9a", "9b", "9c", "9d", "9e", "9f",
            "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9",
            "aa", "ab", "ac", "ad", "ae", "af", "b0", "b1", "b2", "b3",
            "b4", "b5", "b6", "b7", "b8", "b9", "ba", "bb", "bc", "bd",
            "be", "bf", "c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7",
            "c8", "c9", "ca", "cb", "cc", "cd", "ce", "cf", "d0", "d1",
            "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "da", "db",
            "dc", "dd", "de", "df", "e0", "e1", "e2", "e3", "e4", "e5",
            "e6", "e7", "e8", "e9", "ea", "eb", "ec", "ed", "ee", "ef",
            "f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9",
            "fa", "fb", "fc", "fd", "fe", "ff"
    };

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param bytes 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] bytes) throws Exception {

        StringBuilder buffer = new StringBuilder();

        final int len = bytes.length;
        for (int i = 0; i < len; i++) {
            int b = bytes[i] & 0xff;
            if (b <= 0xf) buffer.append("0");

            buffer.append(HexTransferTable[b]);
        }

        return buffer.toString();

    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {

        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {

            int t1 = TransferTable[arrB[i]];
            int t2 = TransferTable[arrB[i + 1]];

            arrOut[i / 2] = (byte) (t1 * 16 + t2);

        }

        return arrOut;

    }

}
