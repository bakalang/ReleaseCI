package com.asuscloud.doTestcase.action.base;

import java.util.EventListener;

public interface TableButtonListener extends EventListener {
	  public void tableButtonClicked( int row, int col );
	}
