import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.RandomStringUtils;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class HomePage extends JFrame {

	private static final int STRING_LENGTH = 1;
	private JTextField answerField;
	private JTextField pointsField;
	private JTextField incorrectField;
	private JTextField timeField;
	private JTextField matchField;
	private int points = 0;
	private int incorrectPoints = 0;
	private int countTime = 0;
	private String username = "Player";
	private long start = -1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage frame = new HomePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public HomePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 380);
		setContentPane(createContentPanel());
	}

	private JPanel createContentPanel() {
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		contentPanel.add(createTitleLabel());

		createAndAddAnswerField(contentPanel);

		createAndAddPointsField(contentPanel);

		createAndAddIncorrectField(contentPanel);

		createAndAddTimeField(contentPanel);

		createAndAddMatchField(contentPanel);

		JButton btnPlay = createAndAddPlayButton(contentPanel);

		createAndAddFinishButton(contentPanel);

		createAndAddTimeLabel(contentPanel);

		createAndAddPointsLabel(contentPanel);

		createAndAddIncorrectLabel(contentPanel);

		createAndAddMatchLabel(contentPanel);

		createAndAddAnswerLabel(contentPanel);

		JButton btnUsername = createAndAddUsernameButton(contentPanel);

		JButton btnHowToPlay = createAndAddHowToPlayButton(contentPanel);

		addUsernameListener(btnUsername);

		addHowToPlayListener(btnHowToPlay);

		addPlayListener(btnPlay);

		addAnswerListener();

		return contentPanel;
	}

	private void addAnswerListener() {
		// Times the users input, also ensures validation
		answerField.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (start != -1) {
						timeField.setText(String.valueOf((System.currentTimeMillis() - start) / 1000.0));
						start = System.currentTimeMillis();
					}
					if (answerField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(answerField, "Make sure your answer isn't empty!");
					}
					if (answerField.getText().equals(matchField.getText())) {
						points++;
						pointsField.setText(String.valueOf(points));
					} else {
						incorrectPoints++;
						incorrectField.setText(String.valueOf(incorrectPoints));
						// does this make sense?
						/*points--;
						pointsField.setText(String.valueOf(points));*/
					}
				}
			}
			
			// Reset fields when ENTER button is released, and add a new string
			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					resetMatchAndAnswerFields();
				}
			}
			
		});
	}

	private void resetMatchAndAnswerFields() {
		matchField.setText("");
		answerField.setText("");
		matchField.setText(RandomStringUtils.randomAlphanumeric(STRING_LENGTH));
	}
	
	private void addPlayListener(JButton btnPlay) {
		// Makes sure the TextField is empty first and then will generate a random
		// string for the user to match.
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				start = System.currentTimeMillis();
				matchField.setHorizontalAlignment(JTextField.CENTER);
				answerField.setHorizontalAlignment(JTextField.CENTER);
				resetMatchAndAnswerFields();

			}

			// A Timer to stop the game after 60 seconds, once the user releases the mouse
			// button on play.
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Timer timer = new Timer(6000 * 10, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						start = -1;
						dispose();
						JOptionPane.showMessageDialog(null,
								username + ", it's game over!\n" + "You scored " + points + " correct points\n"
										+ "AND\n" + "You scored " + incorrectPoints + " incorrect points");
					}
				});
				timer.setRepeats(false);
				timer.start();
			}
		});
	}

	private void addHowToPlayListener(JButton btnHowToPlay) {
		// Link to a website I made just for an extra feature
		btnHowToPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Desktop browser = Desktop.getDesktop();
				try {
					browser.browse(new URI("https://typethattextapp.yolasite.com/"));
				} catch (IOException | URISyntaxException err) {
				}
			}

		});
	}

	private void addUsernameListener(JButton btnUsername) {
		btnUsername.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				username = JOptionPane.showInputDialog(btnUsername, "Enter a username");
				if (username.trim().isEmpty()) {
					JOptionPane.showMessageDialog(answerField, "Enter a valid name");
				} else {
					JOptionPane.showMessageDialog(answerField,
							"Success!\n Welcome" + " " + username + " " + "enjoy the game!");
				}
			}
		});
	}

	private JButton createAndAddHowToPlayButton(JPanel contentPanel) {
		JButton btnHowToPlay = new JButton("How to play");
		btnHowToPlay.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnHowToPlay.setBounds(369, 16, 107, 20);
		contentPanel.add(btnHowToPlay);
		return btnHowToPlay;
	}

	private JButton createAndAddUsernameButton(JPanel contentPanel) {
		JButton btnUsername = new JButton("Username");
		btnUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnUsername.setBounds(21, 17, 89, 20);
		contentPanel.add(btnUsername);
		return btnUsername;
	}

	private void createAndAddAnswerLabel(JPanel contentPanel) {
		JLabel lblAnswer = new JLabel("Answer");
		lblAnswer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAnswer.setBounds(194, 212, 74, 14);
		contentPanel.add(lblAnswer);
	}

	private void createAndAddMatchLabel(JPanel contentPanel) {
		JLabel lblMatch = new JLabel("Match");
		lblMatch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMatch.setBounds(199, 146, 46, 14);
		contentPanel.add(lblMatch);
	}

	private void createAndAddIncorrectLabel(JPanel contentPanel) {
		JLabel lblIncorrect = new JLabel("Incorrect");
		lblIncorrect.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblIncorrect.setBounds(390, 146, 86, 14);
		contentPanel.add(lblIncorrect);
	}

	private void createAndAddPointsLabel(JPanel contentPanel) {
		JLabel lblPoints = new JLabel("Points Scored");
		lblPoints.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPoints.setBounds(390, 90, 107, 14);
		contentPanel.add(lblPoints);
	}

	private void createAndAddTimeLabel(JPanel contentPanel) {
		JLabel lblTime = new JLabel("Your time (s)");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTime.setBounds(390, 202, 107, 14);
		contentPanel.add(lblTime);
	}

	private void createAndAddFinishButton(JPanel contentPanel) {
		JButton btnFinish = new JButton("Finish");
		btnFinish.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFinish.setBounds(174, 274, 89, 33);
		contentPanel.add(btnFinish);
	}

	private JButton createAndAddPlayButton(JPanel contentPanel) {
		JButton btnPlay = new JButton("Play");
		btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPlay.setBounds(174, 81, 89, 33);
		contentPanel.add(btnPlay);
		return btnPlay;
	}

	private void createAndAddMatchField(JPanel contentPanel) {
		matchField = new JTextField();
		matchField.setEditable(false);
		matchField.setBounds(149, 171, 151, 20);
		matchField.setColumns(10);
		contentPanel.add(matchField);
	}

	private void createAndAddTimeField(JPanel contentPanel) {
		timeField = new JTextField();
		timeField.setBounds(390, 227, 86, 20);
		timeField.setColumns(10);
		contentPanel.add(timeField);
	}

	private void createAndAddIncorrectField(JPanel contentPanel) {
		incorrectField = new JTextField();
		incorrectField.setEditable(false);
		incorrectField.setBounds(390, 171, 86, 20);
		incorrectField.setColumns(10);
		contentPanel.add(incorrectField);
	}

	private void createAndAddPointsField(JPanel contentPanel) {
		pointsField = new JTextField();
		pointsField.setEditable(false);
		pointsField.setBounds(390, 115, 86, 20);
		pointsField.setColumns(10);
		contentPanel.add(pointsField);
	}

	private void createAndAddAnswerField(JPanel contentPanel) {
		answerField = new JTextField();
		answerField.setBounds(149, 237, 151, 20);
		answerField.setColumns(10);
		contentPanel.add(answerField);
	}

	private JLabel createTitleLabel() {
		JLabel titleLabel = new JLabel("Type That Text");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		titleLabel.setForeground(Color.RED);
		titleLabel.setBounds(149, 11, 151, 27);
		return titleLabel;
	}

}