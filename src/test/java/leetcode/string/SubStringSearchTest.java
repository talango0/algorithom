package leetcode.string;

import edu.princeton.cs.algs4.StdOut;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SubStringSearchTest {

    @Test
    void bruteForceSearch() {
        Assert.assertEquals(0,SubStringSearch.bruteForceSearch("abc", "abcd"));
    }

    static class KMPTest {

        @Test
        void search() {

    //        String pat = "AACAA";
            String pat = "ABABAC";
            String txt = "AABRAACADABRAACAADABRA";
            SubStringSearch.KMP kmp = new SubStringSearch.KMP(pat);


            StdOut.println("text:    "+txt);
            int offset = kmp.search(txt);
            StdOut.print("pattern: ");
            for(int i = 0; i < offset; i++){
                StdOut.print(" ");
            }
            StdOut.print(pat);

        }
    }


    @Test
    public void testBoyerMoore(){
        String pat = "ABABAC";
        String txt = "AABRAACADABRAACAADABRA";
        SubStringSearch.BoyerMoore boyerMoore = new SubStringSearch.BoyerMoore(pat);
        int i = boyerMoore.search(txt);
        Assert.assertEquals(txt.length(), i);

        boyerMoore = new SubStringSearch.BoyerMoore("cde");
        Assert.assertEquals(2,boyerMoore.search("abcde"));
    }

    @Test
    public void testRabinKarp(){
        String pat = "ABABAC";
        String txt = "AABRAACADABRAACAADABRA";
        SubStringSearch.RabinKarp rabinKarp = new SubStringSearch.RabinKarp(pat);
        Assert.assertEquals(txt.length(), rabinKarp.search(txt));

        rabinKarp = new SubStringSearch.RabinKarp("cde");
        Assert.assertEquals(2, rabinKarp.search("abcde"));

    }

}