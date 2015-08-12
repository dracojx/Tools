
public class Grouping {

	public static void main(String[] args) {
		int max = 9;
		int numbers = 4;
		int index = 0;
		int[] a = new int[numbers + 1];
		int[] b = new int[max * numbers];
		
		while(index < 36) {
			int n = (int) (Math.random() * numbers) + 1;
			if(a[n] < max) {
				b[index] = n;
				a[n]++;
				index++;
			}
		}
		
		for(int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}
	}

}
