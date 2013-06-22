package Interface;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


public class progress implements Runnable {

	public JFrame frame;
	private JProgressBar progressBar;
	private int i = 0;
	private Thread t;
	private Swarm s;
	

	public static void main(String[] args) throws IOException, InterruptedException {

		progress window = new progress();
		window.frame.setVisible(true);
		window.updateProgress();

	}

	public progress() throws IOException {
		initialize();

		t = new Thread(this);
		t.start();
	}

	public void updateProgress() throws InterruptedException, IOException {

		s = new Swarm();
		s.frame.setVisible(true);
		while(s.complet == 0) {
			
		}
		frame.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 114);
		frame.setTitle("A Carregar ....");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		progressBar = new JProgressBar();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(44, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		frame.setVisible(true);
		while (true) {
			
			this.i += 5;
			this.progressBar.setValue(this.i % 100);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			//	// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
