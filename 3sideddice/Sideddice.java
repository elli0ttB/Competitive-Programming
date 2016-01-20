import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

public class Sideddice {
    Kattio io = new Kattio(System.in);
    public Rational[][] dice;
    final Rational zero = new Rational(0, 1);

    public Sideddice(){
        // Given Three Dice, three probablity distrubtions, roll 1

    }

    // if matrix is invertible:
    //     find transmoration vector.
    //     if each term of the transformation vector is positive: 
    //         return true.
    //     else
    //         return false.
    // if not invertible
    //    if transformation vector exists
    //        GOTO 21.
    //    else 
    //       return false;
    //       ORRRR just find 
    //        A1 A2 A3 | V1
    //        B1 B2 B3 | V2
    //        C1 C2 C3 | V3

    public boolean solve(){
        // how do we handle divide by zero exceptions?
        // it means the matrix is not invertible
        // it won't happen unless a whole column is 0

        for (int i=0; i< 3; i++){

            if (dice[i][i].equals(zero)){
                // look for first element that is not zero
                for (int row = i + 1; row < 4; row++){
                    if (! dice[row][i].equals(zero)) {
                        // swap to shove zero down column
                        dice[i][i] = dice[row][i];
                        dice[row][i] = zero;
                        break;
                    }
                }
                if (dice[i][i].equals(zero))
                    continue;
            }
            // STEp 2: make dice[i][i] = 1 by dividing out row
            Rational divisor = dice[i][i];
            for (int col = i; col < 4; col++){
                dice[i][col] = dice[i][col].divide(dice[i][i]);
            }

            // STEP 3: zero out everything below i for that column for each row
            for(int j=i+1; j< 3; j++){
                Rational factor = dice[j][i].divide(dice[i][i]);
                
                for (int col=i; col < 4 ; col++)
                    dice[j][col] = dice[j][col].minus
                                  (dice[i][col].times(factor));
            }


        }
        partialPivot(0);
        zeroOut(0, new int[]{1, 2});

        partialPivot(1);
        zeroOut(1, new int[]{2});
        //// ^^^ in this part  ^^^ //// 

        // back substitution
        zeroOut(2, new int[]{0, 1});
        zeroOut(1, new int[]{0});

        // check that solution is entirely positive.
        for (int i=0; i < 3; i++){
            dice[i][3] = dice[i][3].divide(dice[i][3]);
            if (! dice[i][3].nonNegative())
                return false;
        }

        return true;
    }

    // add row1 * factor to row2

    public void addRow(int rowAdded, int rowAddedTo, Rational factor){
        for (int i = 0; i < 4; i++){
            dice[rowAddedTo][i] = dice[rowAddedTo][i]
                                 .plus
                                 (dice[rowAdded][i].times(factor));
        }

    }
    public static void main(String[] args){
        new Sideddice();
    }

    class Rational implements Comparable<Rational>{
        int numerator;
        int denominator;

        public Rational(int x, int y){
            this.numerator = x;
            this.denominator = y;
            simplify();
        }

        public Rational times(int x){
            return new Rational(x * numerator, denominator);
        }

        public Rational times(Rational o){
            return new Rational(numerator * o.numerator,
                    denominator * o.denominator);
        }
        
        public Rational inverse(){
            return new Rational(denominator, numerator);
        }

        public Rational plus (Rational o){
            int num = this.numerator * o.denominator + o.numerator * this.denominator;
            int denom =this.denominator * o.denominator;
            return new Rational(num, denom);
        }

        public Rational minus (Rational o){
            return plus(o.times(-1));
        }

        public Rational divide (int x){
            return new Rational(numerator, x * denominator);
        }

        public Rational divide (Rational o){
            return times(o.inverse());
        }

        public Rational (Rational r){
            this.numerator = r.numerator;
            this.denominator = r.denominator;
        }

        public boolean nonNegative(){
            return numerator > 0;
        }

        private Rational simplify(){
            if (denominator < 0) {
                denominator *= -1;
                numerator  *= -1;
            }
            int max = Math.max(Math.abs(numerator), denominator);
            int min = Math.min(Math.abs(numerator), denominator);
            int gcd = gcd(max, min);
            numerator /= gcd;
            denominator /= gcd;
            return this;
        }

        private int gcd(int max, int min){
            return (min == 0) ? max : gcd(min, max % min);
        }

        @Override
        public int compareTo(Rational o){
            // return this - o
            return this.minus(o).nonNegative() ? 1 : -1;
        }

        @Override
        public boolean equals(Object o){
            Rational r = (Rational)o;
            return (numerator == r.numerator) &&
                (denominator == r.denominator);
        }


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
