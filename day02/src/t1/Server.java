package t1;

import java.util.Scanner;

public class Server {
   
   boolean flag = true;
   
   public void startServer() {
      Scanner client = new Scanner(System.in);
      String msg;
      while(flag) {
         System.out.println("Server Ready");
         msg = client.nextLine();
         //Receiver Thread
         Receiver receiver=new Receiver(msg);
         receiver.start();
         //Thread�� ������ ��Ű�Ƿ� �޼����� ������ ���ÿ� ���������� ó����. ������� ó������ ����
      }
   }
   
   class Receiver extends Thread{
	   
	   String msg;
	   public Receiver() { 
	   }
	   public Receiver(String msg) {
		   this.msg=msg;
	   }
	   

      @Override
      public void run() {
         for(int i=0;i<10;i++) {
        	 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	 System.out.println(i);;
         }
         System.out.println(msg+"Completed");
         
         //Sender thread�� ���� 
         //Client���� ����
         Sender sender=new Sender(msg);
         sender.start();
      }
      
   }//end Receiver
   
   class Sender extends Thread{
	   
	   String msg;
	   
	   public Sender() {
	   }
	   public Sender(String msg) {
		   this.msg=msg;
	   }
	   
	   @Override
	      public void run() {
	         for(int i=0;i<10;i++) {
	        	 try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	 System.out.println(i);;
	         }
	         System.out.println(msg+" : Send Completed");
	      }
	   
	   
	   
   }//Sender class

   public static void main(String[] args) {
      System.out.println("Server Start");
      new Server().startServer();
      System.out.println("Server Stop");
   }

}