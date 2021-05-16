import java.util.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.awt.*; 
import java.io.*;

// in this program the user can load a previously saved card set. The questions are displayed first, 
// then once the user clicks "show answer" button the answer is displayed until all questions
// have been cycled through.

public class QuizCardPlayer {
	// instance variables
	private JTextArea display;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private QuizCard currentCard;
	private int currentCardIndex;
	private JFrame frame;
	private JButton nextButton;
	private boolean isShowAnswer;

	
	public static void main(String[] args) {
		QuizCardPlayer reader = new QuizCardPlayer();
		reader.go();
	}

	public void go() {
		// build and display GUI
		frame = new JFrame("Quiz Card Player");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif", Font.BOLD, 24);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		display = new JTextArea(10,20);
		display.setFont(bigFont);

		display.setLineWrap(true);
		display.setEditable(false);

		JScrollPane qScroller = new JScrollPane(display);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		nextButton = new JButton("Show Question");
		mainPanel.add(qScroller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load Card Set");
		loadMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(640,500);
		frame.setVisible(true);

	}
	// inner class
	class NextCardListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			// if this is a question, show the answer, otherwise show next question. 
			// Set a flag for wether we are viewing an answer or question
			if(isShowAnswer) {
				display.setText(currentCard.getAnswer());
				nextButton.setText("Next Card");
				isShowAnswer = false;
			} else {
				if(currentCardIndex < cardList.size()) { /* show next questoin */
					showNextCard();
				} else { /* last card has been shown */
					display.setText("That was the last card");
					nextButton.setEnabled(false);
				}
			}
		}
	}
	class OpenMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			// bring up a file dialog box (select file), let the user navigate to and choose a crad set to open
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
		}
	}
	private void loadFile(File file) {
		// must build an ArrayList of cards by reading them from a textfile
		// called from openMenuListener, reads the file one line at a time
		// tells the makeCard method to make a new card out of the line
		// one line in the file holds the question and answer seperated by "/"
		cardList = new ArrayList<QuizCard>();
		try {
			// bufferedReader chained to fileReader given file (chosen by user from select file dialog box)
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null) {
				makeCard(line);
			}
			reader.close();
		} catch (Exception ex) {
			System.out.println("couldn't read the card file");
			ex.printStackTrace();
		}
		showNextCard();
	}
	private void makeCard(String lineToParse) {
		// called by the loadFile method, takes a line from the text file
		// parses into two pieces (question and answer) and creates a new quiz card and adds it to the arrayList CardList
		String[] result = lineToParse.split("/");
		QuizCard card = new QuizCard(result[0],result[1]);
		cardList.add(card);
		System.out.println("made a card");
	}
	private void showNextCard() {
		currentCard = cardList.get(currentCardIndex);
		currentCardIndex++;
		display.setText(currentCard.getQuestion());
		nextButton.setText("Show Answer");
		isShowAnswer = true;

	}
}























