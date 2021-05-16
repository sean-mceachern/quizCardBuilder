import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

// This program displays a GUI where the user can input a question
// in the question field. Then input an answer in the answer field.
// once all the desired questions and answers have been input
// click file and save to save the card set. This can then be
// loaded in the QuizCardPlayer program.


public class QuizCardBuilder {
	// instance variables
	private JTextArea question;
	private JTextArea answer;
	private ArrayList<QuizCard> cardList;
	private JFrame frame;

	public static void main(String[] args) {
		QuizCardBuilder builder = new QuizCardBuilder();
		builder.go();
	}
	// build and display GUI
	public void go() {
		frame = new JFrame("Quiz Card Builder Thingy");
		JPanel mainPanel = new JPanel();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// text area for the question. set question font
		Font bigFont = new Font("sanserif", Font.BOLD, 24);
		question = new JTextArea(6,20);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setFont(bigFont);
		// add scroll bar for question
		JScrollPane qScroller = new JScrollPane(question);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// text area for the answer, set answer font 
		answer = new JTextArea(6,20);
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		answer.setFont(bigFont);
		// add scroll bar for the answer
		JScrollPane aScroller = new JScrollPane(answer);
		aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// add "next" button, brings up next card when clicked
		JButton nextButton = new JButton("Next Card");
		// list of cards with questions and answers
		cardList = new ArrayList<QuizCard>();
		// add question and answer labels
		JLabel qLabel = new JLabel("Question:");
		JLabel aLabel = new JLabel("Answer:");
		mainPanel.add(qLabel);
		mainPanel.add(qScroller);
		mainPanel.add(aLabel);
		mainPanel.add(aScroller); 
		mainPanel.add(nextButton);
		// listens for user to click "next" button
		nextButton.addActionListener(new NextCardListener());
		// add the menu bar with "File" tab that contains New and Save options
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		// listener for "New" file button
		newMenuItem.addActionListener(new NewMenuListener());
		// add the "new" and "save" options to file menu
		saveMenuItem.addActionListener(new SaveMenuListener());
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		// add the file option to the menu bar
		menuBar.add(fileMenu);
		// add the menu 
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500,600);
		frame.setVisible(true);
	}

	// inner class: triggered when the user hits next card button. stores card in list and starts new card
	private class NextCardListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			// add the current card to the list and clear the text areas
			QuizCard card = new QuizCard(question.getText(),answer.getText());
			cardList.add(card);
			clearCard();
		}
	}

	// inner class: triggered when the user select "save" from the file menu. Saves all the cards in the current list as a set
	private class SaveMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			// bring up a file dialogue box. user can name and save the set
			QuizCard card = new QuizCard(question.getText(),answer.getText());
			cardList.add(card);
			// save menu item calls saveFile method
			JFileChooser fileSave = new JFileChooser();
			// save dialog appears in the frame
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());

		}
	}

	// inner class: triggered when the user selects "new" from the file menu. starts a new set of card
	private class NewMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			// clear out the card list and text area
			cardList.clear();
			clearCard();
		}
	}

	private void clearCard(){
		question.setText("");
		answer.setText("");
		question.requestFocus();
	}

	// called by SaveMenuListener, does the file writing 
	private void saveFile(File file) {
		// iterate through the list of cards, write each to textfile (in a parsable way)
		try {
			// "to cover later in the textbook..."
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			// iterate through cardList and write them out one card per line
			for(QuizCard card:cardList) {
				writer.write(card.getQuestion() + "/");
				writer.write(card.getAnswer() + "\n");
			}
			writer.close();
		} catch(IOException ex) {
			System.out.println("couldn’t write the cardList out");
			ex.printStackTrace();
		}
	}
}

















