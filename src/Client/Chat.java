package Client;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;

import Model.Messenger;
import Model.User;


public class Chat extends JPanel {

	private JTextField txtContent;
	private static JPanel mess;
	Socket socket;
	
	public String filedialog() {
		String filename = null;
		try {
			FileDialog fd= new FileDialog(new Dialog(new Frame()),"Open", FileDialog.LOAD);
			File file1 = null;
			FileSystemView fileSystemView = FileSystemView.getFileSystemView();
			File[] root = fileSystemView.getRoots();
			for(File fileSystemRoot : root) {
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
				file1 = (File) node.getUserObject();
				System.out.println("file1 :"+file1);
				File[] files = fileSystemView.getFiles(file1, true);
				System.out.println("files :"+files);
			}
			fd.setDirectory(file1.getAbsolutePath());
			fd.setVisible(true);
			filename = fd.getDirectory()+fd.getFile();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return filename;
	}

	public Chat(User user) {
		this.socket = user.getSocket();
		setAutoscrolls(true);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.text);
		add(panel);
		panel.setLayout(null);
		
		JButton btnSend = new JButton("");
		btnSend.setBackground(SystemColor.text);
		btnSend.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSend.setPreferredSize(new Dimension(33, 10));
		btnSend.setIconTextGap(1);
		btnSend.setIcon(new ImageIcon("D:\\SOURCE_CODE\\JAVA\\GiuaKy\\Image\\sent-icon.png"));
		btnSend.setBorder(null);
		btnSend.setBounds(402, 398, 57, 27);
		panel.add(btnSend);
		
		txtContent = new JTextField();
		txtContent.setBackground(SystemColor.control);
		txtContent.setBounds(0, 398, 392, 27);
		panel.add(txtContent);
		txtContent.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(526, 387));
		scrollPane.setOpaque(false);
		scrollPane.setBounds(0, 0, 526, 387);
		panel.add(scrollPane);
		
		mess = new JPanel();
		mess.setBackground(new Color(255, 255, 255));
		mess.setLayout(new BoxLayout(mess, BoxLayout.PAGE_AXIS));
		
		scrollPane.setViewportView(mess);
		Action action = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String content = txtContent.getText().trim();
				String[] SPlit = content.split(" ");
				System.out.println(SPlit[0]);
				if(content.equals("QUIT")) {
					Client.QUIT=1;
					System.out.println("QUIT: "+Client.QUIT);
				}
				
				if (content.length() > 0) {
					try {
						Messenger messenger = new Messenger("", content);
						addMessenger(messenger);
						Client.check = true;
						Client.kickcount =0;
						System.out.println("Chat: "+Client.kickcount);
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
						objectOutputStream.writeObject(new Messenger(user.getName(), content));
						objectOutputStream.flush();
						txtContent.setText("");
						txtContent.requestFocusInWindow();
					} catch (Exception e1) {
						System.out.println(e1.getMessage());
						e1.printStackTrace();
					}
				}
			}
		};
		txtContent.addActionListener(action);
		JButton btnsendIcon = new JButton("");
		btnsendIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File f=new File(filedialog());
					if (f.exists()) {
						Client.check = true;
						System.out.println(f.exists());
						FileInputStream imgg = new FileInputStream(f);
						byte i[] = new byte[(int) f.length()];
						imgg.read(i, 0, (int) f.length());
						Model.Messenger messenger = new Model.Messenger("", i);
						addMessenger(messenger);
						ObjectOutputStream objectOutputStream;
						objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
						objectOutputStream.writeObject(new Messenger(user.getName(),i));
						objectOutputStream.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnsendIcon.setIcon(new ImageIcon("D:\\SOURCE_CODE\\JAVA\\GiuaKy\\Image\\image-icon.png"));
		btnsendIcon.setBackground(SystemColor.text);
		btnsendIcon.setBorder(null);
		btnsendIcon.setBounds(469, 398, 57, 27);
		panel.add(btnsendIcon);
		
		btnSend.addActionListener(action);
	}
	
	public static void addMessenger(Messenger messenger) {
		ChatContent chatContent = null;
		if (messenger.getContent() != null) {
			chatContent = new ChatContent(messenger.getName(),messenger.getContent());
		} else {
			System.out.println(messenger.toString());
			chatContent = new ChatContent(messenger.getName(),messenger.getImage());
		}
		mess.add(chatContent);
		mess.revalidate();
		int height = (int)mess.getPreferredSize().getHeight();
		Rectangle rect = new Rectangle(0,height,10,10);
		mess.scrollRectToVisible(rect);
	}

}
