package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JOptionPane;

import Model.Messenger;
import Model.User;

public class Server {
	ServerSocket serverSocket;
	Vector<String> users = new Vector<String>();
	Vector<UserManaging> clients = new Vector<UserManaging>();
	
	public static void main(String[] args) throws Exception {
		new Server().createServer();
	}
	
	// start createserver
	public void createServer() throws Exception{
		serverSocket = new ServerSocket(1201);
        JOptionPane.showMessageDialog(null, "Server started", "Server", JOptionPane.INFORMATION_MESSAGE);
		System.out.println("Server On");
		while(true) {
			Socket client = serverSocket.accept();
			if(client.isConnected()) {
				System.out.println("Client Tồn Tại");
				UserManaging c = new UserManaging(client);

			}
		}
	}
	// end createServer
	
	// start sendtoallclient
	public void sendtoallclient(Messenger messenger) throws IOException {
		for (UserManaging cli : clients) {
			if(!cli.user.getName().equals(messenger.getName()))
				cli.sendMessage(messenger);
		}
	}
	// end sendtoallclient
	
	// start UserManaging
	class UserManaging extends Thread {
		User user;
		String id = String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		public UserManaging(Socket client) throws Exception {
			InputStream is = client.getInputStream();
			ObjectInputStream objIS = new ObjectInputStream(is);
			Messenger messenger = (Messenger) objIS.readObject();
			this.user = new User(messenger.getContent(), id, client);
			clients.add(this);
			users.add(user.getName());
			sendtoallclient(new Messenger("Server", this.user.getName() + " Join Chat Room", Event.NEW_USER ));
			sendtoallclient(new Messenger("Server", user.getName().toString(), Event.UPDATE_USER));
			start();
		}
		
		public void sendMessage(Messenger messenger) throws IOException {
			OutputStream os = user.getSocket().getOutputStream();
			ObjectOutputStream objOS = new ObjectOutputStream(os);
			objOS.writeObject(messenger);
			objOS.flush();
		}
		
		// start run
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Messenger messenger = null;
			try {
				while(true) {
					if(user.getSocket().isConnected()) {
						InputStream is = user.getSocket().getInputStream();
						ObjectInputStream objIS = new ObjectInputStream(is);
						messenger = (Messenger) objIS.readObject();
						if(messenger.getContent() !=null && messenger.getContent().equals(Event.DICONNECT)) {
							clients.remove(this);
							users.remove(messenger.getName());
							sendtoallclient(new Messenger("Server", messenger.getName()+ " Đã Thoát", Event.IFM_DICONNECT));
							sendtoallclient(new Messenger("Server", messenger.getName().toString(), Event.UPDATE_USER));
							break;
						}
						sendtoallclient(messenger);
					}
					else {
						System.out.println(user.getName()+" is not connected");
					}
				}
			} catch (Exception e) {
				clients.remove(this);
				users.remove(user.getName());
				e.printStackTrace();
			}
		}
		// end run
	}
	// end UserManaging
}








