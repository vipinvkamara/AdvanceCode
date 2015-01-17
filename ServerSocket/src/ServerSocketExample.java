import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerSocketExample {

	public static void main(String[] args) {
		try{
			ServerSocket myServerSocket=new ServerSocket(8888);
			System.out.println("Listening in 8888, Still Waiting for a connection");
			Socket mySocket=myServerSocket.accept();
			System.out.println("Connected Successfully");

			InputStream ins=mySocket.getInputStream();
			String s="";
			byte[] b=new byte[10];

			while(ins.read(b)>0){
				s=s+(new String(b))	;
				b=new byte[10];
			}

			mySocket.close();
			myServerSocket.close();

			System.out.println("Message Received:: \n"+s);
		}catch(Exception e){

		}
	}
}
