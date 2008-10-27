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

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class RjsServerHandler extends IoHandlerAdapter {
	public static final String POLICY_FILE_REQUEST = "<policy-file-request/>";
	public static final String POLICY_FILE_CONTENTS = "<?xml version=\"1.0\"?>" +
		"<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">" +
		"<cross-domain-policy>" +
		"<site-control permitted-cross-domain-policies=\"all\"/>" +
		"<allow-access-from domain=\"*\" to-ports=\"*\"/>" +
		"</cross-domain-policy>";

	private IoAcceptor ioAcceptor;

	public RjsServerHandler(IoAcceptor ioAcceptor) {
		this.ioAcceptor = ioAcceptor;
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message.equals(POLICY_FILE_REQUEST)) {
			session.write(POLICY_FILE_CONTENTS);
		}
		else {
			ioAcceptor.broadcast(message);
		}
	}
}
