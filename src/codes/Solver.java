package codes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Solver {

	public static SudokuGrid Grid = new SudokuGrid();
	static File infile = new File("test.txt");

	// gives a value to a blank square, then removes the possibilities
	// vertically and horizontally
	public static void fillSquare(Node Node, int value) {
		if (value != 0 && Node.getPoss(value) == true) {
			Node.setData(value);
			// set all possibilities to false now so that it doesn't interfere
			// with other methods
			for (int i = 1; i < 10; i++) {
				Node.setPoss(false, i);
			}

			// Now handle all the affected sections (vertical, horizontal, box)
			// vertical
			Node temp = Node;
			while (temp.getUp() != null) {
				temp = temp.getUp();
				temp.setPoss(false, value); // since value is the index
			}
			temp = Node;
			while (temp.getDown() != null) {
				temp = temp.getDown();
				temp.setPoss(false, value); // since value is the index
			}
			// horizontal
			temp = Node;
			while (temp.getRight() != null) {
				temp = temp.getRight();
				temp.setPoss(false, value); // since value is the index
			}
			temp = Node;
			while (temp.getLeft() != null) {
				temp = temp.getLeft();
				temp.setPoss(false, value); // since value is the index
			}
			// box
			temp = getBox(Node.getBox());
			for (int i = 0; i < 3; i++) {
				temp.setPoss(false, value);
				if (temp.getRight() != null)
					temp = temp.getRight();
			}
			temp = getBox(Node.getBox()).getDown();
			for (int i = 0; i < 3; i++) {
				temp.setPoss(false, value);
				if (temp.getRight() != null)
					temp = temp.getRight();
			}
			temp = getBox(Node.getBox()).getDown().getDown();
			for (int i = 0; i < 3; i++) {
				temp.setPoss(false, value);
				if (temp.getRight() != null)
					temp = temp.getRight();
			}
		} else if (value == 0)
			Node.setData(value);
	}

	// takes a node and checks its column if any squares have only one
	// possibility (check the node itself first)
	// it then sets that spot to that one possibility
	public static void verticalOnePoss(Node Node) {
		int poss, value = 0; // counts how many numbers are possible
		Node temp = Node;

		// checking node itself first
		poss = 0;
		for (int i = 1; i < 10; i++)
			if (temp.getPoss(i) == true)
				poss++;

		if (poss == 1) { // then we must fill in the square
			for (int i = 1; i < 10; i++) // need to find which number is the one possibility
				if (temp.getPoss(i) == true)
					value = i;
			fillSquare(temp, value);
		}

		// check above
		while (temp.getUp() != null) {
			temp = temp.getUp();

			poss = 0;
			for (int i = 1; i < 10; i++)
				if (temp.getPoss(i) == true)
					poss++;

			if (poss == 1) { // then we must fill in the square
				for (int i = 1; i < 10; i++) // need to find which number is the one possibility
					if (temp.getPoss(i) == true)
						value = i;
				fillSquare(temp, value);
				temp = Node; // since we need to go through this column again
			}
		}

		// check below
		temp = Node;
		while (temp.getDown() != null) {
			temp = temp.getDown();

			poss = 0;
			for (int i = 1; i < 10; i++)
				if (temp.getPoss(i) == true)
					poss++;

			if (poss == 1) { // then we must fill in the square
				for (int i = 1; i < 10; i++) // need to find which number is the one possibility
					if (temp.getPoss(i) == true)
						value = i;
				fillSquare(temp, value);
				temp = Node;
			}
		}
	}

	// horizontal counterpart to the verticalOnePoss method
	public static void horizontalOnePoss(Node Node) {
		int poss, value = 0; // counts how many numbers are possible
		Node temp = Node;

		// checking node itself first
		poss = 0;
		for (int i = 1; i < 10; i++)
			if (temp.getPoss(i) == true)
				poss++;

		if (poss == 1) { // then we must fill in the square
			for (int i = 1; i < 10; i++) // need to find which number is the one possibility
				if (temp.getPoss(i) == true)
					value = i;
			fillSquare(temp, value);
		}

		// check right
		while (temp.getRight() != null) {
			temp = temp.getRight();

			poss = 0;
			for (int i = 1; i < 10; i++)
				if (temp.getPoss(i) == true)
					poss++;

			if (poss == 1) { // then we must fill in the square
				for (int i = 1; i < 10; i++) // need to find which number is the one possibility
					if (temp.getPoss(i) == true)
						value = i;
				fillSquare(temp, value);
				temp = Node; // since we need to go through this column again
			}
		}

		// check left
		temp = Node;
		while (temp.getLeft() != null) {
			temp = temp.getLeft();

			poss = 0;
			for (int i = 1; i < 10; i++)
				if (temp.getPoss(i) == true)
					poss++;

			if (poss == 1) { // then we must fill in the square
				for (int i = 1; i < 10; i++) // need to find which number is the one possibility
					if (temp.getPoss(i) == true)
						value = i;
				fillSquare(temp, value);
				temp = Node;
			}
		}
	}

	// check if there is a number (1 to 9) in the column where only one square
	// could satisfy it
	public static void columnScan(Node Node) {
		int squares; // number of squares that could be that number
		Node temp = Node;

		for (int i = 1; i < 10; i++) { // i is the value we are checking
			squares = 0;
			// check the node itself
			if (temp.getPoss(i) == true)
				squares++;
			// check above
			while (temp.getUp() != null) {
				temp = temp.getUp();
				if (temp.getPoss(i) == true)
					squares++;
			}
			// check below
			temp = Node;
			while (temp.getDown() != null) {
				temp = temp.getDown();
				if (temp.getPoss(i) == true)
					squares++;
			}

			// there is only one choice if squares == 1, now we need to locate
			// this square
			if (squares == 1) {
				while (temp.getUp() != null && temp.getPoss(i) != true) // since temp is now at the bottom of the column
					temp = temp.getUp();
				fillSquare(temp, i);
			}
		}
	}

	// horizontal counterpart to the above method
	// check if there is a number (1 to 9) in the row where only one square
	// could satisfy it
	public static void rowScan(Node Node) {
		int squares; // number of squares that could be that number
		Node temp;

		for (int i = 1; i < 10; i++) { // i is the value we are checking
			temp = Node;
			squares = 0;

			// check the node itself
			if (temp.getPoss(i) == true)
				squares++;
			// check right
			while (temp.getRight() != null) {
				temp = temp.getRight();
				if (temp.getPoss(i) == true)
					squares++;
			}
			// check left
			temp = Node;
			while (temp.getLeft() != null) {
				temp = temp.getLeft();
				if (temp.getPoss(i) == true)
					squares++;
			}

			// there is only one choice if squares == 1, now we need to locate
			// this square
			if (squares == 1) {
				while (temp.getRight() != null && temp.getPoss(i) != true) // since temp is now at the left of the row
					temp = temp.getRight();
				fillSquare(temp, i);
			}
		}
	}

	// box counterpart to the above two methods
	// check if there is a number (1 to 9) in the box where only one square
	// could satisfy it, uses the box locating method below
	// to check every box you would need to call this nine times (for
	// flexibility it only checks one box)
	public static void boxScan(int box) {
		Node cornerNodeBox = getBox(box);
		Node temp, chosenOne = null; // save the first instance as it will be the chosenOne if squares == 1
		int squares;

		for (int i = 1; i < 10; i++) {
			squares = 0;

			// first row
			temp = cornerNodeBox;
			for (int j = 0; j < 3; j++) {
				if (temp.getPoss(i) == true) {
					squares++;
					if (squares == 1)
						chosenOne = temp;
				}
				if (temp.getRight() != null)
					temp = temp.getRight();
			}
			// next row
			temp = cornerNodeBox.getDown();
			for (int j = 0; j < 3; j++) {
				if (temp.getPoss(i) == true) {
					squares++;
					if (squares == 1)
						chosenOne = temp;
				}
				if (temp.getRight() != null)
					temp = temp.getRight();
			}
			// final row
			temp = cornerNodeBox.getDown().getDown();
			for (int j = 0; j < 3; j++) {
				if (temp.getPoss(i) == true) {
					squares++;
					if (squares == 1)
						chosenOne = temp;
				}
				if (temp.getRight() != null)
					temp = temp.getRight();
			}

			if (squares == 1) { // then this square is the chosenOne as located beforehand
				fillSquare(chosenOne, i);
			}
		}
	}

	// gets the number of the desired box, then returns the node of the top left
	// corner of the box
	// locates it through logical grid system based on box number
	public static Node getBox(int box) {
		Node cornerNode = Grid.getForDisplay();

		// horizontal locating
		if (box % 3 == 2) {
			for (int i = 0; i < 3; i++)
				cornerNode = cornerNode.getRight();
		} else if (box % 3 == 0) {
			for (int i = 0; i < 6; i++)
				cornerNode = cornerNode.getRight();
		}
		// vertical locating
		if (box >= 4 && box <= 6) {
			for (int i = 0; i < 3; i++)
				cornerNode = cornerNode.getDown();
		} else if (box >= 7 && box <= 9) {
			for (int i = 0; i < 6; i++)
				cornerNode = cornerNode.getDown();
		}

		return cornerNode;
	}

	// method checks if 2 squares share only 2 possibilities and have 2 possibilities,
	// then eliminates the possibilities for all other squares in box
	// we check each square (set it as the candidate) and go through all the
	// others, except the ones in the back
	public static void shareBox(int box) {
		Node temp, eliminate, leftMarker, candidate, cornerNode = getBox(box);
		int candPoss, tempPoss; // number of possibilities
		boolean samePoss, foundPair = false; // search stops once we find a loop

		leftMarker = temp = candidate = cornerNode;

		while ((candidate.getRight() != null && candidate.getBox() == candidate.getRight().getBox())
				|| (leftMarker.getDown() != null && leftMarker.getBox() == leftMarker.getDown().getBox())) {
			if (candidate.getRight() == null || candidate.getBox() != candidate.getRight().getBox()) {
				candidate = leftMarker = leftMarker.getDown();

				if (cornerNode.getBox() != leftMarker.getBox()) // then we found nothing after reaching the end, time to abandon loop
					break;
			} else
				candidate = candidate.getRight();

			temp = candidate; // ensures they will never be the same after the following code

			while (((temp.getRight() != null && temp.getBox() == temp.getRight().getBox())
					|| (leftMarker.getDown() != null && leftMarker.getBox() == leftMarker.getDown().getBox()))
					&& !foundPair) {
				if ((temp.getRight() == null || temp.getBox() != temp.getRight().getBox())) {
					if (leftMarker.getDown() == null)
						break;

					temp = leftMarker = leftMarker.getDown();

					if (cornerNode.getBox() != leftMarker.getBox()) // then we found nothing after reaching the end, time to abandon loop
						break;
				} else
					temp = temp.getRight();

				// reset conditions
				candPoss = tempPoss = 0;
				samePoss = true;

				for (int i = 1; i < 10; i++) {
					if (candidate.getPoss(i) == true)
						candPoss++;
					if (temp.getPoss(i) == true)
						tempPoss++;
					if (candidate.getPoss(i) != temp.getPoss(i)) // if this happens then this square no longer needs to be considered
						samePoss = false;
				}

				if (candPoss == 2 && samePoss) { // now identify and eliminate these 2 numbers from the other squares
					foundPair = true;

					// first row
					eliminate = cornerNode;
					for (int j = 0; j < 3; j++) {
						if (eliminate != candidate && eliminate != temp) { // making sure it is another square
							for (int i = 1; i < 10; i++)
								if (eliminate.getPoss(i) == candidate.getPoss(i) == true)
									eliminate.setPoss(false, i);
						}
						if (eliminate.getRight() != null)
							eliminate = eliminate.getRight();
					}
					// next row
					eliminate = cornerNode.getDown();
					for (int j = 0; j < 3; j++) {
						if (eliminate != candidate && eliminate != temp) { // making sure it is another square
							for (int i = 1; i < 10; i++)
								if (eliminate.getPoss(i) == candidate.getPoss(i) == true)
									eliminate.setPoss(false, i);
						}
						if (eliminate.getRight() != null)
							eliminate = eliminate.getRight();
					}
					// final row
					eliminate = cornerNode.getDown().getDown();
					for (int j = 0; j < 3; j++) {
						if (eliminate != candidate && eliminate != temp) { // making sure it is another square
							for (int i = 1; i < 10; i++)
								if (eliminate.getPoss(i) == candidate.getPoss(i) == true)
									eliminate.setPoss(false, i);
						}
						if (eliminate.getRight() != null)
							eliminate = eliminate.getRight();
					}
				}

			}
		}

	}

	public static StackedSudokus Stack = new StackedSudokus(); // create stack outside of method
	public static void guess() {
		
		SudokuGrid prevGrid;
		Node originalTemp, temp, leftMarker, cornerNode;
		temp = leftMarker = cornerNode = Grid.getForDisplay();
		boolean incorrect = false, methodIncorrect = false;
		int p, x;

		temp = leftMarker = cornerNode;
		// need to find the first square that is empty
		while (temp.getData() != 0) {
			if (temp.getRight() != null)
				temp = temp.getRight();
			else if (leftMarker.getDown() != null) {
				temp = leftMarker = leftMarker.getDown();
			}
		} // temp is now at node with data equal to 0

		// must save the node we guessed on
		originalTemp = temp;
		Grid.setOriginalTemp(originalTemp);
		
		p = 1;

		do {
			methodIncorrect = false; // reset
			Stack.push(copyGrid());

			do {
				incorrect = false;
				
				while (p < 10 && originalTemp.getPoss(p) != true) {
					p++;
				} // p is now on a possibility that is true

				if (p == 10) { // need to go back a grid
					incorrect = true;
					Stack.pop();
					copyGrid(Stack.getLast());

					x = 1;
					while (x < 10 && originalTemp.getPoss(x) != true) {
						x++;
					}
					originalTemp.setPoss(false, x);
					p = x;
				}
			} while(incorrect);

			fillSquare(originalTemp, p); // guessed, try to solve as much as possible now
			
			do { // try to solve twice
				prevGrid = copyGrid();

				temp = cornerNode;
				verticalOnePoss(temp);
				columnScan(temp);
				while (temp.getRight() != null) {
					temp = temp.getRight();
					verticalOnePoss(temp);
					columnScan(temp);
				}

				temp = cornerNode;
				horizontalOnePoss(temp);
				rowScan(temp);
				while (temp.getDown() != null) {
					temp = temp.getDown();
					horizontalOnePoss(temp);
					rowScan(temp);
				}

				for (int box = 1; box < 10; box++) {
					shareBox(box);
					boxScan(box);
				}
				// AGAIN
				temp = cornerNode;
				verticalOnePoss(temp);
				columnScan(temp);
				while (temp.getRight() != null) {
					temp = temp.getRight();
					verticalOnePoss(temp);
					columnScan(temp);
				}

				temp = cornerNode;
				horizontalOnePoss(temp);
				rowScan(temp);
				while (temp.getDown() != null) {
					temp = temp.getDown();
					horizontalOnePoss(temp);
					rowScan(temp);
				}

				for (int box = 1; box < 10; box++) {
					shareBox(box);
					boxScan(box);
				}
				
			} while (!checkSolved() && !sameData(prevGrid) && !incorrect());

			methodIncorrect = incorrect();
			if (methodIncorrect) {
				copyGrid(Stack.getLast());
				Stack.pop();
			
				originalTemp.setPoss(false, p);
			} else if(!checkSolved()){ // must guess again then
				guess();
			}
		} while (methodIncorrect);

	}
	
	// means grid has incorrect numbers, checks by seeing if an empty square has no possibilities
	public static boolean incorrect() { 
		Node temp, leftMarker;
		int allNine;
		boolean incorrect = false;
		
		temp = leftMarker = Grid.getForDisplay();

		allNine = 0;
		for (int i = 1; i < 10; i++) {
			if (temp.getPoss(i) == false & temp.getData() == 0)
				allNine++;
		}
		if (allNine == 9)
			incorrect = true;

		while (temp.getRight() != null || leftMarker.getDown() != null) {
			if (temp.getRight() != null) {
				temp = temp.getRight();

				allNine = 0;
				for (int i = 1; i < 10; i++) {
					if (temp.getPoss(i) == false & temp.getData() == 0)
						allNine++;
				}
				if (allNine == 9)
					incorrect = true;
			} else if (leftMarker.getDown() != null) {
				temp = leftMarker = leftMarker.getDown(); // shift down

				allNine = 0;
				for (int i = 1; i < 10; i++) {
					if (temp.getPoss(i) == false & temp.getData() == 0)
						allNine++;
				}
				if (allNine == 9)
					incorrect = true;
			}
		}
		
		return incorrect;
	}

	public static SudokuGrid copyGrid() { // creates a non static copy of the grid for the guess method to work
		SudokuGrid Copy = new SudokuGrid();
		Copy.createGrid(9);

		int rights = 0, downs = 0;
		Node leftMarker, temp, copyLeftMarker, copyTemp;
		leftMarker = temp = Grid.getForDisplay();
		copyLeftMarker = copyTemp = Copy.getForDisplay();

		copyTemp.setData(temp.getData()); // give initial node same data
		for (int i = 1; i < 10; i++) { // give same possibilities
			copyTemp.setPoss(temp.getPoss(i), i);
		}
		while (temp.getRight() != null || leftMarker.getDown() != null) {
			if (temp.getRight() != null) {
				temp = temp.getRight();
				copyTemp = copyTemp.getRight();
			} else if (leftMarker.getDown() != null) {
				temp = leftMarker = leftMarker.getDown();
				copyTemp = copyLeftMarker = copyLeftMarker.getDown();
			}
			copyTemp.setData(temp.getData()); // give same data
			for (int i = 1; i < 10; i++) { // give same possibilities
				copyTemp.setPoss(temp.getPoss(i), i);
			}
		}

		// mark same originalTemp, locate first
		if (Grid.getOriginalTemp() != null) {
			temp = Grid.getOriginalTemp();
			while (temp.getLeft() != null) {
				temp = temp.getLeft();
				rights++;
			}
			while (temp.getUp() != null) {
				temp = temp.getUp();
				downs++;
			}
			// now locate on copy and set it as originalTemp
			copyTemp = Copy.getForDisplay();
			for (int i = 0; i < rights; i++) {
				copyTemp = copyTemp.getRight();
			}
			for (int i = 0; i < downs; i++) {
				copyTemp = copyTemp.getDown();
			}
			Copy.setOriginalTemp(copyTemp); // set it
		}

		return Copy;
	}

	public static void copyGrid(SudokuGrid Copy) { // copies a grid onto the static Grid
		
		int rights = 0, downs = 0;
		Node leftMarker, temp, copyLeftMarker, copyTemp;
		leftMarker = temp = Grid.getForDisplay();
		copyLeftMarker = copyTemp = Copy.getForDisplay();

		temp.setData(copyTemp.getData()); // give initial node same data
		for (int i = 1; i < 10; i++) { // give same possibilities
			temp.setPoss(copyTemp.getPoss(i), i);
		}
		while (temp.getRight() != null || leftMarker.getDown() != null) {
			if (temp.getRight() != null) {
				temp = temp.getRight();
				copyTemp = copyTemp.getRight();
			} else if (leftMarker.getDown() != null) {
				leftMarker = leftMarker.getDown();
				copyLeftMarker = copyLeftMarker.getDown();
				temp = leftMarker;
				copyTemp = copyLeftMarker;
			}
			temp.setData(copyTemp.getData()); // give same data
			for (int i = 1; i < 10; i++) { // give same possibilities
				temp.setPoss(copyTemp.getPoss(i), i);
			}
		}
		
		// give same original temp
		copyTemp = Copy.getOriginalTemp();
		while (copyTemp.getLeft() != null) {
			copyTemp = copyTemp.getLeft();
			rights++;
		}
		while (copyTemp.getUp() != null) {
			copyTemp = copyTemp.getUp();
			downs++;
		}
		// now locate on Grid and set it as originalTemp
		temp = Grid.getForDisplay();
		for (int i = 0; i < rights; i++) {
			temp = temp.getRight();
		}
		for (int i = 0; i < downs; i++) {
			temp = temp.getDown();
		}
		Copy.setOriginalTemp(temp);

	}

	public static boolean sameData(SudokuGrid Copy) { // checks if this grid has the exact same data squares and ignore if possibilities are same
		boolean same = true;

		Node leftMarker, temp, copyLeftMarker, copyTemp;
		leftMarker = temp = Grid.getForDisplay();
		copyLeftMarker = copyTemp = Copy.getForDisplay();

		if (copyTemp.getData() != temp.getData()) // then not same
			same = false;
		while (temp.getRight() != null || leftMarker.getDown() != null) {
			if (temp.getRight() != null) {
				temp = temp.getRight();
				copyTemp = copyTemp.getRight();
			} else if (leftMarker.getDown() != null) {
				temp = leftMarker = leftMarker.getDown();
				copyTemp = copyLeftMarker = copyLeftMarker.getDown();
			}
			if (copyTemp.getData() != temp.getData()) // then not same
				same = false;
		}

		return same;
	}

	// check if puzzle has been solved
	public static boolean checkSolved() {
		Node temp, leftMarker;
		boolean solved = true;

		temp = leftMarker = Grid.getForDisplay();

		for (int x = 0; x < 81; x++) {
			if (temp.getData() == 0) // if a single blank square exists then not yet solved
				solved = false;
			if (temp.getRight() != null)
				temp = temp.getRight();
			else { // go down a row if we reach the end
				leftMarker = leftMarker.getDown();
				temp = leftMarker;
			}
		}

		return solved;
	}

	// reads the puzzle from a text file
	public static void readPuzzle() throws IOException {
		Node temp, leftMarker;
		int data;

		temp = leftMarker = Grid.getForDisplay();

		for (int x = 0; x < 81; x++) {
			// set the box numbers using calculations on the iteration number 
			// must be done before entering the data
			temp.setBox(((x % 9) / 3 + 1) + (x / 27) * 3);
			if (temp.getRight() != null)
				temp = temp.getRight();
			else { // go down a row if we reach the end
				leftMarker = leftMarker.getDown();
				temp = leftMarker;
			}

		}

		Scanner reader = new Scanner(infile);
		temp = leftMarker = Grid.getForDisplay();
		for (int x = 0; x < 81; x++) {
			data = reader.nextInt();
			fillSquare(temp, data);

			if (temp.getRight() != null)
				temp = temp.getRight();
			else { // go down a row if we reach the end
				leftMarker = leftMarker.getDown();
				temp = leftMarker;
			}
		}
	}

	public static void main(String[] args) throws IOException {

		Grid.createGrid(9);
		readPuzzle();
		Grid.displayGrid();

		SudokuGrid prev;
		Node temp, cornerNode = Grid.getForDisplay();
		do {
			prev = copyGrid();

			temp = cornerNode;
			verticalOnePoss(temp);
			columnScan(temp);
			while (temp.getRight() != null) {
				temp = temp.getRight();
				verticalOnePoss(temp);
				columnScan(temp);
			}

			temp = cornerNode;
			horizontalOnePoss(temp);
			rowScan(temp);
			while (temp.getDown() != null) {
				temp = temp.getDown();
				horizontalOnePoss(temp);
				rowScan(temp);
			}

			for (int box = 1; box < 10; box++) {
				shareBox(box);
				boxScan(box);
			}

			if (sameData(prev)) {
				System.out.println("GUESSING");
				guess();
			}

			System.out.println();
			Grid.displayGrid();
		} while (!checkSolved());

		System.out.println();
		System.out.println("Finished");
		Grid.displayGrid();

	}

}
