2.
  พิมพ์ A 10บรรทัดและ B 10บรรทัดและลำดับไม่แน่นอนเพราะสองเธรดทำงานพร้อมกันและschedulerเป็นผู้เลือกว่าเธรดไหนจะได้ก่อน
3.
  สร้าง anonymous class ที่สืบทอดจาก Thread และ override run()
	while (true) จะทำงานไม่หยุด
	เพิ่มตำแหน่งลูกบอลตามความเร็ว ballSpeedX กับ ballSpeedY
	ตรวจการชนขอบ ถ้าชนจะกลับทิศความเร็วและปรับตำแหน่งให้อยู่ที่ขอบ
	repaint() ขอวาดใหม่ ซึ่งจะเรียก paintComponent()
	Thread.sleep(1000 / UPDATE_RATE) พักประมาณ 33 ms ได้ราว 30 เฟรมต่อวินาที และเปิดโอกาสให้เธรดอื่นทำงาน
	ดักจับ InterruptedException ที่อาจเกิดขณะ sleep
	gameThread.start() เริ่มเธรดใหม่ แล้ว JVM เรียก run() ให้เอง
	invokeLater สร้าง GUI บน Event Dispatch Thread แยกจากเธรดลูกบอล
