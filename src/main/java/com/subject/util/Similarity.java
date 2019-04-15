package com.subject.util;

/*
 LCS(Longest Common Subsequence)算法实现的文本相似度分析：
算法原理：

(1) 将两个字符串分别以行和列组成矩阵。
(2) 计算每个节点行列字符是否相同，如相同则为 1。
(3) 通过找出值为 1 的最长对角线即可得到最长公共子串。

   人 民  共 和 时 代
中 0, 0, 0, 0, 0, 0
华 0, 0, 0, 0, 0, 0
人 1, 0, 0, 0, 0, 0
民 0, 1, 0, 0, 0, 0
共 0, 0, 1, 0, 0, 0
和 0, 0, 0, 1, 0, 0
国 0, 0, 0, 0, 0, 0

为进一步提升该算法，我们可以将字符相同节点(1)的值加上左上角(d[i-1, j-1])的值，
这样即可获得最大公用子串的长度。如此一来只需以行号和最大值为条件即可截取最大子串。

   人 民  共 和 时 代
中 0, 0, 0, 0, 0, 0
华 0, 0, 0, 0, 0, 0
人 1, 0, 0, 0, 0, 0
民 0, 2, 0, 0, 0, 0
共 0, 0, 3, 0, 0, 0
和 0, 0, 0, 4, 0, 0
国 0, 0, 0, 0, 0, 0
 * */
public class Similarity {

    private final String content_regex="(?i)[^a-zA-Z0-9\u4E00-\u9FA5]";
    /**
     * 判断两段正文相似度
     * @return
     */
    public static Float calculatesimilar(String str1,String str2) {
        //计算两个字符串的长度。
        int len1 = str1.length();
        int len2 = str2.length();
        //建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }
        /*输出dif二维数组*/
        String teststr="";
        for(int i=0;i<=len1;i++){
            teststr="";
            for(int j=0;j<=len2;j++){
                teststr=teststr+" "+dif[i][j];
            }
            //System.out.println(i+"行："+teststr);
        }

        /**/
        //计算相似度
        float similarity =1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        return Float.valueOf(similarity);
    }

    //得到最小值
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }
}
