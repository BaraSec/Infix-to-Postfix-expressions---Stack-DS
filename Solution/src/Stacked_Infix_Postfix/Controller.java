package Stacked_Infix_Postfix;

import Structures.CharsLinkedStack;
import Structures.DoubleLinkedStack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable
{
	@FXML
	private ListView<String> inEx, posEx, posEv;
	@FXML
	private MenuItem export, showIn, showPost, evPost;
	@FXML
	private TextField stat;

	private ArrayList<String> list1;
	private ArrayList<String> list2;
	private ArrayList<String> list3;

	// A method that initializes the needed UI components
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		stat.setEditable(false);
		stat.setAlignment(Pos.BASELINE_CENTER);
		showIn.setDisable(true);
		showPost.setDisable(true);
		evPost.setDisable(true);
		export.setDisable(true);
	}

	// A method that shows the Infix expressions
	public void showIn()
	{
		ObservableList<String> inList = FXCollections.observableArrayList(alterList(list1));
		inEx.setItems(inList);
		stat.setText("Status: Infix Expressions were loaded successfully.");
	}

	// A method that shows the postfix expressions
	public void showPost()
	{
		ObservableList<String> postList = FXCollections.observableArrayList(list2);
		posEx.setItems(postList);
		stat.setText("Status: Postfix Expressions were loaded successfully.");
	}

	// A method that shows the postfix evaluations
	public void showEvs()
	{
		ObservableList<String> postList = FXCollections.observableArrayList(list3);
		posEv.setItems(postList);
		stat.setText("Status: Postfix Evaluations were loaded successfully.");
	}

	// A method that loads that data from the file specified
	public void loadData()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Available Data File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File file1 = fileChooser.showOpenDialog(stat.getScene().getWindow());

		File file = new File(String.valueOf(file1));
		Scanner in = null;

		ArrayList<String> eqs = new ArrayList();

		try
		{
			in = new Scanner(file);
		}
		catch (FileNotFoundException e)
		{
			stat.setText("Status: File isn't found.");
			return;
		}

		while(in.hasNextLine())
			eqs.add(in.nextLine());

		list1 = eqs;
		list2 = infixToPostfix(list1);
		list3 = findValues(list2);

		showIn.setDisable(false);
		showPost.setDisable(false);
		evPost.setDisable(false);
		export.setDisable(false);
		stat.setText("Status: Data has been loaded successfully.");
	}

	// A method that adds (Valid/Invalid) to the infix expressions list
	public ArrayList<String> alterList(ArrayList<String> list)
	{
		ArrayList<String> eq = new ArrayList<>();

		for(int j = 0; j < list.size(); j++)
		{
			char[] chars = new char[51];

			if(isValid(list.get(j)))
			{
				for(int i = 0; i <= (50 - list.get(j).length()); i++)
					chars[i] = ' ';

				eq.add(list.get(j) + String.valueOf(chars) + "| Valid");
			}
			else
			{
				for(int i = 0; i <= (50 - list.get(j).length()); i++)
					chars[i] = ' ';

				eq.add(list.get(j) + String.valueOf(chars) + "| Invalid");
			}
		}

		return eq;
	}

	// A method that checks if the infix expression is valid or not
	public boolean isValid(String str)
	{
		if(str.length() == 0)
			return false;

		int counter = 0;

		for(int i = 0; i < str.length(); i++)
		{
			if(str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/')
			{
				if(i == 0)
					return false;
				if(str.charAt(i-1) != ')' && !Character.isDigit(str.charAt(i-1)))
				{
					return false;
				}
				else if(str.charAt(i+1) != '(' && !Character.isDigit(str.charAt(i+1)))
				{
					return false;
				}
			}
			else if(i > 0 && (str.charAt(i) == '(' && Character.isDigit(str.charAt(i-1))) || i < str.length() - 1 && (str.charAt(i) == ')' && Character.isDigit(str.charAt(i+1))))
				return false;
			else if(str.charAt(i) == '(')
				counter++;
			else if(str.charAt(i) == ')')
				counter--;
		}

		if(counter == 0)
			return true;
		return false;
	}

	// A method that exits the system
	public void close()
	{
		stat.setText("Good Bye !");
		System.exit(0);
	}

	// A method that converts infix to postfix
	public ArrayList<String> infixToPostfix(ArrayList<String> exp)
	{
		ArrayList<String> resultList = new ArrayList<>();

		for(int k = 0; k < exp.size(); k++)
		{
			if(!isValid(exp.get(k)))
				continue;

			String result = "";
			CharsLinkedStack chars = new CharsLinkedStack();

			for (int i = 0; i < exp.get(k).length(); i++)
			{
				char currentChar = exp.get(k).charAt(i);

				if (Character.isDigit(currentChar))
				{
					int j = i;

					for(j = i; j < exp.get(k).length() && Character.isDigit(exp.get(k).charAt(j)); j++)
						result += exp.get(k).charAt(j);

					if(result.length() > 0 && result.charAt(result.length() - 1) != ' ')
						result += " ";

					i = j - 1;
				}

				else if (currentChar== '(')
					chars.push(currentChar);

				else if (currentChar == ')')
				{
					if(result.length() > 0 && result.charAt(result.length() - 1) != ' ')
						result += " ";

					while (!chars.isEmpty() && chars.peek() != '(')
						result += chars.pop();

					if(result.length() > 0 && result.charAt(result.length() - 1) != ' ')
						result += " ";

					chars.pop();
				}

				else
				{
					if(result.length() > 0 && result.charAt(result.length() - 1) != ' ')
						result += " ";
					while (!chars.isEmpty() && priority(currentChar) <= priority(chars.peek()))
						result += chars.pop();
					if(result.length() > 0 && result.charAt(result.length() - 1) != ' ')
						result += " ";

					chars.push(currentChar);
				}

			}

			while (chars.size() > 1)
				result += chars.pop() + " ";
			if(!chars.isEmpty())
				result += chars.pop();

			resultList.add(result);
		}

		return resultList;
	}

	// A method that returns the priority of the operation
	public int priority(char operator)
	{
		if(operator == '+' || operator == '-')
			return 1;
		else if(operator == '*' || operator == '/')
			return 2;
		return -1;
	}

	// A method that evaluates the postfix expressions
	public ArrayList<String> findValues(ArrayList<String> exp)
	{
		ArrayList<String> list4 = new ArrayList<>(exp.size());

		for(int j = 0; j < exp.size(); j++)
		{
			String temp = exp.get(j);
			String[] str = temp.split("[ ]+");
			DoubleLinkedStack as = new DoubleLinkedStack();

			for(int i = 0; i < str.length; i++)
			{
				if(Character.isDigit(str[i].charAt(0)))
					as.push(Integer.parseInt(str[i]));
				else if(str[i].charAt(0) == ' ')
					continue;
				else
				{
					switch(str[i].charAt(0))
					{
					case '+':
						as.push(as.pop() + as.pop());
						break;
					case '-':
						double op11 = as.pop();
						as.push(as.pop() - op11);
						break;
					case '*':
						as.push(as.pop() * as.pop());
						break;
					case '/':
						double op12 = as.pop();
						if(op12 != 0)
							as.push(as.pop() / op12);
						else
						{
							as.push('E');
							i = str.length;
						}
					}
				}
			}

			if(as.peek() == 'E')
				list4.add("{" + temp + "} ==> {Cannot Divide By 0}");
			else
				list4.add("{" + temp + "} ==> {" + as.toString() + "}");
		}

		return list4;
	}

	// A method that exports the data to a file
	public void export() throws FileNotFoundException
	{
		ArrayList<String> list5 = new ArrayList<>(list2.size());
		int j = 0;

		for(int i = 0; i < list1.size(); i++)
		{
			if(isValid(list1.get(i)))
				list5.add("{" + list1.get(i) + "} ==> {" + list3.get(j++));
		}

		File file = new File("Output.txt");
		PrintWriter outData = new PrintWriter(file);

		outData.println("{Infix Expression} ==> {Postfix Expression} ==> {Postfix Evaluation}");
		outData.println();

		for(String exp: list5)
			outData.println(exp);

		outData.close();

		stat.setText("Status: Data has been exported to a file successfully.");
	}
}
