package LineSafari.LineSafari;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ExampleTests {  

  // "Good" examples from the Kata description.
  
  @Test
  public void exGood1() {
    final char grid[][] = makeGrid(new String[] {
    "           ",
    "X---------X",
    "           ",
    "           "
    });
    
    assertEquals(true, Dinglemouse.line(grid));
  }

  @Test
  public void exGood2() {
    final char grid[][] = makeGrid(new String[] {
    "     ",
    "  X  ",
    "  |  ",
    "  |  ",
    "  X  "
    });
    
    assertEquals(true, Dinglemouse.line(grid));
  }

  @Test
  public void exGood3() {
    final char grid[][] = makeGrid(new String[] {
    "                    ",    
    "     +--------+     ",
    "  X--+        +--+  ",
    "                 |  ",
    "                 X  ",
    "                    "
    });
    
    assertEquals(true, Dinglemouse.line(grid));
  }

  @Test
  public void exGood4() {
    final char grid[][] = makeGrid(new String[] {
    "                     ",    
    "    +-------------+  ",
    "    |             |  ",
    " X--+      X------+  ",
    "                     "
    });
    
    assertEquals(true, Dinglemouse.line(grid));
  }

  @Test
  public void exGood5() {
    final char grid[][] = makeGrid(new String[] {
    "                      ",    
    "   +-------+          ",
    "   |      +++---+     ",
    "X--+      +-+   X     "
    });
    
    assertEquals(true, Dinglemouse.line(grid));
  }

  // "Bad" examples from the Kata description.
  
  @Test
  public void exBad1() {
    final char grid[][] = makeGrid(new String[] {
    "X-----|----X"
    });
    
    assertEquals(false, Dinglemouse.line(grid));
  }
  
  @Test
  public void exBad2() {
    final char grid[][] = makeGrid(new String[] {
    " X  ",
    " |  ",
    " +  ",
    " X  "
    });
    
    assertEquals(false, Dinglemouse.line(grid));
  }

  @Test
  public void exBad3() {
    final char grid[][] = makeGrid(new String[] {
    "   |--------+    ",
    "X---        ---+ ",
    "               | ",
    "               X "
    });
    
    assertEquals(false, Dinglemouse.line(grid));
  }

  @Test
  public void exBad4() {
    final char grid[][] = makeGrid(new String[] {
    "              ",
    "   +------    ",
    "   |          ",
    "X--+      X   ",
    "              "
    });
    
    assertEquals(false, Dinglemouse.line(grid));
  }
  
  @Test
  public void exBad5() {
    final char grid[][] = makeGrid(new String[] {
    "      +------+",
    "      |      |",
    "X-----+------+",
    "      |       ",
    "      X       ",
    });
    
    assertEquals(false, Dinglemouse.line(grid));
  }
  
  @Test
  public void exBad6() {
	  final char grid[][] = makeGrid(new String[] {
			"   X-----+",  
			" X |     |",
			"   |     |",
			"   |     |",
			"   +-----+"			  
	  });
	  
	  assertEquals(false, Dinglemouse.line(grid));
  }
  
  @Test
  public void exBad7() {
	  final char grid[][] = makeGrid(new String[] {
			   "+-----+",  
			   "|+---+|",  
			   "||+-+||",  
			   "|||X+||",  
			   "X|+--+|",  
			   " +----+"  			  
	  });
	  
	  assertEquals(true, Dinglemouse.line(grid));
  }
  
  @Test
  public void breadcrumbsOneWay() {
	  final char grid[][] = makeGrid(new String[] {
		         "      X  ",  
		         "X+++  +-+", 
		         " +++--+ |", 
		         "      +-+" 			  
	  });
	  
	  assertEquals(true, Dinglemouse.line(grid));
  }

	private static char[][] makeGrid(String[] strings) {
		char[][] grid = new char[strings.length][];
		for (int i = 0; i < strings.length; i++) {
			grid[i] = strings[i].toCharArray();
		}
		return grid;
	}
  
}
