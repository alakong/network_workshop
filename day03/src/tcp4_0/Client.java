package tcp4_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	
	//�ȿ��� ���� ȥ�� �Ϥ������ֵ���
	
	boolean flag=true;
	
	String ip;
	int port;
	Socket socket;

	
	
	public Client() {
	}
	
	public Client(String ip, int port) throws Exception {
		this.ip = ip;
		this.port = port;
		connect();
		startClient();
	}
	
	public void connect() {
		int countTry = 0;
		while (countTry++ < 10) {
			try {
				socket = new Socket(ip, port); // C�� �ٸ��� ���� ������ ���ܹ߻����� ������
				// connect���� ��� �Ϸ�ȴ�.
				if (socket != null && socket.isConnected()) {
					break;
				}
			} catch (IOException e) {
				try {
					System.out.println("retry");
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	
	public void startClient() {
		new Receiver(socket).start();
		
		Scanner sc= new Scanner(System.in);
		System.out.print("Input Client...");	
		while(flag) {
			//System.out.print("Input Client...");	
			
			String str= sc.nextLine();
		
			if(str!=null) 
			new Thread(new Sender(socket,str)).start();
			
			if(str.equals("q")) {
				sc.close();
				
				break;
			}
		
		}
		
		
		
		System.out.println("Stop Client...");
	}
	
	
	
	class Receiver extends Thread{
		
		private Socket socket;
		InputStream in;
		InputStreamReader isr;
		BufferedReader br;
		
		public Receiver(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			
			String iSaid="";
			
			try {
				while(socket.isConnected()) {
					
					
				//�ޱ����
				in = socket.getInputStream();
				isr = new InputStreamReader(in);
				br = new BufferedReader(isr);
				//���� �а� ���
				iSaid=br.readLine();
				if(iSaid.equals("q")) {
					System.out.println("Ŭ���̾�Ʈ ���ù� ������ ������~~");
					break;
				}
				
				System.out.println("Server: "+iSaid);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		}
		
	}
	
	class Sender implements Runnable{
		
		private Socket socket;		
		OutputStream out;
		OutputStreamWriter outw;
		PrintWriter print;
		String iSaid;
		
		public Sender(Socket socket,String iSaid) {
			this.socket = socket;
			this.iSaid= iSaid;
		}

		@Override
		public void run() {
		
			//System.out.println("Client ����");	
			try {
				out = socket.getOutputStream();
				outw = new OutputStreamWriter(out);
				print = new PrintWriter(outw);
				print.println(iSaid);
				print.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(outw!=null&&iSaid.equals("q")) {
					System.out.println("outw sender ������!");
					outw.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
			
		}
		
	}
	
	public static void main(String args[]) {
		
		Client client=null;
		try{
			client= new Client("70.12.114.142", 7777);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
