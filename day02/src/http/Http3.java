package http;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class Http3 {
	
	

	public static void main(String[] args) throws Exception {
		
		//파일 다운로드
		
		URL url = new URL("http://127.0.0.1/ppt.zip");
		InputStream in= url.openStream();
		
		//저장하는 경로 설정
		FileOutputStream out= new FileOutputStream("down.zip");
		
		int i=0;
		while(true) {
			i=in.read();
			System.out.println(i);
			
			if(i!=-1) {
				break;
			}
			out.write(i);
		}
		in.close();
		out.close();
	}
}
