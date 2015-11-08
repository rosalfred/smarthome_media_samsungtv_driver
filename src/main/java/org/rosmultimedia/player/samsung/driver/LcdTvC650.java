/**
 * This file is part of the Alfred package.
 *
 * (c) Mickael Gaillard <mick.gaillard@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.rosmultimedia.player.samsung.driver;

import org.rosmultimedia.player.samsung.SamsungTvNode;

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
