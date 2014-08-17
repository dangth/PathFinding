package pathfinding.finder.d10cn.sample;

import java.util.LinkedList;
import java.util.List;

import pathfinding.enviroment.PCell;
import pathfinding.enviroment.PDirection;

public class PState {
	private final PCell mCell;
	private final List<PDirection> mPath;

	public PState(PCell cell, List<PDirection> path) {
		mCell = cell;
		mPath = path;
	}

	public PCell getPCell() {
		return mCell;
	}

	public List<PDirection> getPath() {
		return new LinkedList(mPath);
	}
}
