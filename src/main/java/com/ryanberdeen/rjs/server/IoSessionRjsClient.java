/*
 * Copyright 2008 Ryan Berdeen.
 *
 * This file is part of rjs.
 *
 * rjs is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * rjs is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with rjs.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ryanberdeen.rjs.server;

import java.net.SocketAddress;

import org.apache.mina.core.session.IoSession;

/**
 * {@link IoSession} implementation of {@link RjsClient}.
 */
public class IoSessionRjsClient implements RjsClient {
	private IoSession session;

	IoSessionRjsClient(IoSession ioSession) {
		this.session = ioSession;
	}

	/**
	 * Returns the {@link IoSession} of the connected client.
	 */
	public IoSession getIoSession() {
		return session;
	}

	public Object getAttribute(String key) {
		return session.getAttribute(key);
	}

	public Object setAttribute(String key, Object value) {
		return session.setAttribute(key, value);
	}

	public void send(String message) {
		session.write(message);
	}

	public SocketAddress getLocalAddress() {
		return session.getLocalAddress();
	}

	public SocketAddress getRemoteAddress() {
		return session.getRemoteAddress();
	}

	public void close() {
		session.close();
	}
}
