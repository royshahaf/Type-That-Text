import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

 private JPanel contentPane;
 private JTextField answerField;
 private JTextField pointsField;
 private JTextField incorrectField;
 private JTextField timeField;
 private JTextField matchField;
 private int points = 0;
 private int incorrectPoints = 0;
 private int countTime = 0;
 private String username;

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
  contentPane = new JPanel();
  contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
  setContentPane(contentPane);
  contentPane.setLayout(null);

  JLabel titleLabel = new JLabel("Type That Text");
  titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
  titleLabel.setForeground(Color.RED);
  titleLabel.setBounds(149, 11, 151, 27);
  contentPane.add(titleLabel);

  answerField = new JTextField();
  answerField.setBounds(149, 237, 151, 20);
  contentPane.add(answerField);
  answerField.setColumns(10);

  pointsField = new JTextField();
  pointsField.setEditable(false);
  pointsField.setBounds(390, 115, 86, 20);
  contentPane.add(pointsField);
  pointsField.setColumns(10);

  incorrectField = new JTextField();
  incorrectField.setEditable(false);
  incorrectField.setBounds(390, 171, 86, 20);
  contentPane.add(incorrectField);
  incorrectField.setColumns(10);

  timeField = new JTextField();
  timeField.setBounds(390, 227, 86, 20);
  contentPane.add(timeField);
  timeField.setColumns(10);

  matchField = new JTextField();
  matchField.setEditable(false);
  matchField.setBounds(149, 171, 151, 20);
  contentPane.add(matchField);
  matchField.setColumns(10);

  JButton btnPlay = new JButton("Play");
  btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 14));
  btnPlay.setBounds(174, 81, 89, 33);
  contentPane.add(btnPlay);

  JButton btnFinish = new JButton("Finish");
  btnFinish.setFont(new Font("Tahoma", Font.PLAIN, 14));
  btnFinish.setBounds(174, 274, 89, 33);
  contentPane.add(btnFinish);

  JLabel lblTime = new JLabel("Your time (s)");
  lblTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
  lblTime.setBounds(390, 202, 107, 14);
  contentPane.add(lblTime);

  JLabel lblPoints = new JLabel("Points Scored");
  lblPoints.setFont(new Font("Tahoma", Font.PLAIN, 16));
  lblPoints.setBounds(390, 90, 107, 14);
  contentPane.add(lblPoints);

  JLabel lblIncorrect = new JLabel("Incorrect");
  lblIncorrect.setFont(new Font("Tahoma", Font.PLAIN, 16));
  lblIncorrect.setBounds(390, 146, 86, 14);
  contentPane.add(lblIncorrect);

  JLabel lblMatch = new JLabel("Match");
  lblMatch.setFont(new Font("Tahoma", Font.PLAIN, 16));
  lblMatch.setBounds(199, 146, 46, 14);
  contentPane.add(lblMatch);

  JLabel lblAnswer = new JLabel("Answer");
  lblAnswer.setFont(new Font("Tahoma", Font.PLAIN, 16));
  lblAnswer.setBounds(194, 212, 74, 14);
  contentPane.add(lblAnswer);

  JButton btnUsername = new JButton("Username");
  btnUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
  btnUsername.setBounds(21, 17, 89, 20);
  contentPane.add(btnUsername);

  JButton btnHowToPlay = new JButton("How to play");
  btnHowToPlay.setFont(new Font("Tahoma", Font.PLAIN, 12));
  btnHowToPlay.setBounds(369, 16, 107, 20);
  contentPane.add(btnHowToPlay);

  btnUsername.addMouseListener(new MouseAdapter() {
   @Override
   public void mouseClicked(MouseEvent arg0) {
    username = JOptionPane.showInputDialog(btnUsername, "Enter a username");
    if (username.trim().isEmpty()) {
     JOptionPane.showMessageDialog(answerField, "Enter a valid name");
    } else {
     JOptionPane.showMessageDialog(answerField, "Success!\n Welcome" + " " + username + " " + "enjoy the game!");
    }
   }
  });

  if (username == null) {
   username = "Player";
  }

  //Link to a website I made just for an extra feature
  btnHowToPlay.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent arg0) {
    Desktop browser = Desktop.getDesktop();
    try {
     browser.browse(new URI("https://typethattextapp.yolasite.com/"));
    } catch (IOException | URISyntaxException err) {}
   }

  });

  btnPlay.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {}
  });

  //Makes sure the TextField is empty first and then will generate a random string for the user to match.
  btnPlay.addMouseListener(new MouseAdapter() {
   @Override
   public void mouseClicked(MouseEvent arg0) {
    matchField.setText("");
    StringGenerator sg = new StringGenerator();
    matchField.setText(sg.randomString());
    matchField.setHorizontalAlignment(JTextField.CENTER);
    answerField.setHorizontalAlignment(JTextField.CENTER);

   }
   //A Timer to stop the game after 60 seconds, once the user releases the mouse button on play.
   @Override
   public void mouseReleased(MouseEvent arg0) {
    Timer timer = new Timer(6000 * 10, new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
      dispose();
      JOptionPane.showMessageDialog(null, username + ", it's game over!\n" + "You scored " + points + " correct points\n" + "AND\n" + "You scored " + incorrectPoints + " incorrect points");
     }
    });
    timer.setRepeats(false);
    timer.start();
   }
  });

  //Times the users input, also ensures validation
  answerField.addKeyListener(new KeyAdapter() {
   long startEntry;
   public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
     countTime++;
     if (countTime % 2 != 0) {
    	 startEntry = System.currentTimeMillis();
     }
     if (countTime % 2 == 0) {
      long endEntry = System.currentTimeMillis();
      long timeDifference = (endEntry - startEntry);
      timeField.setText(String.valueOf(timeDifference / 1000.0));
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
      points--;
      pointsField.setText(String.valueOf(points));
     }
    }
   }
  });

  //Reset fields when the button is released, and add a new string
  answerField.addKeyListener(new KeyAdapter() {
   public void keyReleased(KeyEvent ke) {
    if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
     matchField.setText("");
     answerField.setText("");
     StringGenerator sg = new StringGenerator();
     matchField.setText(sg.randomString());
    }
   }

  });
 }

}