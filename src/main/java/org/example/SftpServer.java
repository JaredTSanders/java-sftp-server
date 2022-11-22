package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.config.keys.AuthorizedKeysAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class SftpServer {
    final SftpConfig sftpConfig;

    @PostConstruct
    public void startServer() throws IOException {
        start();
    }

    private void start() throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setHost(sftpConfig.getBindAddress());
        sshd.setPort(sftpConfig.getSftpPort());
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(sftpConfig.getHostFile()));
        sshd.setSubsystemFactories(List.of(new SftpSubsystemFactory()));
        sshd.setFileSystemFactory(
                new VirtualFileSystemFactory(new File(sftpConfig.getSftpHomeDirectory()).toPath())
        );

        sshd.setPublickeyAuthenticator(new AuthorizedKeysAuthenticator(sftpConfig.getPublicKey()));
        try{
            sshd.start();
            log.info("SFTP Server Starter on Port: {}", sftpConfig.getSftpPort());
        } catch (IOException e) {
            log.error("Failed to start SFTP Server: {}", e.toString());
        }
    }
}
