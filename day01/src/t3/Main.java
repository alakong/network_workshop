package t3;

import java.util.Scanner;

class Receiver implements Runnable{
	
	String cmd;
	
	public void setCmd(String cmd) {
		this.cmd=cmd;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true) {
			try {
				Thread.sleep(1);//�̰� ������ cmd���� �ٲ� �̸� ������ ���� ����
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if(cmd!=null && cmd.equals("s")) {
				//Send Message
				for(int i=0;i<=10;i++) {
					System.out.println(i);
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
				}
				//Send Message End...
			
			}
			
			//�տ� for���� ������ �Ŀ� while���� ����
			if(cmd!=null &&cmd.equals("e"))
				break;	

		}
	}	
}

public class Main {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Receiver r = new Receiver();
		Thread t= new Thread(r);
		t.start();
		
		Scanner sc= new Scanner(System.in);
		System.out.println("Input Cmd s");
		String cmd=sc.nextLine();
		r.setCmd(cmd);
		
		System.out.println("Input Cmd e");
		String cmd2=sc.nextLine();
		r.setCmd(cmd2);
		
		sc.close();
		

	}

}
