package at.ait.dme.yumaJS.client.annotation.gui;

import at.ait.dme.yumaJS.client.annotation.gui.edit.BoundingBox;
import at.ait.dme.yumaJS.client.annotation.gui.edit.Range;
import at.ait.dme.yumaJS.client.annotation.gui.edit.Selection.SelectionChangeHandler;

/**
 * An interface for GUI elements that embody a fragment (as part of an annotation).
 * 
 * @author Rainer Simon <rainer.simon@ait.ac.at>
 */
public interface FragmentWidget extends Comparable<FragmentWidget> {
	
	public void setSelectionChangeHandler(SelectionChangeHandler handler);
	
	public BoundingBox getBoundingBox();
	
	public void setBoundingBox(BoundingBox bbox);
	
	public Range getRange();
	
	public void setRange(Range range);
	
	public void startEditing();
	
	public void cancelEditing();
	
	public void stopEditing();
	
	public void setZIndex(int idx);

}
