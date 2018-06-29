package tcp4_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server {
	int port;
	ServerSocket serversocket;
	Socket socket;
	boolean flag = true;

	public Server() throws IOException {
		port = 7777;
		serversocket = new ServerSocket(port);
	}
	
	public void startServer() throws IOException {

		Scanner sc = new Scanner(System.in);
		socket = serversocket.accept();
		new Receiver(socket).start();
		System.out.print("Server... ");
		while (flag) {
		//System.out.print("Server... ");
			String str = sc.nextLine();
			
			new Thread(new Sender(socket,str)).start();
			if (str.equals("q")) {
				sc.close();
				break;
			}

		}

		System.out.println("Stop Server...");
	}

	class Receiver extends Thread {

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
				while (socket.isConnected()) {
					
					// 받기 셋팅
					in = socket.getInputStream();
					isr = new InputStreamReader(in);
					br = new BufferedReader(isr);
					// 오면 읽고 출력
	
					iSaid=br.readLine();
					if (iSaid.equals("q")||socket.isClosed()) {
						System.out.println("서버 리시버 스레드 꺼진다~~");
						break;
					}

					System.out.println("Client: "+iSaid);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(br!=null)
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	class Sender implements Runnable {
		private Socket socket;
		OutputStream out;
		OutputStreamWriter outw;
		PrintWriter print;
		String iSaid;

		public Sender(Socket socket, String iSaid) {
			this.socket = socket;
			this.iSaid = iSaid;
		}

		@Override
		public void run() {

			//System.out.println("Server 보냄");
			try {
				out = socket.getOutputStream();
				outw = new OutputStreamWriter(out);
				print = new PrintWriter(outw);
				print.println(iSaid);
				print.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(outw!=null&&iSaid.equals("q")) {
						System.out.println("outw sender 꺼진다!");
					outw.close();}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public static void main(String args[]) {
		
		try {
			Server server = null;
			server = new Server();
			server.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
