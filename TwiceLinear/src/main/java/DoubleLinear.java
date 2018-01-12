import java.util.NavigableSet;
import java.util.TreeSet;

public class DoubleLinear {
	public static int dblLinear(int n) {
		NavigableSet<Integer> u = new TreeSet<>();
		for (int min2x = 1, min3x = 1; u.size() < n;) {
			if (min2x * 2 + 1 < min3x * 3 + 1) {
				u.add(min2x * 2 + 1);
				min2x = u.higher(min2x);
			} else {
				u.add(min3x * 3 + 1);
				min3x = u.higher(min3x);
			}
		}
		return u.pollLast();
	}
}
