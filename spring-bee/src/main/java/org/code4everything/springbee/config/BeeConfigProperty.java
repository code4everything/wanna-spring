package org.code4everything.springbee.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pantao
 * @since 2018/10/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeeConfigProperty {

    private Integer tokenExpired;

    private String[] whiteListPrefix;

    private String[] interceptorListPrefix;

    private String[] blackListPrefix;

    private String storagePath;
}
