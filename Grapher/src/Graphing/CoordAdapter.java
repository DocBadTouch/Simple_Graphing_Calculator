package Graphing;

public class CoordAdapter implements FunctionGenerator{
	
	@Override
	public int genFunction(int x) {
		// TODO Auto-generated method stub
		int Y = ((x/3) < 100) ? (x/3)*-1:(x/3)-100;
		
		
		return Y;
	}
	public int genBackX(int x,int W) {
		int X;
		if(x==0) {
			X = x+(W/2);
		}
		else if(x<0) {
			X = x*(W/200)*-1;
		}else X = x*(W/200)+W/2;
		return X;
	}
	public int genBackY(int y, int H) {
		int Y;
		if(y==0) {
			Y = y+H/2;
		}
		else if(y<0) {
			Y = y*(H/200)*-1 + H/2;
		}else Y = y*(H/200);
		return Y;
	}

	
}
