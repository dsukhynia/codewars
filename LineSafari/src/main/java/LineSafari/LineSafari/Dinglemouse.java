package LineSafari.LineSafari;

import java.util.function.Function;

public class Dinglemouse {

	private int elementsCount;
	private int[] start;
	private int[] end;
	private char[][] grid;
	private Orientation direction;

	private enum Orientation {
		//@formatter:off
		NORTH	(step -> step.north(), 	step -> step.south(), 	step -> step.west(), 	step -> step.east()), 
		SOUTH	(step -> step.south(), 	step -> step.north(), 	step -> step.east(), 	step -> step.west()), 
		WEST	(step -> step.east(), 	step -> step.west(), 	step -> step.north(), 	step -> step.south()), 
		EAST	(step -> step.west(), 	step-> step.east(), 	step -> step.south(), 	step -> step.north());
		//@formatter:on
		private Function<Step, int[]> forward;
		private Function<Step, int[]> backward;
		private Function<Step, int[]> left;
		private Function<Step, int[]> right;

		private Orientation(Function<Step, int[]> forward, Function<Step, int[]> backward, Function<Step, int[]> left,
				Function<Step, int[]> right) {
			this.forward = forward;
			this.backward = backward;
			this.left = left;
			this.right = right;
		}

		int[] stepForward(Step step) {
			return forward.apply(step);
		}

		int[] stepBackward(Step step) {
			return backward.apply(step);
		}

		int[] stepLeft(Step step) {
			return left.apply(step);
		}

		int[] stepRight(Step step) {
			return right.apply(step);
		}
	};
	

	private abstract class Step {
		protected int x, y;
		protected Orientation orientation;

		protected Step(int x, int y, Orientation orientation) {
			this.x = x;
			this.y = y;
			this.orientation = orientation;
		}

		protected int[] north() {
			return y > 0 ? new int[] { x, y - 1 } : null;
		}

		protected int[] south() {
			return x < grid.length - 1 ? new int[] { x + 1, y } : null;
		}

		protected int[] east() {
			return y < grid[x].length - 1 ? new int[] { x, y + 1 } : null;
		}

		protected int[] west() {
			return x > 0 ? new int[] { x - 1, y } : null;
		}

		abstract Step nextStep();
	}

	class TerminalStep extends Step {
		protected TerminalStep(int x, int y) {
			super(x, y, Orientation.NORTH);
			if (grid[x][y] != 'X') {
				throw new IllegalArgumentException();
			}
		}

		public Step nextStep() {
			return null;
		}
	}

	private Dinglemouse(char[][] grid) {
		elementsCount = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] != ' ') {
					elementsCount++;
					if (grid[i][j] == 'X') {
						if (start == null)
							start = new int[] { i, j };
						else if (end == null) {
							end = new int[] { i, j };
						}
					}
				}
			}
		}
	}

	private boolean validateLine() {
		Step step = new TerminalStep(start[0], start[1]);
		while ((step = step.nextStep()) != null) {
			elementsCount--;
		}
		return step instanceof TerminalStep && elementsCount == 0;
	}

	public static boolean line(final char[][] grid) {
		return false;
	}

	public static void main(String[] args) {
	}

}
