package org.example;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Data
@Configuration
public class SftpConfig {
    @Value("${sftp.port:22}")
    private int sftpPort;

    @Value("${sftp.hostFile:src/main/resources/keys/host.ser}")
    private Path hostFile;

    @Value("${sftp.privKey:src/main/resources/keys/id_rsa}")
    private Path privateKey;

    @Value("${sftp.pubKey:src/main/resources/keys/id_rsa.pub}")
    private Path publicKey;

    @Value("${sftp.userName:user}")
    private String sftpUsername;

    @Value("${sftp.password:password}")
    private String sftpPassword;

    @Value("${sftp.homeDirectory:/tmp/app/files}")
    private String sftpHomeDirectory;

    @Value("${sftp.bindAddress:0.0.0.0}")
    private String bindAddress;
}
