package at.ait.dme.yumaJS.client.annotation.ui;

import at.ait.dme.yumaJS.client.init.Labels;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;

/**
 * The {@link CommentWidget} is an editable text field. It is used as part of the 
 * {@link AnnotationWidget} while in editing mode.
 * 
 * @author Rainer Simon <rainer.simon@ait.ac.at>
 */
public class CommentWidget extends Composite {
		
	private FlowPanel container = new FlowPanel();
	
	private TextArea textArea = new TextArea();
	
	private PushButton btnSave, btnCancel;
	
	private FlowPanel buttonContainer = new FlowPanel();
	
	private boolean hasFocus = false;
	
	public CommentWidget(Labels labels, boolean showCancelButton) {
		this(null, labels, showCancelButton);
	}

	public CommentWidget(String text, Labels labels, boolean showCancelButton) {
		container.setStyleName("yuma-comment");
		textArea.setStyleName("yuma-comment-textarea");
		
		if (text == null) {
			textArea.getElement().setAttribute("placeholder", "Add a Comment...");
		} else {
			textArea.setText(text);
		}
		
		textArea.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				hasFocus = true;
			}
		});
		
		textArea.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				hasFocus = false;
			}
		});
		
		textArea.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
					clear();
				}
			}
		});
		
		if (labels == null) {
			btnSave = new PushButton("OK");
			btnCancel = new PushButton("CANCEL");
		} else {
			btnSave = new PushButton(labels.save());
			btnCancel = new PushButton(labels.cancel());			
		}
		
		btnSave.setStyleName("yuma-button");
		btnSave.addStyleName("yuma-button-save");
		
		btnCancel.setStyleName("yuma-button");
		btnCancel.addStyleName("yuma-button-cancel");

		buttonContainer.setStyleName("yuma-comment-buttons");
		
		buttonContainer.add(btnSave);
		if (showCancelButton)
			buttonContainer.add(btnCancel);
		

		container.add(textArea);
		container.add(buttonContainer);
		initWidget(container);
	}

	public boolean hasFocus() {
		return hasFocus;
	}
	
	public void setText(String text) {
		textArea.setText(text);
	}
	
	public String getText() {
		return textArea.getText();
	}
	
	public void setFocus(boolean focused) {
		hasFocus = focused;
		textArea.setFocus(focused);
	}
	
	public void clear() {
		textArea.setText(null);
		textArea.setFocus(false);
	}
	
	public HandlerRegistration addSaveClickHandler(ClickHandler handler) {
		return btnSave.addClickHandler(handler);
	}
	
	public HandlerRegistration addCancelClickHandler(ClickHandler handler) {
		return btnCancel.addClickHandler(handler);
	}
	
}
