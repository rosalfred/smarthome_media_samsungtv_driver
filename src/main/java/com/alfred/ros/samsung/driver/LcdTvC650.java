package com.alfred.ros.samsung.driver;

import com.alfred.ros.samsung.SamsungTvNode;

/**
 * Samsung SmartTV LExxC650 drivers.<br/>
 * Base on IP TV node.<br/>
 * TODO : PIP sound channels
 * 
 * @since 1.0
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class LcdTvC650 extends SamsungRemoteSession {

	public LcdTvC650(final SamsungTvNode samsungTvNode) {
		super(samsungTvNode);
	}

}
