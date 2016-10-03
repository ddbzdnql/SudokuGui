package sample;
import java.util.ArrayList;

public class Storage {
		public int x;
		public int y;
		public ArrayList<Integer> record;
		
		public Storage(int i, int j){
			this.x = j;
			this.y = i;
			record = new ArrayList<Integer>();
		}
}
