package tcp2;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
   int port;
   ServerSocket serversocket;
   Socket socket;
   boolean flag = true;
   
   public Server() throws IOException {
      port=7777;
      serversocket =new ServerSocket(port);
   }
   public void startServer() throws IOException{
      while(flag) {
         socket = serversocket.accept();
         new Thread(new SenderThread(socket)).start();         
      }
   }
   
   class SenderThread implements Runnable{
      private Socket socket;
      OutputStream out;
      OutputStreamWriter outw;
      public SenderThread(Socket socket) {
         this.socket = socket;
      }
      @Override
      public void run() {
         try {
         Thread.sleep(5000);
      } catch (InterruptedException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
         try {
            out = socket.getOutputStream();
            outw = new OutputStreamWriter(out);
            outw.write("hello "+socket.getInetAddress());
         } catch (IOException e) {
            e.printStackTrace();
         }
         finally {
            try {
               if(outw != null)
                  outw.close();
               if(socket != null)
                  socket.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
            
         }
      }
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