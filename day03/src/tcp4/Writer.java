package tcp4;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Writer implements Runnable {

	Socket socket;
	OutputStream out;
	DataOutputStream dos;
	boolean flag;
	Scanner sc;
	String name;
	
	public Writer() {
		
	}
	
	public Writer(Socket socket, String name) {
		this.socket = socket;
		sc = new Scanner(System.in);
		flag = true;
		this.name = name;
		
		try {
			out = this.socket.getOutputStream();
			dos = new DataOutputStream(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void closeSocket(){
		if(socket!=null) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void writeMsg(String msg){
		try {

			dos.writeUTF(msg);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			flag = false;
			System.out.println("채팅종료");
		}
	
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("메세지를 입력하세요");
		
		while(flag){
			

			String s = sc.nextLine();
			
			if(s.equals("exit")) {
				sc.close();
				flag = false;
				closeSocket();
			}
			
			writeMsg(s);
		  
		  
		}
		
	}
}
