package http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Http2 {
	public static void main(String[] args) throws Exception {
		String name="유정";
		name=URLEncoder.encode(name,"UTF-8");
		URL url= new URL("http://127.0.0.1/login?id=qq&pwd=11&name="+name);
		//"?id=qq&pwd=11&name=안뉴" 이렇게 쿼리 스트링에 한글이 들어간 채로 바로 url 객체에 넣으면 깨져서 들어감
		//그래서 한글 부분을 인코딩한 후에 넣어주어야함
		URLConnection con= url.openConnection();
		con.setConnectTimeout(5000);//5초동안 응답이 없으면 예외발생
		
		InputStream in=con.getInputStream();
		InputStreamReader ir= new InputStreamReader(in,"UTF-8");
		BufferedReader br= new BufferedReader(ir);
		
		String str=br.readLine();//여러 라인을 받아와야 하는 경우 스트링버퍼사용
		System.out.println(str);
		br.close();
	}
}
