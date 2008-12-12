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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * The main interface between a Java application and rjs Server.
 *
 * @since 0.1
 */
public class RjsServer {
	private IoAcceptor ioAcceptor;
	private int port;

	private RjsHandler handler;

	/**
	 * Sets the port the server will listen on.
	 *
	 * @since 0.1
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Sets the target of rjs events.
	 *
	 * @since 0.1
	 */
	public void setHandler(RjsHandler handler) {
		this.handler = handler;
	}

	/**
	 * Binds to the port and starts accepting connections.
	 *
	 * @since 0.1
	 */
	public void start() throws IOException {
		ioAcceptor = new NioSocketAcceptor();
		ioAcceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), "\0", "\0")));

		ioAcceptor.setHandler(new RjsServerHandler(handler));

		ioAcceptor.bind(new InetSocketAddress(port));
	}

	/**
	 * Closes all active connections and stops accepting new ones.
	 *
	 * @since 0.1
	 */
	public void stop() {
		ioAcceptor.unbind();
		ioAcceptor.dispose();
	}
}
