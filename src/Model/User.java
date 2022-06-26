package Model;

import java.net.Socket;

public class User {
	private String name;
	private String id;
	private Socket socket;
	
	public User(String name,String id,Socket socket) {
		super();
		this.name = name;
		this.id = id;
		this.socket = socket;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	
	
}
