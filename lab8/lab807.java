/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab8;

/**
 *
 * @author kitti
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

public class lab807 {
    static DataInputStream in = new DataInputStream(System.in);
    static byte[] buf = new byte[1 << 16];
    static int len = 0;
    static int ptr = 0;

    static int read() throws IOException {
        if (ptr == len) {
            len = in.read(buf, 0, buf.length);
            ptr = 0;
            if (len <= 0) {
                return -1;
            }
        }
        return buf[ptr++];
    }

    static int nextInt() throws IOException {
        int c = read();
        while (c < '0') {
            c = read();
        }
        int r = 0;
        while (c >= '0') {
            r = r * 10 + (c - '0');
            c = read();
        }
        return r;
    }

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        int m = nextInt();
        int[][] route = new int[m][];
        int[] cnt = new int[n + 1];
        int[] last = new int[n + 1];
        Arrays.fill(last, -1);

        for (int i = 0; i < m; i++) {
            int s = nextInt();
            route[i] = new int[s];
            for (int j = 0; j < s; j++) {
                int st = nextInt();
                route[i][j] = st;
                if (last[st] != i) {
                    last[st] = i;
                    cnt[st]++;
                }
            }
        }

        int[] start = new int[n + 2];
        for (int s = 1; s <= n; s++) {
            start[s + 1] = start[s] + cnt[s];
        }
        int[] ls = new int[start[n + 1]];
        int[] fill = new int[n + 1];
        Arrays.fill(last, -1);
        for (int i = 0; i < m; i++) {
            for (int st : route[i]) {
                if (last[st] != i) {
                    last[st] = i;
                    ls[start[st] + fill[st]++] = i;
                }
            }
        }

        boolean[][] adj = new boolean[m][m];
        for (int s = 1; s <= n; s++) {
            for (int a = start[s]; a < start[s + 1]; a++) {
                for (int b = start[s]; b < start[s + 1]; b++) {
                    adj[ls[a]][ls[b]] = true;
                }
            }
        }

        int[][] d = new int[m][m];
        int[] queue = new int[m];
        for (int i = 0; i < m; i++) {
            Arrays.fill(d[i], -1);
            int head = 0;
            int tail = 0;
            d[i][i] = 0;
            queue[tail++] = i;
            while (head < tail) {
                int u = queue[head++];
                for (int v = 0; v < m; v++) {
                    if (adj[u][v] && d[i][v] == -1) {
                        d[i][v] = d[i][u] + 1;
                        queue[tail++] = v;
                    }
                }
            }
        }

        int q = nextInt();
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < q; t++) {
            int a = nextInt();
            int b = nextInt();
            if (a == b) {
                sb.append("0\n");
                continue;
            }
            int best = Integer.MAX_VALUE;
            for (int x = start[a]; x < start[a + 1]; x++) {
                int[] row = d[ls[x]];
                for (int y = start[b]; y < start[b + 1]; y++) {
                    int dd = row[ls[y]];
                    if (dd >= 0 && dd < best) {
                        best = dd;
                    }
                }
            }
            if (best == Integer.MAX_VALUE) {
                sb.append("impossible\n");
            } else {
                sb.append(best).append('\n');
            }
        }
        System.out.print(sb);
    }
}