package Model;

import java.io.Serializable;

public class Messenger implements Serializable{
	private String name;
	private String content;
	private byte[] image;
	private String event;
	public Messenger(String name, String content, String event) {
		super();
		this.name = name;
		this.content = content;
		this.event = event;
	}
	
	public Messenger(String name, byte[] image) {
		super();
		this.name = name;
		this.image = image;
	}
	
	

	public Messenger(String name, String content) {
		super();
		this.name = name;
		this.content = content;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String toString() {
		return this.name + "\t" + this.content;
	}
	
	
	
}
