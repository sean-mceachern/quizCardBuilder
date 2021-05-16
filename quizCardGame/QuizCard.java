
import java.io.*;

// This is the helper class to set the questions and answers to be displayed.

public class QuizCard implements Serializable{
	private String uniqueID;
	private String category;
	private String question;
	private String answer;
	private String hint;

	public QuizCard(String q, String a) {
		question = q;
		answer = a;
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	} 
}












