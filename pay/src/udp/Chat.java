package udp;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Chat {
	public Frame f = new Frame("������");
	public TextField tfIP = new TextField(15);
	public List list = new List(6);
	public DatagramSocket ds;
	
	Chat()
	{
		try {
			ds = new DatagramSocket(3000);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	public void init()
	{
		f.setSize(300, 300);
		f.add(list);
		Panel p = new Panel();
		p.setLayout(new BorderLayout());
		p.add("West", tfIP);
		TextField tfData = new TextField(20);
		p.add("East", tfData);
		f.add("South", p);
		new Thread(
			new Runnable()
			{
				public void run() {
					byte buf[] = new byte[1024];
					DatagramPacket dp = new DatagramPacket(buf,1024);
					while(true) {
						try {
							ds.receive(dp);
						} catch (IOException e) {
							e.printStackTrace();
						}
						list.add(new String(buf,0,dp.getLength())
								+ ":from" + 
								dp.getAddress().getHostAddress(),0);
					}
						
				}
			}
		).start();
		f.setVisible(true);
		f.setResizable(false);
		f.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					ds.close();
					f.setVisible(false);
					f.dispose();
					System.exit(0);
				}
			}
		);
		tfData.addActionListener(
			new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent e) {
					byte[] buf;
					buf = e.getActionCommand().getBytes();
					try {
						DatagramPacket dp = new DatagramPacket(
								buf, 
								buf.length,
								InetAddress.getByName(tfIP.getText()),
								3000);
						ds.send(dp);
					} catch(Exception ex) {
						ex.printStackTrace();
					}
					((TextField) e.getSource()).setText("");
				}
			}
		);
	}
	public static void main(String[] args) {
		Chat chat = new Chat();
		chat.init();
	}

}
