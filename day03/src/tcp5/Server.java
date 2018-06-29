package tcp5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class Server {

	ServerSocket serverSocket;
	boolean flag = true;

	// 클라이언트 소켓의 DataoutputStream 넣어줄거당
	ArrayList<DataOutputStream> list = new ArrayList<>();

	public Server() throws IOException {

		serverSocket = new ServerSocket(9999);
		System.out.println("Server Ready");

	}

	// Client Accept
	// while문 돌면서 클라이언트 들어올 때마다 소켓 생성하도록
	// socket마다 Receiver 쓰레드 돌게 해줌
	public void start() throws IOException {

		while (flag) {
			Socket socket = serverSocket.accept();
			System.out.println(socket.getInetAddress() + " Connected");
			
			Receiver r = new Receiver(socket);
			r.start();
			

		}
	}

	// 각각의 소켓마다 인풋스트림 생성하고 데이터 받도록 / 각각의 소켓마다 아웃풋스트림도 여기서 받아서 arraylist에 넣어주기만 한다
	class Receiver extends Thread {

		InputStream in;
		DataInputStream dis;
		OutputStream out;
		DataOutputStream dos;

		public Receiver(Socket socket) throws IOException {

			in = socket.getInputStream();
			dis = new DataInputStream(in);

			out = socket.getOutputStream();
			dos = new DataOutputStream(out);

			list.add(dos);
			System.out.println();

		}

		@Override
		public void run() {

			while (dis != null) {

				String str="";
				
				try {
					str = dis.readUTF();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (str.equals("q")&&str!=null) {

					break;
				}

				System.out.println(str);
			
				//send Spring Server
				SendHttp http = new SendHttp(str);
				http.start();
				

			} //while
			
			//클라이언트가 종료하겠다고하면 receiver에서 종료메세지 받고 리스트에 저장되어있는 dataoutputstream 삭제
		    list.remove(dos);
		    if(dos!=null) {
		    	try {
					dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    
		    
		    if(dis!=null) {
		    	try {
					dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }

		}//run

	}

	

	//클라이언트가 입력한 문자열 ,로 split해서 speed와 temp라는 이름으로 webServer로 넘겨줌
	class SendHttp extends Thread{
		String speed;
		String temp;
		String msg[];
		String urls="http://127.0.0.1/ws/main.do";
		
		public SendHttp(String msg) {
			this.msg = msg.split(",");
			speed = this.msg[0].trim();
			temp = this.msg[1].trim();
			
		}
		
		@Override
		public void run() {
		
			
			urls += "?speed="+speed+"&temp="+temp;
			
			try {
				URL url = new URL(urls);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setConnectTimeout(5000);
				con.getInputStream();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = null;

		try {
			server = new Server();
			server.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
