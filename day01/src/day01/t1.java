package day01;

class Thread1 extends Thread{
	
	String msg;
	boolean flag=true;
	
	public Thread1(String msg) {
		this.msg=msg;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		int i=0;
	while(flag) {
			System.out.println(msg+" "+i++);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public void setFlag(boolean flag) {
		this.flag=flag;
	}
}

class Thread2 implements Runnable{
		
	String msg;
	boolean flag=true;
	
	public Thread2(String msg) {
		this.msg=msg;
	}
	

	@Override//implements Runnable�ؼ� override�ؾ��ϴ� �޼ҵ�
	public void run() {
		// TODO Auto-generated method stub
		
		int i=0;
	while(flag) {
			System.out.println(msg+" "+i++);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	public void setFlag(boolean flag) {
		this.flag=flag;
	}
		
	}


public class t1 {
	public static void main(String[] args) {
		
		Thread1 t1= new Thread1("t1");
		
		//Runnable�� ������ ���� Thread�� ������ ��ü�� �ִ´�. new Thread �ȿ� Runnable���� ��ӹ��� �ָ� �־� �پ��ϰ� ���� ����
		//�� �پ缺�� �����ϴ� ���
		Thread2 r= new Thread2("t2");
		Thread t2=new Thread(r);
		
		//Thread�� ���ÿ� ����� �̷��� Thread�� �����Ű�� ���ÿ� main���� �۾��� �� ���� �ִ�.
		t1.start();
		t2.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t1.setFlag(false);
		r.setFlag(false);
		
		
	}

}
