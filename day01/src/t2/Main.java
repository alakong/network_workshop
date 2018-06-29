package t2;

class T1 extends Thread{

		int result = 1;
		boolean flag = true;

		public int getResult() {
			return this.result;
		}
		public void setFlag(boolean flag) {
			this.flag = flag;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub

			while (flag) {
				result++;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	};
}

public class Main {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		T1 t1= new T1();
		t1.start();
		int result=0;
		while(result <= 20) {
			result=t1.getResult();//thread의 상태값을 뽑아낼 수 있다.하지만 시간차 오차가 생길 수도있다.
			System.out.println(result);
			if(result==20) {
				t1.setFlag(false);//Thread소멸
				break;
			}
		}
	}



}
