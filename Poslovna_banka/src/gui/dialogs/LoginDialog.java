package gui.dialogs;

import gui.main.form.MainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import database.DBConnection;

public class LoginDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	private LoginPanel panel;
	
	public LoginDialog(JFrame parent) 
	{
		super(parent, "Ulogujte se" , true);
		setSize(500,150);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		
		panel = new LoginPanel(this);
		
		JPanel pan = new JPanel();
		Border blackline = BorderFactory.createLineBorder(Color.black);
		TitledBorder titleBorder = BorderFactory.createTitledBorder(blackline, "Ulogujte se");
		titleBorder.setTitleJustification(TitledBorder.CENTER);
		pan.setBorder(titleBorder);
		
		JPanel panButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		Dimension dim = new Dimension(100, 26);
		
		JButton btnOk = new JButton("Prijavi se");
		btnOk.setPreferredSize(dim);
		btnOk.setDefaultCapable(true);
		getRootPane().setDefaultButton(btnOk);
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkUsername()) {
					panel.getTxtUser().requestFocus();
					return;
				}
				
				if(!checkPassword()) {
					panel.getTxtPass().requestFocus();
					return;
				}
								
				String username = panel.getTxtUser().getText().trim();
				String pass = new String(panel.getTxtPass().getPassword());
				
				try 
				{
					if(DBConnection.getDatabaseWrapper().checkUsernameAndPassword(username, pass))
					{
						LoginDialog.this.dispose();
					}
					else 
					{
						panel.getTxtUser().requestFocus();
						panel.getTxtUser().setText(username);
						panel.getTxtPass().setText("");
						JOptionPane.showMessageDialog(null, "Pogresno korisnicko ime i/ili lozinka.", "Greska", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} 
				catch (SQLException e1) 
				{
					panel.getTxtUser().requestFocus();
					panel.getTxtUser().setText(username);
					panel.getTxtPass().setText("");
					JOptionPane.showMessageDialog(null, "Pogresno korisnicko ime i/ili lozinka.", "Greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		
		JButton btnCancel= new JButton("Cancel");
		btnCancel.setPreferredSize(dim);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getInstance().dispose();
				MainFrame.getInstance().setVisible(false);
				System.exit(0);
			}
		});
				
		panButton.add(btnOk);
		panButton.add(btnCancel);	
		
		pan.add(panel, BorderLayout.NORTH);
		pan.add(panButton, BorderLayout.SOUTH);
		
		add(pan);
	}
	
	private void refreshColor() {
		panel.getTxtUser().setBackground(Color.WHITE);
		panel.getTxtPass().setBackground(Color.WHITE);
	}
	
	private boolean checkUsername() {
		if(panel.getTxtUser().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Unesite korisnicko ime.", "Greska", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean checkPassword() {
		if(((JTextField)panel.getTxtPass()).getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Unesite lozniku.", "Greska", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	private class LoginPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private JLabel lblUser;
		private JLabel lblPass;
		
		private JTextField txtUser;
		private JPasswordField txtPass;
		
		public LoginPanel(JDialog parent) {
			setMinimumSize(new Dimension(150, 150));
			Dimension dim = new Dimension(150,20);
			
			JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			
			lblUser = new JLabel("Korisničko ime");
			lblUser.setPreferredSize(dim);
			
			KeyAdapter ka = new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					LoginDialog.this.refreshColor();
				}
			};
			
			txtUser=new JTextField();
			txtUser.setPreferredSize(dim);
			txtUser.addKeyListener(ka);
	  	
			p1.add(lblUser);
			p1.add(txtUser);
			
			lblPass = new JLabel("Lozinka");
			lblPass.setPreferredSize(dim);
	  	
			txtPass=new JPasswordField();
			txtPass.setPreferredSize(dim);
			txtPass.addKeyListener(ka);
			
			p2.add(lblPass);
			p2.add(txtPass);
			
			BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
			setLayout(box);
			
			add(p1);
			add(p2);
		}

		public JTextField getTxtUser() {
			return txtUser;
		}

		public void setTxtUser(JTextField txtUser) {
			this.txtUser = txtUser;
		}

		public void setTxtPass(JPasswordField txtPass) {
			this.txtPass = txtPass;
		}

		public JPasswordField getTxtPass() {
			return txtPass;
		}
	}

}
