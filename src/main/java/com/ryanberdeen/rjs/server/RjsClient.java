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

/**
 * Interface to a connected client.
 *
 * @since 0.1
 */
public interface RjsClient {
	/**
	 * Stores a value associated with the client.
	 *
	 * @param key the key of the attribute
	 * @param value the value of the attribute
	 * @return the previous value of the attribute, or <code>null</code> if the
	 *         attribute is not set
	 * @since 0.1
	 */
	public Object setAttribute(String key, Object value);

	/**
	 * Returns an attribute associated with the client.
	 *
	 * @param key the key of the attribute
	 * @return the value of the attribute, or <code>null</code> if the
	 *         attribute is not set
	 * @since 0.1
	 */
	public Object getAttribute(String key);

	/**
	 * Sends a message to the client.
	 *
	 * @since 0.1
	 */
	public void send(String message);

	/**
	 * Returns the local socket address.
	 *
	 * @since 0.1
	 */
	public SocketAddress getLocalAddress();

	/**
	 * Returns the remote socket address.
	 *
	 * @since 0.1
	 */
	public SocketAddress getRemoteAddress();

	/**
	 * Closes the connection to the client.
	 *
	 * @since 0.1
	 */
	public void close();
}
