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

import org.apache.mina.core.service.IoHandler;
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
	public static final String SESSION_STARTED_KEY = RjsServerHandler.class + ".sessionStarted";

	public static final String EXPECTED_HI_MESSAGE = "expected hi";

	private IoHandler delegate;

	public RjsServerHandler(IoHandler delegate) {
		this.delegate = delegate;
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
		delegate.sessionIdle(session, status);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		delegate.sessionClosed(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message.equals(POLICY_FILE_REQUEST)) {
			session.write(POLICY_FILE_CONTENTS);
		}
		else if (message.equals(HI_MESSAGE)) {
			if (!session.containsAttribute(SESSION_STARTED_KEY)) {
				session.setAttribute(SESSION_STARTED_KEY);
				delegate.sessionOpened(session);
			}
			else {
				delegate.messageReceived(session, message);
			}
		}
		else if (!session.containsAttribute(SESSION_STARTED_KEY)) {
			session.write(EXPECTED_HI_MESSAGE);
			session.close();
		}
		else {
			delegate.messageReceived(session, message);
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		delegate.messageSent(session, message);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		delegate.exceptionCaught(session, cause);
	}
}
