package at.ait.dme.yumaJS.client;

import at.ait.dme.yumaJS.client.annotation.impl.html5media.audio.AudioPlayer;
import at.ait.dme.yumaJS.client.init.InitParams;

import com.google.gwt.core.client.EntryPoint;

public class TestAudioHosted implements EntryPoint {
	
	public void onModuleLoad() {
		new AudioPlayer("../samples/KevinMacLeod-AcidTrumpet.ogg", 
				"annotateMe", createInitParams());
	}
	
	private native InitParams createInitParams() /*-{
		return {
			height:20,
			width:360,
			stylesheet:'../css/theme-dark'
		};
	}-*/;

}
