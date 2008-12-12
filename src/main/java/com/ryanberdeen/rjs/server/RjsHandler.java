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

/**
 * Handles all events from {@link RjsClient}s.
 *
 * @param <C> the class of <code>RjsClient</code>
 */
public interface RjsHandler {
	/**
	 * Called after the client has connected.
	 */
	public void clientConnected(RjsClient client) throws Exception;

	/**
	 * Called when a message has been received from a client.
	 */
	public void messageReceived(RjsClient client, String message) throws Exception;

	/**
	 * Called when an exception is thrown while handling a client event.
	 */
	public void exceptionCaught(RjsClient client, Throwable t);

	/**
	 * Called after a client has disconnected.
	 */
	public void clientDisconnected(RjsClient client) throws Exception;
}
