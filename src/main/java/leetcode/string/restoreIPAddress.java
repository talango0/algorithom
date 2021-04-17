package leetcode.string;

import java.util.ArrayList;
import java.util.List;

/**
 *
 Given a string containing only digits, restore it by returning all possible valid IP address combinations.

 Example:
 "0000" => ["0.0.0.0"]
 "010010" =>["0.10.0.10", "0.100.1.0"]
 Input: "25525511135"
 Output: ["255.255.11.135", "255.255.111.35"]
 */

public class restoreIPAddress {
    public static void main(String[] args) {
        String s ="010010";
        Solution solution = new Solution();
        List<String> ipList = solution.restoreIpAddresses(s);
        for (String ip:ipList) {
            System.out.println(ip);
        }
    }

    static class Solution {
        public List<String> restoreIpAddresses(String s) {

            List<String> ipList = new ArrayList<String>();
            int n = s.length();
            StringBuffer ip = new StringBuffer();
            for(int i=0; i<3; i++){
                for(int j=i+1;j<i+4 ;j++){
                    for(int k = j+1; k<j+4&&(k + 1)<n;k++){
                        String s1 = s.substring(0, i + 1);
                        String s2 = s.substring(i + 1, j + 1);
                        String s3 = s.substring(j + 1, k + 1);
                        String s4 = s.substring(k + 1, n);
                        if(s1.length()>3 || s1.length()>1&&s1.startsWith("0")||s1=="" || Integer.valueOf(s1)>255){
                            continue;
                        }else if(s2.length()>3 || s2.length()>1&&s2.startsWith("0") ||s2=="" || Integer.valueOf(s2)>255){
                            continue;
                        }else if(s3.length()>3 || s3.length()>1&&s3.startsWith("0") ||s3=="" || Integer.valueOf(s3)>255){
                            continue;
                        }else if (s4.length()>3 || s4.length()>1&&s4.startsWith("0") ||s4=="" || Integer.valueOf(s4)>255){
                            continue;
                        }else{
                            ip.delete(0,ip.length());
                            ip.append(s1);
                            ip.append(".");
                            ip.append(s2);
                            ip.append(".");
                            ip.append(s3);
                            ip.append(".");
                            ip.append(s4);
                            ipList.add(ip.toString());
                        }
                    }
                }
            }

            return ipList;
        }
    }

}
