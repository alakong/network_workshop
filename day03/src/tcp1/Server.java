package tcp1;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
   int port;
   ServerSocket serversocket;
   Socket socket;
   OutputStream out;
   OutputStreamWriter outw;
   boolean flag = true;
   
   public Server() throws IOException {
      port=7777;
      serversocket =new ServerSocket(port); //특정포트를 이용하여 서버역활을 할것이다. 다른사람이 서버를사용하고있을때 사용불가
   }
   public void startServer() throws IOException{
      System.out.println("start server");
      System.out.println("Ready server");
      while(flag) {
         
      try {
      socket = serversocket.accept(); //클라이언트 접속 요청들어오면 새로운 소켓으로 반환->클라이언트가 들어오길 기다리는 상태
      System.out.println(socket.getInetAddress());
      out = socket.getOutputStream();
      outw = new OutputStreamWriter(out);
      outw.write("hi cilent, it's server!");   
      } catch (IOException e) {
         throw e;
      }
      finally {
         if(outw != null)
            outw.close();
         if(socket != null)
            socket.close();
      }
      }
      //System.out.println("end server");
   }
   
   public static void main(String[] args) {
      Server server=null;
      try {
         server = new Server();
         server.startServer();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}