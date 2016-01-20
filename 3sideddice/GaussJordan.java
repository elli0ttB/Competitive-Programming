import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

public class GaussJordan {
    Kattio io = new Kattio(System.in);
    private double[][] mat;
    private int rows;
    private int cols;
    
    public static void main(String[] args){
        new GaussJordan(3, 4).solveForInput();
    }

    public GaussJordan(int rows, int cols){
        this.mat = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public void solveForInput(){
        try {
            while (true) {

                // get given dice to use
                for (int i= 0; i< rows; i++)
                    for (int j= 0; j< cols - 1; j++) {
                        mat[i][j] = io.getInt();
                    }

                // get desired dice probabilities
                for (int i= 0; i< rows; i++) {
                    mat[i][cols - 1] = io.getInt();
                }

                io.println( isSolvable() ? "YES" : "NO" );
            }
            // lazy way to solve checking for eof - wait for catastrophe
        } catch (NumberFormatException e){
            io.flush();
            io.close();
        }
    }

    public boolean isSolvable(){
        convertToRowEchelon();
        return backSubstitution();
    }

    private void convertToRowEchelon (){
        for (int i=0; i< rows ; i++){
            // STEP 1 - maximize; look for largest element
            int maxRow = i;
            for (int row = i+1; row < rows; row++) {
                if ( mat[row][i] > mat[maxRow][i] )
                    maxRow = row;
            }
           
            swapRow(i, maxRow, i);
            // if entire column is zero, move along
            if ( zero(mat[i][i]) )
                continue;
            
            // STEP 2: make mat[i][i] = 1 by dividing out row
            double divisor = mat[i][i];
            for (int col = i; col < cols; col++)
                mat[i][col] /= divisor;

            // STEP 3: zero out everything below i in that column for each row
            for (int j=i+1; j< rows; j++){
                addRow(i, j, -mat[j][i] / mat[i][i], i);
            }
        // STEP 4: increment i
        }
    } 

    private boolean backSubstitution() {
        // back substitution: this part of the algorithm will assume one more
        // row than column, so unlike the first part, it will not work
        // for any matrix.
        for (int i= rows -1 ; i >= 0 ; i--){

            // could be zero if whole column is zero, otherwise is 1
            if ( ! zero(mat[i][i]) ) {
                for (int row = i -1; row >= 0 ; row-- ){
                    addRow(i, row, -mat[row][i], i);
                }
            // entire row is zero, so augmented has to be 0 
            } else if ( ! zero(mat[i][cols -1]) )
                    return false;

        // check that entire last column is non-negative
            if (mat[i][cols-1] / mat[i][i] < 0)
                return false;
        }

        return true;
    }
    
    private void addRow(int rowAdded, int rowAddedTo, double factor, int start){
        for (int i = start; i < cols; i++)
            mat[rowAddedTo][i] += mat[rowAdded][i] * factor;
    }

    private void swapRow(int row1, int row2, int start){
        for (int i = start; i < cols; i++){
            double tmp = mat[row1][i];
            mat[row1][i] = mat[row2][i];
            mat[row2][i] = tmp;
        }
    }

    // need to figure out if this should be smaller
    private boolean zero(double d){
        return Math.abs(d) < 10e-9;
    }

    private boolean negative(double d){
        return d < -10e-9;
    }

    public void print(){
        for (double[] row : mat) {
            for (double i : row)
                System.out.printf("%.1f ", i);
            System.out.println();
        }
        System.out.println();
    }

}

class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
	super(new BufferedOutputStream(System.out));
	r = new BufferedReader(new InputStreamReader(i));
    }
    public Kattio(InputStream i, OutputStream o) {
	super(new BufferedOutputStream(o));
	r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
	return peekToken() != null;
    }

    public String peekWord(){
        return peekToken();
    }

    public int getInt() {
	return Integer.parseInt(nextToken());
    }

    public int tryInt(){
        String integer = nextToken();
        try {
            return Integer.parseInt(integer);
        }
        catch (Exception e){
            System.out.println("exception happend with " + integer);
            throw e;
        }
    }

    public double getDouble() { 
	return Double.parseDouble(nextToken());
    }

    public long getLong() {
	return Long.parseLong(nextToken());
    }

    public String getWord() {
	return nextToken();
    }

    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
	if (token == null) 
	    try {
		while (st == null || !st.hasMoreTokens()) {
		    line = r.readLine();
		    if (line == null) return null;
		    st = new StringTokenizer(line);
		}
		token = st.nextToken();
	    } catch (IOException e) { }
	return token;
    }

    private String nextToken() {
	String ans = peekToken();
	token = null;
	return ans;
    }
}
