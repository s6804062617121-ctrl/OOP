/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;

/**
 *
 * @author kitti
 */
import java.util.ArrayDeque;
import java.util.Scanner;

public class lab706 {
    static int M, N;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static int[][] bfs(int[][] g, int sr, int sc) {
        int[][] d = new int[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                d[i][j] = -1;
            }
        }
        ArrayDeque<int[]> q = new ArrayDeque<>();
        d[sr][sc] = 1;
        q.add(new int[]{sr, sc});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int k = 0; k < 4; k++) {
                int nr = cur[0] + dr[k];
                int nc = cur[1] + dc[k];
                if (nr >= 0 && nr < M && nc >= 0 && nc < N
                        && g[nr][nc] == 1 && d[nr][nc] == -1) {
                    d[nr][nc] = d[cur[0]][cur[1]] + 1;
                    q.add(new int[]{nr, nc});
                }
            }
        }
        return d;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        M = in.nextInt();
        N = in.nextInt();
        int rs = in.nextInt() - 1;
        int cs = in.nextInt() - 1;
        int re = in.nextInt() - 1;
        int ce = in.nextInt() - 1;

        int[][] g = new int[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                g[i][j] = in.nextInt();
            }
        }

        int[][] ds = bfs(g, rs, cs);
        int[][] de = bfs(g, re, ce);

        int count = 0;
        int best = Integer.MAX_VALUE;

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (g[i][j] != 0) {
                    continue;
                }
                boolean ok = false;
                for (int a = 0; a < 4; a++) {
                    int ar = i + dr[a];
                    int ac = j + dc[a];
                    if (ar < 0 || ar >= M || ac < 0 || ac >= N || ds[ar][ac] == -1) {
                        continue;
                    }
                    for (int b = 0; b < 4; b++) {
                        if (a == b) {
                            continue;
                        }
                        int br = i + dr[b];
                        int bc = j + dc[b];
                        if (br < 0 || br >= M || bc < 0 || bc >= N || de[br][bc] == -1) {
                            continue;
                        }
                        ok = true;
                        int len = ds[ar][ac] + 1 + de[br][bc];
                        if (len < best) {
                            best = len;
                        }
                    }
                }
                if (ok) {
                    count++;
                }
            }
        }

        System.out.println(count);
        System.out.println(best == Integer.MAX_VALUE ? -1 : best);
    }
}