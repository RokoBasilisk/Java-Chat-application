package Client;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Model.Messenger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;

import javax.swing.border.CompoundBorder;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.util.Locale;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.BoxLayout;
import java.awt.Point;
import javax.swing.DebugGraphics;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

public class ChatContent extends JPanel {
	
	FlowLayout flowLayout;
	JLabel lblName;
	JLabel lblContent;

	public ChatContent(String name, String content ) {
		
		setBackground(Color.WHITE);
		setMaximumSize(new Dimension(32767, 50));
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		lblContent = new JLabel("Xin Chao");
		lblName = new JLabel("K");
		
		if (name.equals("") || name.equals("Server")) {
			if (name.equals("Server")) {
				flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
				lblContent.setBackground(Color.WHITE);
				lblContent.setFont(new Font("Tahoma", Font.ITALIC, 11));
			} else {
				flowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
				lblContent.setBackground(Color.YELLOW);
				lblContent.setFont(new Font("Tahoma", Font.PLAIN, 13));
			}
			
			lblName.setVisible(false);
			setBorder(new EmptyBorder(5, 0, 5, 10));
		} else {	
			flowLayout = new FlowLayout(FlowLayout.RIGHT, 0, 0);
			setBorder(new EmptyBorder(5, 10, 5, 0));
			lblContent.setBackground(new Color(0,204,255));
		}
		setLayout(flowLayout);
		lblName.setOpaque(true);
		lblName.setBorder(new EmptyBorder(9, 12, 9, 12));
		lblName.setBackground(Color.WHITE);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblName.setText((name.equals("")) ? "" : name.toUpperCase());
		add(lblName);
		
		JLabel lblNewLabel = new JLabel(" ");
		lblNewLabel.setBorder(new EmptyBorder(2, 5, 2, 5));
		add(lblNewLabel);
		
		lblContent.setBorder(new EmptyBorder(11, 15, 11, 15));
		lblContent.setDisplayedMnemonicIndex(2);
		lblContent.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblContent.setForeground(new Color(0, 0, 0));
		lblContent.setOpaque(true);
		lblContent.setHorizontalAlignment(SwingConstants.LEFT);
		lblContent.setText(content);
		add(lblContent);
	}
	
	public ChatContent (String name, byte[] img) {
		setBackground(Color.WHITE);
		setMaximumSize(new Dimension(32767, 70));
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		lblContent = new JLabel();
		lblName = new JLabel("K");
		
		if (name.equals("")) {
			flowLayout = new FlowLayout(FlowLayout.RIGHT, 0, 0);
			lblName.setVisible(false);
			setBorder(new EmptyBorder(5, 0, 5, 10));
		} else {	
			flowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
			setBorder(new EmptyBorder(5, 10, 5, 0));
		}
		setLayout(flowLayout);
		lblName.setOpaque(true);
		lblName.setBorder(new EmptyBorder(9, 12, 9, 12));
		lblName.setBackground(new Color(255, 0, 51));
		lblName.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblName.setText((name.equals("")) ? "" : name.toUpperCase());
		add(lblName);
		
		JLabel lblNewLabel = new JLabel(" ");
		lblNewLabel.setBorder(new EmptyBorder(2, 5, 2, 5));
		add(lblNewLabel);
		
		lblContent.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblContent.setHorizontalAlignment(SwingConstants.LEFT);
		lblContent.setIcon(reIcon(img));
		add(lblContent);
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ChatContent( Messenger messenger ) {
		setBackground(Color.WHITE);
		setMaximumSize(new Dimension(32767, 50));
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		lblContent = new JLabel("Xin Chao");
		
		flowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
		setBorder(new EmptyBorder(5, 10, 5, 0));
		lblContent.setBackground(Color.LIGHT_GRAY);
		setLayout(flowLayout);
		
		JLabel lblNewLabel = new JLabel(" ");
		lblNewLabel.setBorder(new EmptyBorder(2, 5, 2, 5));
		add(lblNewLabel);
		
		lblContent.setBorder(new EmptyBorder(11, 15, 11, 15));
		lblContent.setDisplayedMnemonicIndex(2);
		lblContent.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblContent.setForeground(new Color(0, 0, 0));
		lblContent.setOpaque(false);
		lblContent.setHorizontalAlignment(SwingConstants.LEFT);
		lblContent.setText(messenger.getContent());
		add(lblContent);
		lblName = new JLabel("K");
		lblName.setOpaque(true);
		lblName.setBorder(new EmptyBorder(9, 12, 9, 12));
		lblName.setBackground(new Color(248, 248, 255));
		lblName.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblName.setText(messenger.getName().substring(0, 1).toUpperCase());

	}
	
	public ImageIcon reIcon(byte[] image) {
		ImageIcon img = new ImageIcon(image);
		return img;
	}

}
