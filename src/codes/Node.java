package codes;

public class Node {
	
	private boolean[] poss = new boolean[10]; // stores which values are possible for this square, index 0 is useless
	private int data; // number in the square, if empty it is 0
	private int box; // which box is it in from 1 to 9
	private Node up, down, left, right;
	
	public Node() {
		poss[0] = false;
		for(int i=1; i < 10; i++)
			poss[i] = true;
	}
	
	public void setPoss(boolean bool, int index) {
		poss[index] = bool;
	}
	
	public boolean getPoss(int index) {
		return poss[index];
	}
	
	public void setData(int data) {
		this.data = data;
	}
	
	public int getData() {
		return data;
	}
	
	public void setBox(int box) {
		this.box = box;
	}
	
	public int getBox() {
		return box;
	}
	
	public void setUp(Node up) {
		this.up = up;
	}
	
	public Node getUp() {
		return up;
	}
	
	public void setDown(Node down) {
		this.down = down;
	}
	
	public Node getDown() {
		return down;
	}
	
	public void setLeft(Node left) {
		this.left = left;
	}
	
	public Node getLeft() {
		return left;
	}
	
	public void setRight(Node right) {
		this.right = right;
	}
	
	public Node getRight() {
		return right;
	}

}

