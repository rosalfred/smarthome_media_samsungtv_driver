package org.rosmultimedia.player.samsung.driver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import org.apache.commons.codec.binary.Base64;
import org.rosmultimedia.player.samsung.SamsungCommand;
import org.rosmultimedia.player.samsung.SamsungTvNode;

/**
* Copied from https://github.com/keremkusmezer/SamyGo-Android-Remote/tree/master/src/de/quist/samy/remocon,
* since there is no binary build available anymore. Thanks to Tom Quist!
*
* @author Tom Quist
* @author Mickael Gaillard <mick.gaillard@gmail.com>
*/
public class SamsungRemoteSession {

	public static final String REMOTE		= "Alfred";	// REMOTE name
	public static final String APP 		= "ros.samsung.tv";		// iphone..iapp.samsung
	private static final String TV 	= "LE32C650";	// iphone.LE32C650.iapp.samsung

	private static final char[] ALLOWED_BYTES = new char[] {0x64, 0x00, 0x01, 0x00};
	private static final char[] DENIED_BYTES = new char[] {0x64, 0x00, 0x00, 0x00};
	private static final char[] TIMEOUT_BYTES = new char[] {0x65, 0x00};

	public static final String ALLOWED = "ALLOWED";
	public static final String DENIED = "DENIED";
	public static final String TIMEOUT = "TIMEOUT";

	private transient String applicationName;
	private transient String uniqueId;
	private transient String host;
	private transient int port;

	private transient Socket socket;

	private transient InputStreamReader reader;

	private transient BufferedWriter writer;

	private transient SamsungTvNode node;

	public SamsungRemoteSession(SamsungTvNode samsungTvNode) {

	}

	private SamsungRemoteSession(
			final SamsungTvNode samsungTvNode,
			final String applicationName,
			final String uniqueId,
			final String host,
			final int port) {
		this.applicationName = applicationName;
		this.uniqueId = uniqueId;

		if (this.uniqueId == null) {
			this.uniqueId = "";
		}

		this.host = host;
		this.port = port;

		this.node = samsungTvNode;
	}

	public static SamsungRemoteSession create(
			final SamsungTvNode samsungTvNode,
			final String applicationName,
			final String uniqueId,
			final String host,
			final int port)
					throws IOException, Exception, TimeoutException {

		SamsungRemoteSession session = new SamsungRemoteSession(
				samsungTvNode,
				applicationName,
				uniqueId,
				host,
				port);

		final String result = session.initialize();
		if (!result.equals(ALLOWED)) {
			if (result.equals(DENIED)) {
				throw new Exception("Connection Denied!"); //ConnectionDenied
			} else

			if (result.equals(TIMEOUT)) {
				throw new TimeoutException();
			}
		}

		// for now we just assume to be connected
		return session;
	}

	private String initialize() throws UnknownHostException, IOException {
		this.node.logD("Creating socket for host " + host + " on port " + port);

		this.socket = new Socket();
		this.socket.connect(new InetSocketAddress(host, port), 5000);

		this.node.logD("Socket successfully created and connected");
		final InetAddress localAddress = this.socket.getLocalAddress();
		this.node.logD("Local address is " + localAddress.getHostAddress());

		this.node.logD("Sending registration message");
		this.writer = new BufferedWriter(
				new OutputStreamWriter(this.socket.getOutputStream()));

		this.writer.append((char)0x00);
		writeText(writer, APP);
		writeText(writer, getRegistrationPayload(localAddress.getHostAddress()));
		this.writer.flush();

		final InputStream inputStream = socket.getInputStream();
		reader = new InputStreamReader(inputStream);
		final String result = readRegistrationReply(reader);
		//sendPart2();

		int dataSize;
		while ((dataSize = inputStream.available()) > 0) {
			inputStream.skip(dataSize);
		}

		return result;
	}

	@SuppressWarnings("unused")
	private void sendPart2() throws IOException {
		this.writer.append((char) 0x00);
		writeText(writer, TV);
		writeText(writer, new String(new char[] { 0xc8, 0x00 }));
	}

	private void checkConnection() throws UnknownHostException, IOException {
		if (socket.isClosed() || !socket.isConnected()) {
			this.node.logI("Connection closed, trying to reconnect...");

			try {
				this.socket.close();
			} catch (IOException e) {
				// Ignore any exception
			}

			this.initialize();
			this.node.logI("Reconnected to server");
		}
	}

	public void destroy() {
		try {
			socket.close();
		} catch (IOException e) {
			// Ignore exception
		}
	}

	private String readRegistrationReply(Reader reader) throws IOException {
		String result;
		this.node.logD("Reading registration reply");
		reader.read(); // Unknown byte 0x02
		final String text1 = readText(reader); // Read
											// "unknown.livingroom.iapp.samsung"
											// for new RC and "iapp.samsung" for
											// already registered RC
		this.node.logD("Received ID: " + text1);
		final char[] resultSeq = readCharArray(reader); // Read result sequence
		if (Arrays.equals(resultSeq, ALLOWED_BYTES)) {
			this.node.logD("Registration successfull");
			result = ALLOWED;
		} else if (Arrays.equals(resultSeq, DENIED_BYTES)) {
			this.node.logD("Registration denied");
			result = DENIED;
		} else if (Arrays.equals(resultSeq, TIMEOUT_BYTES)) {
			this.node.logD("Registration timed out");
			result = TIMEOUT;
		} else {
			final StringBuilder builder = new StringBuilder();

			for (final char subSeq : resultSeq) {
				builder.append( Integer.toHexString(subSeq) );
				builder.append( ' ' );
			}

			result = builder.toString();
			this.node.logE("Received unknown registration reply: " + result);
		}

		return result;
	}

	private String getRegistrationPayload(final String ip) throws IOException {
		final StringWriter writer = new StringWriter();
		writer.append((char) 0x64);
		writer.append((char) 0x00);
		writeBase64Text(writer, ip);
		writeBase64Text(writer, uniqueId);
		writeBase64Text(writer, applicationName);
		writer.flush();
		return writer.toString();
	}

	private static String readText(final Reader reader) throws IOException {
		final char[] buffer = readCharArray(reader);
		return new String(buffer);
	}

	private static char[] readCharArray(final Reader reader) throws IOException {
		if (reader.markSupported()) {
			reader.mark(1024);
		}

		final int length = reader.read();
		final int delimiter = reader.read();
		if (delimiter != 0) {
			if (reader.markSupported()) {
				reader.reset();
			}
			throw new IOException("Unsupported reply exception");
		}

		final char[] buffer = new char[length];
		reader.read(buffer);
		return buffer;
	}

	private static Writer writeText(final Writer writer, final String text)
			throws IOException {

		return writer
				.append((char) text.length())
				.append((char) 0x00).append(text);
	}

	private static Writer writeBase64Text(final Writer writer, final String text)
			throws IOException {
		final String b64 = new String(Base64.encodeBase64(text.getBytes()));
		return writeText(writer, b64);
	}

	private void internalSendKey(final SamsungCommand key) throws IOException {
		this.writer.append((char) 0x00);
		writeText(this.writer, TV);
		writeText(this.writer, this.getKeyPayload(key));
		this.writer.flush();

		//int i =
		this.reader.read(); // Unknown byte 0x00
		//String t =
		readText(this.reader); // Read "iapp.samsung"
		//char[] c =
		readCharArray(this.reader);
	}

	public void sendKey(final SamsungCommand key) throws IOException {
		this.node.logD("Sending key " + key.getValue() + "...");
		this.checkConnection();

		try {
			this.internalSendKey(key);
		} catch (SocketException e) {
			this.node.logE("Could not send key because the server closed the connection. Reconnecting...");
			this.initialize();

			this.node.logI("Sending key " + key.getValue() + " again...");
			this.internalSendKey(key);
		}

		this.node.logI("Successfully sent key " + key.getValue());
	}

	private String getKeyPayload(final SamsungCommand key) throws IOException {
		final StringWriter writer = new StringWriter();
		writer.append((char) 0x00);
		writer.append((char) 0x00);
		writer.append((char) 0x00);

		writeBase64Text(writer, key.getValue());
		writer.flush();

		return writer.toString();
	}

	private void internalSendText(final String text) throws IOException {
		this.writer.append((char) 0x01);
		writeText(this.writer, TV);
		writeText(this.writer, this.getTextPayload(text));
		this.writer.flush();

		if (!this.reader.ready()) {
			return;
		}

		//final int i =
		this.reader.read(); // Unknown byte 0x02
		//final String t =
		readText(this.reader); // Read "iapp.samsung"
		//final char[] c =
		readCharArray(this.reader);
	}

	public void sendText(final String text) throws IOException {
		this.node.logD("Sending text \"" + text + "\"...");
		this.checkConnection();

		try {
			this.internalSendText(text);
		} catch (SocketException e) {
			this.node.logE("Could not send key because the server closed the connection. Reconnecting...");
			this.initialize();
			this.node.logI("Sending text \"" + text + "\" again...");
			this.internalSendText(text);
		}
		this.node.logD("Successfully sent text \"" + text + "\"");
	}

	private String getTextPayload(final String text) throws IOException {
		final StringWriter writer = new StringWriter();
		writer.append((char) 0x01);
		writer.append((char) 0x00);
		writeBase64Text(writer, text);
		writer.flush();
		return writer.toString();
	}

}
