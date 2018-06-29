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
	

	@Override//implements Runnable해서 override해야하는 메소드
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
		
		//Runnable를 생성할 때는 Thread로 생성한 객체에 넣는다. new Thread 안에 Runnable에사 상속받은 애를 넣어 다양하게 동작 가능
		//더 다양성을 보장하는 방식
		Thread2 r= new Thread2("t2");
		Thread t2=new Thread(r);
		
		//Thread는 동시에 진행됨 이렇게 Thread를 실행시키고 동시에 main에서 작업을 할 수도 있다.
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
