package Interface;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;

import config.config;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class Swarm {

	public JFrame frame;
	private DrawSwarm d;
	private JButton btnIniciar;
	private Thread t;
	private JButton btnNewButton;
	private config config;
	private JSlider slider;
	private JCheckBox chckbxDebugOnoff;
	private progress progress;
	public int complet = 0;
	
	public config getConfig() {
		return config;
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public Swarm(progress progress) throws IOException {
		this.progress = progress;
		initialize();
		complet = 1;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 696, 483);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Configura\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//DrawSwarm
		try {
			//Numero de particula etc
			//arg[2] = nrFlores --- arg[3] = nrParticles
			config = new config(1, 5, 10, 20,this.progress); 
			d = new DrawSwarm(config);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		d.setBorder(new TitledBorder(null, "Desenho", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(d, GroupLayout.PREFERRED_SIZE, 442, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addComponent(d, GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
					.addGap(16))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		
		btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				t = new Thread(new Runnable() {
					
					@SuppressWarnings("static-access")
					@Override
					public void run() {
						// TODO Auto-generated method stub
						while(true) {
							
							d.repaint();
							try {
								t.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
				t.start();
				System.out.println("Sai daqui");
			}
		});
		
		btnNewButton = new JButton("Sair");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				t.stop();
			}
		});
		
		JLabel lblVelocidade = new JLabel("Velocidade");
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				config.setVelocidade(slider.getValue());
			}
		});
		slider.setMaximum(5);
		slider.setValue(1);
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		JLabel lblNmeroPartculas = new JLabel("N\u00FAmero Part\u00EDculas");
		
		JSpinner spinner = new JSpinner();
		spinner.setValue(this.config.getNrParticle());
		spinner.setEnabled(false);
		
		JLabel lblNmeroDeFlores = new JLabel("N\u00FAmero de Flores");
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setValue(this.config.getNrFlowers());
		spinner_1.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("SPIINER: " + ((JSpinner)arg0.getSource()).getValue() + "");
				config.setNrFlwers(Integer.parseInt(((JSpinner)arg0.getSource()).getValue() + ""));
			}
		});
		
		chckbxDebugOnoff = new JCheckBox("Debug ON/OFF");
		chckbxDebugOnoff.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				boolean debug = chckbxDebugOnoff.isSelected();
				if(debug) {
					config.setDebug(1);
				} else {
					config.setDebug(0);
				}
			}
		});
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(slider, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(6)
					.addComponent(btnIniciar)
					.addGap(18)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
					.addGap(21))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNmeroPartculas)
					.addContainerGap(101, Short.MAX_VALUE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblVelocidade)
					.addContainerGap(148, Short.MAX_VALUE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(146, Short.MAX_VALUE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNmeroDeFlores)
					.addContainerGap(105, Short.MAX_VALUE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(145, Short.MAX_VALUE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(chckbxDebugOnoff)
					.addContainerGap(88, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(btnIniciar)
						.addComponent(btnNewButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblVelocidade)
					.addGap(1)
					.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNmeroPartculas)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNmeroDeFlores)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxDebugOnoff)
					.addGap(141))
		);
		panel_1.setLayout(gl_panel_1);
		frame.getContentPane().setLayout(groupLayout);
	}
}
