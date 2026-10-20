package com.signalsimulator.algorithms;

public class PalindromeFinder {
    // Manacher's algorithm: O(n) time and O(n) extra space.
    public String longestPalindrome(String s) {
        if (s == null || s.isEmpty()) return "";
        StringBuilder t = new StringBuilder("^");
        for (int i=0;i<s.length();i++) { t.append('#').append(s.charAt(i)); }
        t.append("#$");
        char[] a = t.toString().toCharArray();
        int[] p = new int[a.length];
        int center=0, right=0, bestCenter=0, bestLen=0;
        for (int i=1;i<a.length-1;i++) {
            int mirror = 2*center-i;
            if (i < right) p[i] = Math.min(right-i, p[mirror]);
            while (a[i+1+p[i]] == a[i-1-p[i]]) p[i]++;
            if (i+p[i] > right) { center=i; right=i+p[i]; }
            if (p[i] > bestLen) { bestLen=p[i]; bestCenter=i; }
        }
        int start = (bestCenter-bestLen)/2;
        return s.substring(start, start+bestLen);
    }
}
