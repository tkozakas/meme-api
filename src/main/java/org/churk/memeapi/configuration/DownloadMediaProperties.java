package org.churk.memeapi.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("media")
public class DownloadMediaProperties {
    private String path;
    private String schedule;
}
