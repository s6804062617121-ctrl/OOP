/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9;

/**
 *
 * @author kitti
 */
import java.util.ArrayList;
import java.util.Scanner;

class ParticleLab {
    private ArrayList<Integer> energy = new ArrayList<>();

    public void add(int x) {
        energy.add(x);
    }

    public long collideAll() {
        long total = 0;
        while (energy.size() >= 2) {
            int bestIdx = 0;
            int bestDiff = -1;
            for (int i = 0; i + 1 < energy.size(); i++) {
                int d = Math.abs(energy.get(i) - energy.get(i + 1));
                if (d > bestDiff) {
                    bestDiff = d;
                    bestIdx = i;
                }
            }
            total += bestDiff;
            energy.remove(bestIdx);
            energy.remove(bestIdx);
        }
        return total;
    }
}

public class lab909 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        ParticleLab lab = new ParticleLab();
        for (int i = 0; i < n; i++) {
            lab.add(in.nextInt());
        }
        System.out.println(lab.collideAll());
    }
}