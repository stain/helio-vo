package org.egso.provider.datamanagement.archives;

import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 * The Link Matrix object creates and keeps all information related with the Links.
 *
 * @author    Romain Linsolas (linsolas@gmail.com)
 * @version   1.0 - 18/10/2004
 */
public class LinkMatrix {

	/**
	 * Matrix of Links between Tables. Each column or row is related to a Table.
	 * To avoid occurences, we are only filling the first half of the Matrix, i.e.
	 * the cases matrix[x][y] where x &lt; y.
	 */
	private Stack<Link>[][] matrix = null;
	/**
	 * The list of Tables.
	 */
	private Table[] tables = null;
	/**
	 * Number of tables (which is also the nubmer of rows/columns of the Matrix).
	 */
	private int size = 0;


	/**
	 * Creates a new LinkMatrix object for a given Base.
	 *
	 * @param base  Base for which one a LinkMatrix will be created.
	 */
	@SuppressWarnings("unchecked")
  LinkMatrix(Base base) {
		List<Table> l = base.getTables();
		size = l.size();
		matrix = new Stack[size][size];
		tables = new Table[size];
		for (int i = 0; i < size; i++) {
			tables[i] = (Table) l.get(i);
		}
		int left = (size * (size - 1)) / 2;
		boolean finished = false;
		int depth = 1;
		while (!finished && (left > 0)) {
			int found = 0;
//			System.out.println("Finding at depth " + depth + ", " + left + " links left.");
			// For all tables...
			for (Table t:l)
			{
				// For all routes from this table, with a maximal depth of 'depth'...
				for (Stack<Link> link:getAllLinks(t, depth, new Vector<Link>(), new Stack<Link>(), new Vector<Stack<Link>>()))
				{
					if (addRoute(link)) {
						found++;
					}
				}
			}
//			System.out.println("Ending depth " + depth + ". " + found + " new results, " + left + " left.");
			finished = (found == 0);
			depth++;
		}
	}


	/**
	 * Gets all Links (without occurences) starting from a given Table at a given
	 * maximal depth.
	 *
	 * @param t              Starting Table.
	 * @param depth          Maximal depth for the search.
	 * @param previousLinks  Previous Links found (avoid occurences).
	 * @param stack          Stack of Links that represents the route created to
	 * reach the current starting Table.
	 * @param allStacks      All routes.
	 * @return               A Vector that contains a list of Stack, each Stack
	 * is a route from the starting Table to the ending Table (with a maximal
	 * depth links).
	 */
	private Vector<Stack<Link>> getAllLinks(Table t, int depth, Vector<Link> previousLinks, Stack<Link> stack, Vector<Stack<Link>> allStacks) {
		Table table = null;
		for (Link link:t.getLinks())
		{
			if (!previousLinks.contains(link)) {
				previousLinks.add(link);
				stack.push(link);
				table = link.getOtherField(t.getName()).getTable();
				if (depth > 1) {
					// Make a copy of allStack to avoid concurrent access.
					Vector<Stack<Link>> allStacks2 = new Vector<Stack<Link>>();
					allStacks2.addAll(allStacks);

					// Getting one step/depth forward.
					allStacks.addAll(getAllLinks(table, depth-1, previousLinks, stack, allStacks2));

				} else {
					Stack<Link> tmp = new Stack<Link>();
					tmp.addAll(stack);

					allStacks.add(tmp);
					stack.pop();
				}
			}
		}
		return (allStacks);
	}


	/**
	 * Returns the connection (i.e. the route) between two Tables.
	 *
	 * @param table1  The starting Table.
	 * @param table2  The ending Table.
	 * @return        The route, as a List of Links, between the two Tables, or
	 * <code>null</code> if this route doesn't exist.
	 */
	public List<Link> getConnection(String table1, String table2) {
		int x = findIndex(table1);
		int y = findIndex(table2);
		if ((x == -1) || (y == -1) || (x == y)) {
			return new Stack<Link>();
		}
		return ((matrix[x][y] != null) ? matrix[x][y] : matrix[y][x]);
	}


	/**
	 * Adds a route.
	 *
	 * @param stack  The Stack that contains the route (Stack of Links).
	 * @return       A boolean at <code>true</code> only if the route has been
	 * added to the Matrix. 
	 */
	private boolean addRoute(Stack<Link> stack) {
		Vector<String> onlyOne = new Vector<String>();
		Vector<String> more = new Vector<String>();
		for (Link link:stack)
		{
			String table = link.getStart().getTable().getName();
			if (!more.contains(table)) {
				if (!onlyOne.contains(table)) {
					onlyOne.add(table);
				} else {
					onlyOne.remove(table);
					more.add(table);
				}
			}
			table = link.getEnd().getTable().getName();
			if (!more.contains(table)) {
				if (!onlyOne.contains(table)) {
					onlyOne.add(table);
				} else {
					onlyOne.remove(table);
					more.add(table);
				}
			}
		}
		// If onlyOne contains 2 elements, then they are the starting and ending
		// table of the route. If its size is 0, it means that the route contains
		// a loop.
		if (onlyOne.size() != 2) {
			return (false);
		}
		return addStack(onlyOne.get(0), onlyOne.get(1), stack, false, true);
	}


	/**
	 * Adds a route (as a Stack) relying two Tables, with condition. If 'onlyIfNull'
	 * is <code>true</code> then the route will be added to the Matrix only if
	 * there was no route before. If 'onlyIfBigger' is <code>true</code>, then the
	 * route will be added only if the Matrix contains no route or a longer route
	 * than the one proposed.
	 *
	 * @param table1         Starting Table of the route.
	 * @param table2         Ending Table of the route.
	 * @param originalStack  The route to eventually add.
	 * @param onlyIfNull     <code>true</code> means that the route must be added
	 * only if the Matrix doesn't have already a route between table1 and table2.
	 * @param onlyIfBigger   <code>true</code> means that the route must be added
	 * only if the Matrix doesn't have already a route between table1 and table2
	 * or if this route is bigger (i.e. the Stack contains more elements than
	 * originalStack).
	 * @return               A boolean at <code>true</code> if the given route
	 * has been added to the Matrix.
	 */
	private boolean addStack(String table1, String table2, Stack<Link> originalStack, boolean onlyIfNull, boolean onlyIfBigger) {
		Stack<Link> stack = new Stack<Link>();
		for (Link l:originalStack)
			stack.push(l);

		// NOTE: Returns true ONLY if matrix[x][y] WAS null. In case of a replacement of matrix[x][y], it returns false!
		int a = findIndex(table1);
		int b = findIndex(table2);
		int x = a;
		int y = b;
		if (a < b) {
			x = b;
			y = a;
		}
		boolean isNull = (matrix[x][y] == null);
		// Change only if matrix[x][y] != null and matrix[x][y].size is bigger than stack.size.
		if (onlyIfBigger) {
			if (isNull) {
//				System.out.println("Adding (" + x + ", " + y + "): " + stack.size());
				matrix[x][y] = stack;
				return (true);
			} else {
				if (matrix[x][y].size() > stack.size()) {
//					System.out.println("Adding (" + x + ", " + y + "): " + stack.size());
					matrix[x][y] = stack;
				}
			}
			return (false);
		}
		// Change only if matrix[x][y] is null
		if (onlyIfNull) {
			if (isNull) {
//				System.out.println("Adding (" + x + ", " + y + "): " + stack.size());
				matrix[x][y] = stack;
				return (true);
			}
			return (false);
		}
//		System.out.println("Adding (" + x + ", " + y + "): " + stack.size());
		matrix[x][y] = stack;
		return (isNull);
	}


	/**
	 * Returns the index in the Matrix for a given Table.
	 *
	 * @param table  The Table.
	 * @return       The index of the Table, -1 if the Table doesn't exist.
	 */
	private int findIndex(String table) {
		int i = 0;
		boolean found = false;
		while (!found && (i < size)) {
			found = tables[i].getName().equals(table);
			i++;
		}
		return (found ? (--i) : -1);
	}


	/**
	 * String representation of the LinkMatrix.
	 *
	 * @return   String representation.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			sb.append(tables[i].getName() + " ");
		}
		sb.append("\n");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (j >= i) {
					sb.append("* ");
				} else {
					if (matrix[i][j] != null) {
						sb.append(matrix[i][j].size() + " ");
					} else {
						sb.append("- ");
					}
				}
			}
			sb.append("\n");
		}
		sb.append("\n");
		for (int i = 1; i < size; i++) {
			for (int j = 0; j < i; j++) {
				sb.append(tables[i].getName() + " <-> " + tables[j].getName() + ": ");
				if (matrix[i][j] != null) {
					for (Link link:matrix[i][j])
					{
						sb.append(" (" + link.toStringWithoutType() + ")");
					}
				} else {
					sb.append("?");
				}
				sb.append("\n");
			}
		}
		return (sb.toString());
	}

}

