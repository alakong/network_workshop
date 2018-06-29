package tcp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	String ip;
	int port;

	Socket socket;
	InputStream in;
	InputStreamReader isr;
	BufferedReader br;

	OutputStream out;
	OutputStreamWriter outw;

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
				socket = new Socket(ip, port); // C와 다르게 소켓 생성시 예외발생하지 않으면
				// connect까지 모두 완료된다.
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

	public void startClient() throws Exception {

		Scanner sc = new Scanner(System.in);

		try {
			
			System.out.println("connected");

			String iSaid;
			System.out.println("");
			
			
			in = socket.getInputStream();
			isr = new InputStreamReader(in);
			br = new BufferedReader(isr);

			out = socket.getOutputStream();
			outw = new OutputStreamWriter(out);
			
			while(true) {

				System.out.println("here");
			
				iSaid = sc.nextLine();
				
				outw.write(iSaid+"\n");
				outw.flush();
				
				String str;
				str=br.readLine();
				if(str==null) break;
				System.out.println(str);
				
			}
			
		} catch (UnknownHostException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			sc.close();
			outw.close();
			br.close();
			socket.close();
		}
	}

	public static void main(String args[]) {
		Client client = null;
		try {
			client = new Client("70.12.114.142", 7777);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}