package Interface;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;


public class progress implements Runnable {

	public JFrame frame;
	private JProgressBar progressBar;
	private JProgressBar progressBar_1;
	private Thread t;
	private JLabel lblNewLabel;
	private Swarm s;
	private JLabel lblNewLabel_1;
	
	public void setProgressDataBase(int value) {
		this.progressBar.setValue(value);
	}
	
	public void setParticleProgress(int value) {
		this.progressBar_1.setValue(value);
	}
	
	public JLabel getLabel () {
		return this.lblNewLabel;
	}
	
	public JLabel getLabelParticle () {
		return this.lblNewLabel_1;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		
		progress window = new progress();
		window.frame.setVisible(true);
		window.updateProgress();

	}

	public progress() throws IOException {
		initialize();
		this.lblNewLabel.setText("Fase 1 ...");
		this.lblNewLabel_1.setText("Fase 2 ...");

		t = new Thread(this);
		t.start();
	}

	public void updateProgress() throws InterruptedException, IOException {

		s = new Swarm(this);
		s.frame.setVisible(true);
		while(s.complet == 0) {
			//this.frame.setTitle("A Carregar " + this.s.getConfig().getTotalProgress() + " %");
		}
		frame.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 169);
		frame.setTitle("A Carregar ....");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		
		lblNewLabel = new JLabel("New label");
		
		lblNewLabel_1 = new JLabel("New label");
		
		progressBar_1 = new JProgressBar();
		progressBar_1.setStringPainted(true);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel)
						.addComponent(progressBar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
						.addComponent(progressBar_1, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addComponent(progressBar_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		frame.setVisible(true);
		while (true) {			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			//	// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
