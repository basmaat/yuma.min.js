package at.ait.dme.yumaJS.client.annotation.impl.image;

import at.ait.dme.yumaJS.client.annotation.Annotatable;
import at.ait.dme.yumaJS.client.annotation.Annotation;
import at.ait.dme.yumaJS.client.annotation.ui.AnnotationWidget;
import at.ait.dme.yumaJS.client.annotation.ui.CompoundOverlay;
import at.ait.dme.yumaJS.client.annotation.ui.AnnotationWidget.AnnotationWidgetEditHandler;
import at.ait.dme.yumaJS.client.annotation.ui.edit.BoundingBox;
import at.ait.dme.yumaJS.client.annotation.ui.edit.Range;
import at.ait.dme.yumaJS.client.annotation.ui.edit.Selection.SelectionChangeHandler;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;

/**
 * This is the image annotation overlay type that is used when reply functionality is DISABLED. 
 * It consists of a {@link ImageFragmentWidget} with a single {@link AnnotationWidget} underneath.
 * When in editing mode, the {@link AnnotationWidget} stays clamped to the lower-left corner
 * of the {@link ImageFragmentWidget}.
 * 
 * @author Rainer Simon <rainer.simon@ait.ac.at>
 */
public class SingleImageAnnotationOverlay implements CompoundOverlay {
		
	private ImageFragmentWidget bboxOverlay;
	
	private AnnotationWidget annotationWidget;
	
	private AbsolutePanel annotationLayer;
	
	public SingleImageAnnotationOverlay(Annotation a, Annotatable annotatable, 
			final AbsolutePanel annotationLayer) {
		
		this.annotationLayer = annotationLayer;
		
		final BoundingBox bbox = annotatable.toBoundingBox(a.getFragment());
		
		bboxOverlay = new ImageFragmentWidget(annotatable, annotationLayer, bbox);
		
		bboxOverlay.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				refresh();
				annotationWidget.setVisible(true);
			}
		});
		
		bboxOverlay.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!annotationWidget.contains(
						event.getRelativeX(annotationLayer.getElement()) + annotationLayer.getAbsoluteLeft(), 
						event.getRelativeY(annotationLayer.getElement()) + annotationLayer.getAbsoluteTop()))
					
					annotationWidget.setVisible(false);
			}
		});
		
		bboxOverlay.setSelectionChangeHandler(new SelectionChangeHandler() {
			public void onRangeChanged(Range range) { }
			
			public void onBoundsChanged(BoundingBox bbox) {
				refresh();				
			}
		});
		
		annotationWidget = new AnnotationWidget(a, bboxOverlay, annotatable);
		
		annotationWidget.addDomHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if (!annotationWidget.isEditing())
					annotationWidget.setVisible(false);
			}
		}, MouseOutEvent.getType());
		
		annotationWidget.setVisible(false);		
		annotationLayer.add(annotationWidget);
		refresh();
	}
	
	private void refresh() {
		BoundingBox bbox = bboxOverlay.getBoundingBox();
		annotationLayer.add(annotationWidget, bbox.getX(), bbox.getY() + bbox.getHeight() + 2);		
	}

	public void addAnnotationWidgetEditHandler(Annotation a, final AnnotationWidgetEditHandler handler) {
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
	
	public void edit(Annotation a) {
		annotationWidget.edit();
	}
	
	public AnnotationWidget getAnnotationWidget() {
		return annotationWidget;
	}

	public void setZIndex(int idx) {
		bboxOverlay.setZIndex(idx);
	}
	
	public void destroy() {
		bboxOverlay.destroy();
		annotationWidget.removeFromParent();
	}

	public int compareTo(CompoundOverlay other) {
		if (!(other instanceof SingleImageAnnotationOverlay))
			return 0;
		
		SingleImageAnnotationOverlay overlay = (SingleImageAnnotationOverlay) other;
		
		// Delegate to bbox overlay
		return bboxOverlay.compareTo(overlay.bboxOverlay);
	}

}
