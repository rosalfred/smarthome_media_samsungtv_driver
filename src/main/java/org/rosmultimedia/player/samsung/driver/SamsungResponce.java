/**
 * This file is part of the Alfred package.
 *
 * (c) Mickael Gaillard <mick.gaillard@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.rosmultimedia.player.samsung.driver;

import java.util.Arrays;

/**
 * Samsung
 *
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class SamsungResponce {
	public static final String ACK = "00";
	public static final String CONFIRMED = "02";

	private String code;
	private String[] datas;
	private String result;
	//private String app;
	private int appSize;
	//private int resultSize;

	public SamsungResponce(char[] data) {
		this.datas = new String(data).split(" ");
		// self.datas = self.__byteToHex( self.data ).split( " " )

		int offset = 0;
		int leng = this.datas.length;

		if (leng >= 1) {
			// Get code
			this.code = this.datas[offset];

			if (leng >= 2) {
				// Get app
				this.appSize = Integer.valueOf(this.datas[offset]);
				int decay = 3;			// code + size + separator
				offset = this.appSize + decay;

				if (leng >= offset) {
					//this.resultSize = Integer.valueOf(this.datas[offset]);
					decay = decay + 2;	// old_decay + size + separator
					offset = this.appSize + decay;

					if (leng >= offset) {
						this.result = Arrays.toString(
								Arrays.copyOfRange(
										this.datas,
										offset,
										this.datas.length-1));
					}
				}
			}
		}
	}

	public String getResult() {
		return this.result;
	}

	public String getCode() {
		return this.code;
	}

	public String byteToHex (byte[] byteString) {
		return ""; // ''.join( [ "%02X " % ord( x ) for x in byteStr ] )
	}

}
