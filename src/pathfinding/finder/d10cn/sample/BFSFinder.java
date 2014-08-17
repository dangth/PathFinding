package pathfinding.finder.d10cn.sample;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import pathfinding.enviroment.PCell;
import pathfinding.enviroment.PDirection;
import pathfinding.enviroment.PGrid;
import pathfinding.finder.AbstractPFinder;

public class BFSFinder extends AbstractPFinder {
	private final Queue<PState> mStateQueue;

	private final boolean[][] mVisitedCell;

	private int mVisitedNo;

	public BFSFinder(PGrid grid) {
		// set grid
		super(grid);

		// Initialised State Queue
		mStateQueue = new LinkedList<PState>();

		// Initialised Visited Cell
		mVisitedCell = new boolean[PGrid.GRID_SIZE][PGrid.GRID_SIZE];

		mVisitedNo = 0;
	}

	@Override
	public List<PDirection> find() {
		// Get the start stage
		mStateQueue.add(new PState(mGrid.getStart(), new LinkedList<PDirection>()));

		while (!mStateQueue.isEmpty()) {

			// Get current
			PState current = mStateQueue.poll();
			PCell currentCell = current.getPCell();
			if (!isVisited(currentCell)) {
				// Set visited cell
				mVisitedCell[currentCell.getCol()][currentCell.getRow()] = true;
				mVisitedNo++;

				// Check if gold
				if (mGrid.isGold(current.getPCell())) {
					return current.getPath();
				}
				// Get next and add to the queue O
				List<PState> nexts = getNextState(current);
				for (PState state : nexts) {
					System.out.print(state.getPCell().getCol() + ":" + state.getPCell().getRow() + " = ");
					System.out.println(mGrid.heuristicA(state.getPCell()));
					mStateQueue.add(state);
				}
			}
		}
		return null;
	}

	private List<PState> getNextState(PState current) {
		List<PState> nexts = new LinkedList<PState>();

		// Get current position
		// Get current path
		PCell cell = current.getPCell();
		List<PDirection> path = current.getPath();

		PDirection[] nextDirection;
		if (path.size() != 0) {
			// If there is a last direction
			PDirection lastDirection = path.get(path.size() - 1);

			// According to the last direction choose only 5 remaining direction
			switch (lastDirection) {
			case N:
				nextDirection = new PDirection[] { PDirection.W, PDirection.NW, PDirection.N, PDirection.NE, PDirection.E };
				break;
			case NE:
				nextDirection = new PDirection[] { PDirection.NW, PDirection.N, PDirection.NE, PDirection.E, PDirection.SE };
				break;
			case E:
				nextDirection = new PDirection[] { PDirection.N, PDirection.NE, PDirection.E, PDirection.SE, PDirection.S };
				break;
			case SE:
				nextDirection = new PDirection[] { PDirection.NE, PDirection.E, PDirection.SE, PDirection.S, PDirection.SW };
				break;
			case S:
				nextDirection = new PDirection[] { PDirection.E, PDirection.SE, PDirection.S, PDirection.SW, PDirection.W };
				break;
			case SW:
				nextDirection = new PDirection[] { PDirection.SE, PDirection.S, PDirection.SW, PDirection.W, PDirection.NW };
				break;
			case W:
				nextDirection = new PDirection[] { PDirection.S, PDirection.SW, PDirection.W, PDirection.NW, PDirection.N };
				break;
			case NW:
				nextDirection = new PDirection[] { PDirection.SW, PDirection.W, PDirection.NW, PDirection.N, PDirection.NE };
				break;
			default:
				// Never happen
				nextDirection = null;
			}
		} else {
			// If no previous direction is found, try all 8
			nextDirection = new PDirection[] { PDirection.E, PDirection.SE, PDirection.S, PDirection.SW, PDirection.W, PDirection.NW, PDirection.N, PDirection.NE };
		}

		getNextState(nexts, cell, path, nextDirection);

		return nexts;
	}

	private void getNextState(List<PState> nextPStates, PCell cell, List<PDirection> path, PDirection[] directions) {
		for (PDirection direction : directions) {

			// Calculate next position
			int nextCol = direction.getNextCol(cell.getCol());
			int nextRow = direction.getNextRow(cell.getRow());

			PCell nextCell = new PCell(nextCol, nextRow);

			if (mGrid.isValidCell(nextCell)) {
				// Clone old path
				List<PDirection> nextPath = new LinkedList<PDirection>(path);

				// Add next direction
				nextPath.add(direction);

				// Add this to nextPStates
				nextPStates.add(new PState(nextCell, nextPath));
			}
		}
	}

	private boolean isVisited(PCell cell) {
		return mVisitedCell[cell.getCol()][cell.getRow()];
	}
}
