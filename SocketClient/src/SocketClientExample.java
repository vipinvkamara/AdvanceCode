import java.io.OutputStream;
import java.net.Socket;


public class SocketClientExample {

	public static void main(String[] args) {

		try {
			String myMessage="Hai, I am Stuck.";
			Socket s=new Socket("192.168.1.4", 8888);
			//Socket s=new Socket("111.92.19.74", 8888);

			OutputStream os=s.getOutputStream();


			os.write(myMessage.getBytes());
			os.flush();
			os.close();
			s.close();

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

}
