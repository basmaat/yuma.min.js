package at.ait.dme.yumaJS.client.annotation.impl.openlayers;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.AbsolutePanel;

import at.ait.dme.yumaJS.client.annotation.Annotation;
import at.ait.dme.yumaJS.client.annotation.impl.openlayers.api.BoxMarker;
import at.ait.dme.yumaJS.client.annotation.impl.openlayers.api.BoxesLayer;
import at.ait.dme.yumaJS.client.annotation.ui.AnnotationWidget;
import at.ait.dme.yumaJS.client.annotation.ui.CompoundOverlay;
import at.ait.dme.yumaJS.client.annotation.ui.AnnotationWidget.AnnotationWidgetEditHandler;
import at.ait.dme.yumaJS.client.annotation.ui.edit.BoundingBox;
import at.ait.dme.yumaJS.client.annotation.ui.edit.Range;
import at.ait.dme.yumaJS.client.annotation.ui.edit.Selection.SelectionChangeHandler;

public class SingleOpenLayersAnnotationOverlay implements OpenLayersCompoundOverlay {

	private AbsolutePanel editingLayer;
	
	private OpenLayersFragmentWidget boxOverlay;
	
	private AnnotationWidget annotationWidget;
	
	public SingleOpenLayersAnnotationOverlay(Annotation a, BoxesLayer annotationLayer,
			AbsolutePanel editingLayer, OpenLayersAnnotationLayer annotatable) {
		
		this.editingLayer = editingLayer;
		
		this.boxOverlay = new OpenLayersFragmentWidget(
				annotatable.toOpenLayersBounds(a.getFragment()),
				annotationLayer, editingLayer, annotatable);
		
		this.boxOverlay.setSelectionChangeHandler(new SelectionChangeHandler() {
			public void onRangeChanged(Range range) { }
			
			public void onBoundsChanged(BoundingBox bbox) {
				refresh();				
			}
		});

		this.boxOverlay.setEventListener(new EventListener() {
			public void onBrowserEvent(Event event) {
				if (event.getTypeInt() == Event.ONMOUSEOUT) {
					if (!annotationWidget.contains(event.getClientX(), event.getClientY()))
						annotationWidget.setVisible(false);
				} else if (event.getTypeInt() == Event.ONMOUSEWHEEL) {
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						public void execute() {
							refresh();
						}
					});
				} else {
					annotationWidget.setVisible(true);
					refresh();
				}
			}
		});
		
		annotationWidget = new AnnotationWidget(a, boxOverlay, annotatable);
		annotationWidget.addDomHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!annotationWidget.isEditing())
					annotationWidget.setVisible(false);
			}
		}, MouseOutEvent.getType());
		
		annotationWidget.setVisible(false);
		editingLayer.add(annotationWidget);
		refresh();
	}
	
	private void refresh() {
		BoundingBox bbox = boxOverlay.getBoundingBox();
		editingLayer.setWidgetPosition(annotationWidget, bbox.getX(), bbox.getY() +  bbox.getHeight() + 2);
	}
	
	public void addAnnotationWidgetEditHandler(Annotation a, AnnotationWidgetEditHandler handler) {
		annotationWidget.addAnnotationWidgetEditHandler(handler);
	}

	public void removeAnnotationWidgetEditHandler(Annotation a,	AnnotationWidgetEditHandler handler) {
		annotationWidget.removeAnnotationWidgetEditHandler(handler);
	}
	
	public void updateAnnotation(String id, Annotation updated) {
		annotationWidget.setAnnotation(updated);
	}
	
	public void removeAnnotation(String id) {
		// Not supported by this CompoundOverlayType
		// use destroy() instead!
	}
	
	public BoxMarker getMarker() {
		return boxOverlay.getBoxMarker();
	}

	public void edit(Annotation a) {
		annotationWidget.edit();	
	}
	
	public void destroy() {
		annotationWidget.removeFromParent();
	}

	public void setZIndex(int idx) {
		boxOverlay.setZIndex(idx);
	}

	public int compareTo(CompoundOverlay other) {
		if (!(other instanceof SingleOpenLayersAnnotationOverlay))
			return 0;
		
		SingleOpenLayersAnnotationOverlay overlay = (SingleOpenLayersAnnotationOverlay) other;
		
		// Delegate to bbox overlay
		return boxOverlay.compareTo(overlay.boxOverlay);
	}

}
