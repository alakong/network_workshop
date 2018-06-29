package tcp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
		while (flag) {
			socket = serversocket.accept();
			new Thread(new SenderThread(socket)).start();
		}
	}

	// 서버 스레드
	class SenderThread implements Runnable {
		private Socket socket;
		OutputStream out;
		OutputStreamWriter outw;
		PrintWriter print;

		InputStream in;
		InputStreamReader isr;
		BufferedReader br;

		public SenderThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {

				System.out.println("connected");
				String content = "";
				in = socket.getInputStream();
				isr = new InputStreamReader(in);
				br = new BufferedReader(isr);

				out = socket.getOutputStream();
				outw = new OutputStreamWriter(out);
				print = new PrintWriter(outw);

				while (true) {
					content = br.readLine();
					
					 if (content.equals("quit")) {
						break;
						
					} else if (content != null) {
						System.out.println("client 의 메시지:" + content);
						print.println(content);
						print.flush();

					}

				}

				System.out.println("end");

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (outw != null)
						outw.close();
					if (socket != null)
						socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public static void main(String[] args) {
		Server server = null;
		try {
			server = new Server();
			server.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
