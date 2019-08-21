package java.solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Gordon Pham-Nguyen
 * @ID 40018402
 *
 *     Assignment 2 programming question
 *
 *     COMP 352-S Winter 2019
 *
 *     Calculates a file of arithmetics using stacks
 *
 */
public class ArithmeticsSolver {

	public static void main(String[] args) {
		PrintWriter pw = null;
		System.out.println("Running...");

		// open a output file
		try {
			pw = new PrintWriter(new FileOutputStream("out.txt"));
		}

		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		// open the file with expressions
		File input = new File("assignment_2_expressions.txt");
		Scanner scanner = null;

		try {
			scanner = new Scanner(input);
			// read each line in the expression and send to evaluate
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				pw.println(evalArithmetic(line));
			}

			pw.flush();
		}

		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			scanner.close();
			pw.close();
		}

		System.out.println("Done.");

	}

	/**
	 * @param line current line of the expressions file
	 * @return result of the line
	 */
	public static Object evalArithmetic(String line) {
		// stack for numbers
		MyStack<Object> operands = new MyStack<>();
		// stack for operators
		MyStack<String> operators = new MyStack<>();
		operators.push("$");
		// removes whitespaces
		line = line.replace(" ", "");
		// read each character in the line
		for (int i = 0; i < line.length(); i++) {
			String character = line.substring(i, i + 1);

			// if the character is a minus sign
			if (character.equals("-")) {
				// if this is the first character of the line
				if (i == 0) {
					character = "u-";
					// if it's not the first character of the line
				} else {
					// check what is in front of it
					String previousChar = line.substring(i - 1, i);

					// if not a number, nor !, nor close parenthesis
					if ((precedence(previousChar) != -1) && (precedence(previousChar) != 2)
							&& !previousChar.equals(")")) {
						character = "u-";
					}
				}
				// if comparison or !
			} else if ((character.equals(">")) || character.equals("<") || character.equals("=")
					|| character.equals("!")) {
				StringBuilder compOperator = new StringBuilder();
				compOperator.append(character);
				String nextChar = "";
				// use the character that comes after
				if (i < (line.length() - 2)) {
					nextChar = line.substring(i + 1, i + 2);
				}
				// if anything that cannot process now then move on to the next character
				if (nextChar.equals("=") && (!character.equals("!") || (line.indexOf("==") == -1))) {
					compOperator.append(nextChar);
					character = compOperator.toString();
					i++;
				}
			}
			// if the character is a number
			if (precedence(character) == -1) {
				StringBuilder number = new StringBuilder();
				number.append(character);
				String nextChar = "";
				// add to current number if the next char is also a number
				while ((i < (line.length() - 1)) && (precedence(nextChar = line.substring(i + 1, i + 2)) == -1)) {
					number.append(nextChar);
					i++;
				}
				// push integer into operand stack
				try {
					operands.push(Integer.valueOf(number.toString()));
				}

				catch (NumberFormatException e) {
					System.out.println(e.getMessage());
				}
				// if precedence of the current operator is less than the precedence of the
				// operator at the top of the stack or a minus sign, or opening parenthesis and
				// not a closing parenthesis
				// push into operators stack
			} else if ((((precedence(character) < precedence(operators.top())) || operators.top().equals("("))
					&& !character.equals(")")) || (character.equals("u-") && operators.top().equals("u-"))) {
				operators.push(character);
				// if closing parenthesis
			} else if (character.equals(")")) {
				// while top of the stack is not an opening parenthesis
				while (!operators.top().equals("(")) {
					operands.push(calculate(operands, operators));
				}

				operators.pop();
			} else {
				while ((precedence(character) >= precedence(operators.top())) && !operators.top().equals("(")) {
					operands.push(calculate(operands, operators));
				}

				operators.push(character);
			}
		}
		// calculate as long as the operators stack has more than two elements
		while (operators.size() > 1) {
			operands.push(calculate(operands, operators));
		}

		return operands.top();
	}

	/**
	 * @param op operator
	 * @return a number based on the operator's precedence
	 */
	public static int precedence(String op) {
		switch (op) {
		case "(":
			return 1;
		case ")":
			return 1;
		case "!":
			return 2;
		case "u-":
			return 3;
		case "^":
			return 4;
		case "*":
			return 5;
		case "/":
			return 5;
		case "+":
			return 6;
		case "-":
			return 6;
		case ">":
			return 7;
		case ">=":
			return 7;
		case "<":
			return 7;
		case "<=":
			return 7;
		case "==":
			return 8;
		case "!=":
			return 8;
		case "$":
			return 99;
		default:
			return -1; // Marks a number and not an operator.
		}
	}

	/**
	 * @param x  first operand
	 * @param y  second operand
	 * @param op operator
	 * @return the result of the operation
	 */
	public static Object applyOperator(int x, int y, String op) {
		switch (op) {
		case "!":
			return factorial(x);
		case "^":
			return exponent(x, y);
		case "*":
			return x * y;
		case "/":
			return x / y;
		case "+":
			return x + y;
		case "-":
			return x - y;
		case "u-":
			return -x;
		case ">":
			return x > y;
		case ">=":
			return x >= y;
		case "<":
			return x < y;
		case "<=":
			return x <= y;
		case "==":
			return x == y;
		case "!=":
			return x != y;
		default:
			return false;
		}
	}

	/**
	 * @param operands  stack
	 * @param operators stack
	 * @return the result of the operation
	 */
	public static Object calculate(MyStack<Object> operands, MyStack<String> operators) {
		String op = operators.pop();
		int x = (int) operands.pop();
		int y = 0;
		// if not a minus sign or factorial
		if ((precedence(op) != 2) && (precedence(op) != 3)) {
			// then two operands to use
			y = (int) operands.pop();
			return applyOperator(y, x, op);
		} else {
			return applyOperator(x, y, op);
		}
	}

	/**
	 * @param x first operand
	 * @param y second operand
	 * @return the result of the exponent operation
	 */
	public static int exponent(int x, int y) {
		if (y == 0) {
			return 1;
		}
		// recursive
		return x * exponent(x, y - 1);
	}

	/**
	 * @param x operand to perform factorial
	 * @return the result of the factorial operation
	 */
	public static int factorial(int x) {
		if (x == 0) {
			return 1;
		}
		// recursive
		return x * factorial(x - 1);
	}

}