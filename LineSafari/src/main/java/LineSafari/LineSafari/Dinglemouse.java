package LineSafari.LineSafari;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class Dinglemouse {

	private int elementsCount;
	private int[] start;
	private int[] end;
	private char[][] grid;

	private final Element endPoint = new Element('X')
			.setNextDirections(Arrays.asList(OrientedPosition::left, OrientedPosition::right,
					OrientedPosition::backward, OrientedPosition::forward))
			.setNextCharacters(new char[] { 'X', '-', '|', '+' });
	private final Element leftRight = new Element('-').setNextDirections(Arrays.asList(OrientedPosition::forward))
			.setNextCharacters(new char[] { '-', '+', 'X' });
	private final Element upDown = new Element('|').setNextDirections(Arrays.asList(OrientedPosition::forward))
			.setNextCharacters(new char[] { '-', '+', 'X' });
	private final Element crossing = new Element('+')
			.setNextDirections(Arrays.asList(OrientedPosition::left, OrientedPosition::right))
			.setNextCharacters(new char[] { '-', '|', '+', 'X' });

	private static final Map<Character, Element> elements = new HashMap<>();
	{
		elements.put('X', endPoint);
		elements.put('-', leftRight);
		elements.put('|', upDown);
		elements.put('+', crossing);
	}

	private class OrientedPosition {
		private int x, y;
		private Orientation orientation;

		private OrientedPosition(int x, int y, Orientation orientation) {
			this.x = x;
			this.y = y;
			this.orientation = orientation;
		}

		OrientedPosition left() {
			return orientation.stepLeft(this);
		}

		OrientedPosition right() {
			return orientation.stepRight(this);
		}

		OrientedPosition forward() {
			return orientation.stepForward(this);
		}

		OrientedPosition backward() {
			return orientation.stepBackward(this);
		}

		OrientedPosition north() {
			return y > 0 ? new OrientedPosition(x, y - 1, Orientation.NORTH) : null;
		}

		OrientedPosition south() {
			return x < grid.length - 1 ? new OrientedPosition(x + 1, y, Orientation.SOUTH) : null;
		}

		OrientedPosition east() {
			return y < grid[x].length - 1 ? new OrientedPosition(x, y + 1, Orientation.EAST) : null;
		}

		OrientedPosition west() {
			return x > 0 ? new OrientedPosition(x - 1, y, Orientation.WEST) : null;
		}

		char peek() {
			return grid[x][y];
		}
	}

	private enum Orientation {
		//@formatter:off
		NORTH	(step -> step.north(), 	step -> step.south(), 	step -> step.west(), 	step -> step.east()), 
		SOUTH	(step -> step.south(), 	step -> step.north(), 	step -> step.east(), 	step -> step.west()), 
		WEST	(step -> step.east(), 	step -> step.west(), 	step -> step.north(), 	step -> step.south()), 
		EAST	(step -> step.west(), 	step-> step.east(), 	step -> step.south(), 	step -> step.north());
		//@formatter:on
		private Function<OrientedPosition, OrientedPosition> forward;
		private Function<OrientedPosition, OrientedPosition> backward;
		private Function<OrientedPosition, OrientedPosition> left;
		private Function<OrientedPosition, OrientedPosition> right;

		private Orientation(Function<OrientedPosition, OrientedPosition> forward,
				Function<OrientedPosition, OrientedPosition> backward,
				Function<OrientedPosition, OrientedPosition> left, Function<OrientedPosition, OrientedPosition> right) {
			this.forward = forward;
			this.backward = backward;
			this.left = left;
			this.right = right;
		}

		OrientedPosition stepForward(OrientedPosition step) {
			return forward.apply(step);
		}

		OrientedPosition stepBackward(OrientedPosition step) {
			return backward.apply(step);
		}

		OrientedPosition stepLeft(OrientedPosition step) {
			return left.apply(step);
		}

		OrientedPosition stepRight(OrientedPosition step) {
			return right.apply(step);
		}
	};

	private class Element {
		private OrientedPosition position;
		private List<Function<OrientedPosition, OrientedPosition>> nextDirections;
		private char value;
		private char[] nextCharacters;

		Element(char value) {
			this.value = value;
		}

		char gridValue() {
			return grid[position.x][position.y];
		}

		Element setNextDirections(List<Function<OrientedPosition, OrientedPosition>> directions) {
			this.nextDirections = directions;
			return this;
		}

		Element setNextCharacters(char[] nextCharacters) {
			this.nextCharacters = nextCharacters;
			return this;
		}

		void setPosition(OrientedPosition position) {
			this.position = position;
			if (gridValue() != value) {
				throw new IllegalArgumentException(
						"Element " + value + " cannot be assigned to cell with value " + gridValue());
			}
			if (gridValue() == '-'
					&& (position.orientation == Orientation.NORTH || position.orientation == Orientation.SOUTH)) {
				throw new IllegalArgumentException("- cannot be a value of verticallly oriented element");
			}
			if (gridValue() == '|'
					&& (position.orientation == Orientation.EAST || position.orientation == Orientation.WEST)) {
				throw new IllegalArgumentException("| cannot be a value of horizontally oriented element");
			}
		}

		Element getNextElement() {
			Optional<Function<OrientedPosition, OrientedPosition>> findFirst = nextDirections.stream()
					.filter(d -> d.apply(this.position).peek() != 0).findFirst();
			return findFirst.isPresent() ? elements.get(findFirst.get().apply(this.position).peek()) : null;
		}

	}

	private Dinglemouse(char[][] grid) {
		this.grid = grid;
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
		if (start == null || end == null) {
			throw new IllegalArgumentException("Both start and end points must be present");
		}
	}

	private boolean validateLine(int x, int y) {
		OrientedPosition startPosition = new OrientedPosition(x, y, Orientation.NORTH);
		Element element = elements.get('X');
		element.setPosition(startPosition);
		int count = 1;
		while (element.getNextElement() != null) {
			count++;
			element = element.getNextElement();
			if (element.gridValue() == 'X')
				break;
		}
		return count == elementsCount && element.value == 'X';
	}

	public static boolean line(final char[][] grid) {
		Dinglemouse dinglemouse = new Dinglemouse(grid);
		return dinglemouse.validateLine(dinglemouse.start[0], dinglemouse.start[1]) ? true
				: dinglemouse.validateLine(dinglemouse.end[0], dinglemouse.end[1]);
	}

}
