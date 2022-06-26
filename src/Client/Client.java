package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.border.LineBorder;

import Model.Messenger;
import Model.User;
import Server.Event;

import javax.swing.JScrollPane;

public class Client extends JFrame {

	private JPanel contentPane;
	Socket socket;
	JPanel pnlListUser;
	JPanel pnlChat;
	String name;
	private int position =0;
	MessagesThread messagesThread;
	public static boolean check = true;
	public static int QUIT = 0;
	public static int kickcount = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void connection (String name) throws UnknownHostException, IOException {
		Messenger messenger = new Messenger(name,name);
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(messenger);
		objectOutputStream.flush();
	}
	
	public void register () {
		try {
			while (true) {
				name = JOptionPane.showInputDialog("Nhập tên của bạn");
				name = upPerCase(name);
				if (name == null) {
					System.exit(0);
				} else if(!name.equals("")&&position ==0) {
					socket = new Socket("localhost",1201);
					connection(name);
					User user = new User(name, "", socket);
					Chat chatPanel = new Chat(user);
					JOptionPane.showMessageDialog(null,"Wellcome "+name);
					messagesThread = new MessagesThread(); 
					messagesThread.start();
					pnlChat.add(chatPanel,BorderLayout.CENTER);
					pnlChat.revalidate();
					position=1;
					break;
				}
				else if(!name.equals("")&&position==1)
				{
					socket = new Socket("localhost",1201);
					connection(name);
					User user = new User(name, "", socket);
					Chat chatPanel = new Chat(user);
					JOptionPane.showMessageDialog(null,"Wellcome "+name);
					messagesThread = new MessagesThread(); 
					messagesThread.start();
					pnlChat.add(chatPanel,BorderLayout.CENTER);
					pnlChat.setLayout(new BorderLayout(6, 0));
					pnlChat.revalidate();
					break;
				}
				else {
					JOptionPane.showMessageDialog(null,"Tên của bạn còn trống");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String upPerCase(String name) {
		String[] arr = name.split(" ");
		name = "";
		for (String x : arr) {
			name = name + (x.substring(0, 1).toUpperCase() + x.substring(1));
			name = name + " ";
		}
		return name;
	}

	/**
	 * Create the frame.
	 */
	public Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 810, 559);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.text);
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("-");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setState(JFrame.ICONIFIED);
			}
		});
		lblNewLabel.setForeground(new Color(34,112,147));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(700, 0, 34, 25);
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(lblNewLabel);
		
		JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
					objectOutputStream.writeObject(new Messenger(name, Event.DICONNECT));
					objectOutputStream.flush();
					messagesThread.stop();
					if(socket.isConnected()==true) socket.close();
					System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		lblX.setHorizontalTextPosition(SwingConstants.CENTER);
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setForeground(Color.RED);
		lblX.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblX.setBounds(733, 0, 34, 25);
		lblX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(lblX);
		
		pnlChat = new JPanel();
		pnlChat.setBounds(10, 76, 526, 425);
		pnlChat.setLayout(new BorderLayout(0, 0));
		contentPane.add(pnlChat);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(546, 76, 211, 425);
		contentPane.add(scrollPane);
		
		pnlListUser = new JPanel();
		pnlListUser.setBackground(new Color(255, 255, 255));
		pnlListUser.setLayout(new BoxLayout(pnlListUser, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(pnlListUser);
		
		JLabel lblNewLabel_1 = new JLabel("Chat Room");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(264, 11, 252, 42);
		contentPane.add(lblNewLabel_1);
		
		JPanel pnlName = new JPanel();
		pnlName.setBounds(10, 11, 146, 42);
		contentPane.add(pnlName);
		pnlName.setLayout(new BoxLayout(pnlName, BoxLayout.X_AXIS));
		
		register();
		pnlName.add(new ChatContent(new Messenger(name.trim(), name.trim())));
		pnlName.revalidate();
		
//		try {
//			if(kickcount==3) {
//				if(messagesThread.isAlive()==true) messagesThread.stop();
//				if(socket.isConnected()==true) socket.close();
//				System.exit(0);
//		}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		
		Timeout();
		quitt();

	}
	private void quitt() {
		TimerTask timert = new TimerTask() {
			
			@Override
			public void run() {
           		if(QUIT==1) {
        			try {
        				System.out.println("CLIENT.QUIT: "+QUIT);
        				System.exit(0);
        				if(socket.isConnected()) socket.close();
        				messagesThread.stop();
        			} catch (Exception e5) {
        				e5.printStackTrace();
        			}
        		}
			}
		};
        Timer timer = new Timer("Timer");
        timer.schedule(timert, 0, 3000);
	}
	
	private void Timeout() {
		JOptionPane jop = new JOptionPane("Bạn Đang Treo Máy", JOptionPane.WARNING_MESSAGE);
		JDialog dialog = jop.createDialog(null, "Cảnh báo");
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!check) {
					kickcount = kickcount + 1;
					System.out.println("Client: "+kickcount);
					try {
						if(kickcount==1) {
							messagesThread.stop();
							socket.close();
							System.exit(0);
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
                	if (dialog.isShowing()) {
						dialog.dispose();
					}
                	dialog.show();
				} else {
					check = false;
				}
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(timerTask, 0, 15000);
	}
	
	class  MessagesThread extends Thread {
        @Override
        public void run() {
            Messenger line;
            try {
                while(true) {
                	ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                	line = (Messenger) objectInputStream.readObject();
                	if(line.getName().equals("Server")) {
                		switch (line.getEvent()) {
						case Event.IFM_DICONNECT:
						case Event.NEW_USER:
							Chat.addMessenger(line);
							break;
						case Event.UPDATE_USER:
							String s = line.getContent().replace("[", "");
							s = s.replace("]", "");
							System.out.println("s :"+ s);
							String[] list = s.split(",");
							System.out.println("list :" + list);
//							pnlListUser.removeAll();
							for(String name : list) {
								ChatContent user = new ChatContent(new Messenger(name.trim(), name.trim()));
								pnlListUser.add(user);
								pnlListUser.revalidate();
							
							}
							break;
						default:
							break;
						}
                	} else {
                		Chat.addMessenger(line);
                	}
                }
            } catch(Exception ex) {
            	ex.printStackTrace();
            }
        }
    }
}
