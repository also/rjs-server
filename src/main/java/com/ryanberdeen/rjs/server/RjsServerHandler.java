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

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

class RjsServerHandler extends IoHandlerAdapter {
	public static final String POLICY_FILE_REQUEST = "<policy-file-request/>";
	public static final String POLICY_FILE_CONTENTS = "<?xml version=\"1.0\"?>" +
		"<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">" +
		"<cross-domain-policy>" +
		"<site-control permitted-cross-domain-policies=\"all\"/>" +
		"<allow-access-from domain=\"*\" to-ports=\"*\"/>" +
		"</cross-domain-policy>";

	public static final String HI_MESSAGE = "hi";

	public static final String EXPECTED_HI_MESSAGE = "expected hi";

	public static final String RJS_CLIENT_KEY = RjsServerHandler.class + ".rjsClient";

	private RjsHandler rjsHandler;

	public RjsServerHandler(RjsHandler rjsHandler) {
		this.rjsHandler = rjsHandler;
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// ignore
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// ignore
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// ignore
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		RjsClient client = getClient(session);
		if (client != null) {
			rjsHandler.clientDisconnected(client);
		}
	}

	@Override
	public void messageReceived(IoSession session, Object messageObject) throws Exception {
		String message = (String) messageObject;

		if (message.equals(POLICY_FILE_REQUEST)) {
			session.write(POLICY_FILE_CONTENTS);
		}
		else {
			RjsClient client = getClient(session);
			if (message.equals(HI_MESSAGE)) {
				if (client == null) {
					client = new IoSessionRjsClient(session);
					session.setAttribute(RJS_CLIENT_KEY, client);
					rjsHandler.clientConnected(client);
				}
				else {
					rjsHandler.messageReceived(client, message);
				}
			}
			else if (client == null) {
				session.write(EXPECTED_HI_MESSAGE);
				session.close();
			}
			else {
				rjsHandler.messageReceived(client, message);
			}
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// ignore
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		RjsClient client = getClient(session);
		if (client != null) {
			rjsHandler.exceptionCaught(client, cause);
		}
	}

	private RjsClient getClient(IoSession session) {
		return (RjsClient) session.getAttribute(RJS_CLIENT_KEY);
	}
}
