package codes;

public class SudokuGrid {
	
	private Node leftMarker, marker, forDisplay, // forDisplay used for display method, also where we will start
	originalTemp;
	private SudokuGrid next, prev; // for the stack for the guess method
	
	public Node getForDisplay() { // need it for starting things, gets the top left node
		return forDisplay;
	}
	
	public void setNext(SudokuGrid next) {
		this.next = next;
	}
	public SudokuGrid getNext() {
		return next;
	}
	
	public void setPrev(SudokuGrid prev) {
		this.prev = prev;
	}
	public SudokuGrid getPrev() {
		return prev;
	}
	
	public Node getOriginalTemp() {
		return originalTemp;
	}
	public void setOriginalTemp(Node originalTemp) {
		this.originalTemp = originalTemp;
	}
	
	public void createGrid(int size) { // size will be 9
		Node Node = new Node(); // top left corner Node
		forDisplay = leftMarker = Node; // left marker is for helping setting the ups and downs
		
		// creating rest of the top row
		for(int i=0; i < size-1; i++) {
			Node.setRight(new Node());
			Node.getRight().setLeft(Node);
			Node = Node.getRight();
		}
		
		for(int h=0; h < size-1; h++) {
			// creating next row while connecting the ups and downs
			leftMarker.setDown(new Node());
			leftMarker.getDown().setUp(leftMarker);
			marker = leftMarker.getDown();
			
			for(int i=0; i < size-1; i++) {
				marker.setRight(new Node());
				marker.getRight().setLeft(marker);
				marker = marker.getRight(); // move on to the right Node
				
				leftMarker = leftMarker.getRight();
				marker.setUp(leftMarker);
				leftMarker.setDown(marker);
			}
			// position left marker
			for(int i=0; i < size-1; i++) {
				marker = marker.getLeft();
			}
			leftMarker = marker;
		}
	}
	
	public void displayGrid() { 
		Node temp = forDisplay;
		Node leftForDisplay = temp;
		
		while(leftForDisplay != null) {
			temp = leftForDisplay;
			while(temp != null) {
				System.out.print(temp.getData() + "   ");
				temp = temp.getRight();
			}
			System.out.println();
			leftForDisplay = leftForDisplay.getDown();
		}
		System.out.println(); // extra empty line after method is done
	}

}