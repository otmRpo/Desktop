package Forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.ServerDaoRemote;
import entities.User;

public class UserF extends JFrame {

	private JPanel contentPane;

	private DefaultTableModel model;
    private int id = -1;
    private JTextField nomInput;
    private int i;
    private Long idS;
    private String[] l= null;
    private String[] l2= null;
    private String type;
    private String bloc=null;
    private Long idB;
    private JTable table;
    private JTextField prenomInput;
    private JTextField emailInput;
    private JTextField telInput;
    private JDateChooser dateChooser;
    
    public static ServerDaoRemote lookUpSalleDaoRemote() throws NamingException {
		Hashtable<Object, Object> jndiProperties = new Hashtable<Object, Object>();
		
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:4200");
		final Context context = new InitialContext(jndiProperties);

		return (ServerDaoRemote) context.lookup("ejb:EarLocalisation/Server/ServiceDao!dao.ServerDaoRemote");}
	/**
	 * Launch the application.
	 */
 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserF frame = new UserF();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void recharger() {
        model.setRowCount(0);
        try {
        	ServerDaoRemote stub = lookUpSalleDaoRemote();
			List<User> users =stub.findAllUsers();
            for (User u : users) {
                model.addRow(new Object[]{
                    u.getIdUser(),
                    u.getNom(),
                    u.getPrenom(),
                    u.getEmail(),
                    u.getTel(),
                    u.getDate()
                       
                }
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	/**
	 * Create the frame.
	 * 
	 */
	/**
	 * Create the frame.
	 */
	public UserF() {
		setTitle("GS User");
		
		getContentPane().setBackground(new Color(0, 153, 255));
		//updateComboBoxBloc();
		
	
		setResizable(true);
		setBounds(100, 100, 824, 435);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(0, 0, 0));
		panel.setBackground(new Color(0, 153, 255));
		panel.setBounds(10, 11, 467, 376);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nom :");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel.setBounds(27, 13, 53, 14);
		panel.add(lblNewLabel);
		
		nomInput = new JTextField();
		nomInput.setBounds(198, 12, 114, 20);
		panel.add(nomInput);
		nomInput.setColumns(10);
		
		
		JButton add = new JButton("ajouter");
		add.setToolTipText("add");
		add.setFont(new Font("Calibri", Font.BOLD, 18));
		add.setForeground(new Color(0, 0, 0));
		add.setBackground(new Color(224, 255, 255));
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					String nom=nomInput.getText();
					String prenom=prenomInput.getText();
					String email=emailInput.getText();
					String tel=telInput.getText();
					Date dateNais=dateChooser.getDate();

					User b=new User(nom,prenom,email,tel,dateNais);
					stub.create(b);
					recharger();
					 nomInput.setText("");
						prenomInput.setText("");
						emailInput.setText("");
						telInput.setText("");
						dateChooser.setCalendar(null);
					
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
			}
				

			
		});
		
		
		add.setBounds(27, 218, 124, 31);
		panel.add(add);
		
		JButton update = new JButton("modifier");
		update.setBackground(new Color(224, 255, 255));
		update.setForeground(new Color(0, 0, 0));
		update.setFont(new Font("Calibri", Font.BOLD, 18));
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					
					 stub.update(stub.findUserById(idB),nomInput.getText(),prenomInput.getText(),emailInput.getText(),telInput.getText(),dateChooser.getDate());
						
					 recharger();
					
					 nomInput.setText("");
						prenomInput.setText("");
						emailInput.setText("");
						telInput.setText("");
						dateChooser.setCalendar(null);
					
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
			}
		});
		update.setBounds(177, 218, 124, 31);
		panel.add(update);
		
		JButton delete = new JButton("supprimer");
		delete.setFont(new Font("Calibri", Font.BOLD, 18));
		delete.setForeground(new Color(0, 0, 0));
		delete.setBackground(new Color(224, 255, 255));
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					  System.out.println(idB);
					  stub.delete(stub.findUserById(idB));
					
					 recharger();
					 nomInput.setText("");
						prenomInput.setText("");
						emailInput.setText("");
						telInput.setText("");
						dateChooser.setCalendar(null);
					
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
				
			}
		});
		delete.setBounds(335, 219, 124, 29);
		panel.add(delete);
		
		prenomInput = new JTextField();
		prenomInput.setColumns(10);
		prenomInput.setBounds(198, 42, 114, 20);
		panel.add(prenomInput);
		
		emailInput = new JTextField();
		emailInput.setColumns(10);
		emailInput.setBounds(198, 131, 114, 20);
		panel.add(emailInput);
		
		telInput = new JTextField();
		telInput.setColumns(10);
		telInput.setBounds(198, 162, 114, 20);
		panel.add(telInput);
		
		JLabel lblPrenom = new JLabel("Prenom :");
		lblPrenom.setForeground(new Color(0, 0, 0));
		lblPrenom.setFont(new Font("Calibri", Font.BOLD, 18));
		lblPrenom.setBounds(27, 43, 77, 14);
		panel.add(lblPrenom);
		
		JLabel lblNewLabel_1_1 = new JLabel("Email :");
		lblNewLabel_1_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1_1.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(27, 132, 53, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Tel :");
		lblNewLabel_1_1_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1_1_1.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel_1_1_1.setBounds(27, 163, 53, 14);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Date Naissance :");
		lblNewLabel_1_1_1_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1_1_1_1.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel_1_1_1_1.setBounds(27, 89, 124, 14);
		panel.add(lblNewLabel_1_1_1_1);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(198, 83, 114, 20);
		panel.add(dateChooser);
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(487, 11, 311, 383);
		getContentPane().add(scrollPane);
////////////////////////////////////////////////////////	
		table = new JTable();
		table.setForeground(new Color(0, 0, 0));
		table.setBackground(new Color(173, 216, 230));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				i=table.getSelectedRow();
				 idB=Long.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString());
				 System.out.println(idB.toString());
				nomInput.setText(model.getValueAt(i, 1).toString());
				prenomInput.setText(model.getValueAt(i, 2).toString());
				emailInput.setText(model.getValueAt(i, 3).toString());
				telInput.setText(model.getValueAt(i, 4).toString());	
				
			}
		});
		model=new DefaultTableModel();
		Object[] column = {"Id_User","Nom","Prenom","Email","Tel","Date_Naissance"};
		Object[] row=new Object[0];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		recharger();
		
	}

}
