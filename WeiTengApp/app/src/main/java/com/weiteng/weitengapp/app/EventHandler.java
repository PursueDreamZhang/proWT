package com.weiteng.weitengapp.app;

import com.weiteng.weitengapp.interf.IEventCode;

public class EventHandler implements IEventCode{
	private static EventHandler mEventHandler;

	public static synchronized EventHandler getEventHandler() {
		if (mEventHandler == null) {
			mEventHandler = new EventHandler();
		}

		return mEventHandler;
	}
}
