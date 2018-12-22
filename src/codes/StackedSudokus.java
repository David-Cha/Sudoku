package codes;

public class StackedSudokus {
	
	private SudokuGrid first, last;
	
	public SudokuGrid getLast() {
		return last;
	}

	private int size;
	
	public StackedSudokus() {
		first = null;
		last = null;
		size = 0;
	}
	
	public void push(SudokuGrid Grid) {
		if(first == null) {
			first = Grid;
			last = Grid;
		}
		else {
			last.setNext(Grid);
			Grid.setPrev(last);
			last = Grid;
		}
		size++;
	}
	
	public void pop() { // pops in stack fashion
		if(size == 1) {
			first = null;
			last = null;
			size--;
		}
		else if(size > 1) {
			last = last.getPrev();
			last.setNext(null);
			size--;
		}
		else
			System.out.println("Nothing to pop.");
	}

}
