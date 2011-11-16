package at.ait.dme.yumaJS.client.annotation.widgets;

import at.ait.dme.yumaJS.client.annotation.widgets.edit.BoundingBox;
import at.ait.dme.yumaJS.client.annotation.widgets.edit.Range;
import at.ait.dme.yumaJS.client.annotation.widgets.edit.Selection.SelectionChangeHandler;

public interface FragmentWidget {
	
	public BoundingBox getBoundingBox();
	
	public void setBoundingBox(BoundingBox bbox);
	
	public Range getRange();
	
	public void setRange(Range range);
	
	public void startEditing(SelectionChangeHandler handler);
	
	public void stopEditing();

}
