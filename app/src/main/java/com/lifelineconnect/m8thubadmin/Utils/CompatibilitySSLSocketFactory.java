package com.lifelineconnect.m8thubadmin.Utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class CompatibilitySSLSocketFactory extends SSLSocketFactory {

    private SSLSocketFactory sslSocketFactory;
    private Set<String> additionalCipherSuites;

    public CompatibilitySSLSocketFactory(SSLSocketFactory sslSocketFactory, String... additionalCipherSuites) {
        this.sslSocketFactory = sslSocketFactory;
        this.additionalCipherSuites = new HashSet(Arrays.asList(additionalCipherSuites));
    }

    private SSLSocket configureSocket(SSLSocket socket) {
        socket.setEnabledCipherSuites(getDefaultCipherSuites());
        return socket;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        Set<String> defaultCipherSuites = new HashSet(Arrays.asList(sslSocketFactory.getDefaultCipherSuites()));
        defaultCipherSuites.addAll(additionalCipherSuites);
        return defaultCipherSuites.toArray(new String[defaultCipherSuites.size()]);
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return sslSocketFactory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(s, host, port, autoClose);
        return configureSocket(socket);
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(host, port);
        return configureSocket(socket);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(host, port, localHost, localPort);
        return configureSocket(socket);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(host, port);
        return configureSocket(socket);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(address, port, localAddress, localPort);
        return configureSocket(socket);
    }
}