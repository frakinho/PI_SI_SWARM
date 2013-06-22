package Entidades;

public class movimento {
	private int id;
	private int mov_x;
	private int move_y;
	
	public movimento(int id, int mov_x, int move_y) {
		this.id = id;
		this.mov_x = mov_x;
		this.move_y = move_y;
	}
	
	public movimento(int mov_x, int move_y) {
		this.mov_x = mov_x;
		this.move_y = move_y;
	}

	//GET AND SET
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getMov_x() {
		return mov_x;
	}


	public void setMov_x(int mov_x) {
		this.mov_x = mov_x;
	}


	public int getMove_y() {
		return move_y;
	}


	public void setMove_y(int move_y) {
		this.move_y = move_y;
	}

}
