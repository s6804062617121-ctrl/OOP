/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab10;

/**
 *
 * @author kitti
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

class ZoneClock extends JPanel {
    private TimeZone zone;
    private int hour;
    private int minute;
    private int second;

    public ZoneClock(String zoneId) {
        zone = TimeZone.getTimeZone(zoneId);
        setCurrentTime();
    }

    public void setCurrentTime() {
        Calendar c = new GregorianCalendar(zone);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        second = c.get(Calendar.SECOND);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int r = (int) (Math.min(getWidth(), getHeight()) * 0.8 * 0.5);
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        g.setColor(Color.black);
        g.drawOval(cx - r, cy - r, 2 * r, 2 * r);
        g.drawString("12", cx - 5, cy - r + 12);
        g.drawString("9", cx - r + 3, cy + 5);
        g.drawString("3", cx + r - 10, cy + 3);
        g.drawString("6", cx - 3, cy + r - 3);

        int sl = (int) (r * 0.8);
        g.setColor(Color.red);
        g.drawLine(cx, cy, (int) (cx + sl * Math.sin(second * (2 * Math.PI / 60))),
                (int) (cy - sl * Math.cos(second * (2 * Math.PI / 60))));

        int ml = (int) (r * 0.65);
        g.setColor(Color.blue);
        g.drawLine(cx, cy, (int) (cx + ml * Math.sin(minute * (2 * Math.PI / 60))),
                (int) (cy - ml * Math.cos(minute * (2 * Math.PI / 60))));

        int hl = (int) (r * 0.5);
        double ha = (hour % 12 + minute / 60.0) * (2 * Math.PI / 12);
        g.setColor(Color.green);
        g.drawLine(cx, cy, (int) (cx + hl * Math.sin(ha)), (int) (cy - hl * Math.cos(ha)));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }
}

public class lab1002v2 extends JFrame {
    private ZoneClock thai = new ZoneClock("Asia/Bangkok");
    private ZoneClock japan = new ZoneClock("Asia/Tokyo");

    public lab1002v2() {
        setLayout(new GridLayout(1, 2));
        add(thai);
        add(japan);
        Timer timer = new Timer(1000, e -> {
            thai.setCurrentTime();
            japan.setCurrentTime();
            thai.repaint();
            japan.repaint();
        });
        timer.start();
    }

    public static void main(String[] args) {
        lab1002v2 frame = new lab1002v2();
        frame.setTitle("Clock Around The World");
        frame.setSize(420, 260);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


//3.
//import: นำเข้าคลาสสำหรับวาดภาพ (java.awt), จัดการเหตุการณ์ (java.awt.event) และ Swing
//Exercise/Main: เป็น JFrame ที่สร้าง RaceCar มาใส่ด้วย add(...) ส่วน main ตั้งชื่อ ขนาด 200×100 จัดกึ่งกลางจอ ให้ปิดโปรแกรมเมื่อกดกากบาท และแสดงเฟรม
//RaceCar extends JPanel: พื้นที่วาดรถ มี xBase เก็บตำแหน่งรถ และ Timer ทำงานทุก 10 ms
//constructor: เริ่ม timer, ทำให้ panel รับคีย์ได้ด้วย setFocusable(true) และลงทะเบียน KeyAdapter แบบ anonymous class
//keyPressed: Ctrl และ + (keyCode 61) ลด delay ทำให้รถเร็วขึ้น ส่วน Ctrl และ - (keyCode 45) เพิ่ม delay ทำให้รถช้าลง (ในเอกสารโจทย์เขียนกลับกัน)
//Listener.actionPerformed: ทุกครั้งที่ timer ครบเวลาจะเรียก repaint()
//paintComponent: super.paintComponent(g) ล้างภาพเก่า ถ้ารถพ้นขอบขวาให้กลับไป -20 ไม่เช่นนั้นเพิ่ม xBase 1 วาดล้อดำ 2 ล้อ ตัวรถสีเขียว 50×10 และหลังคา Polygon สีแดง