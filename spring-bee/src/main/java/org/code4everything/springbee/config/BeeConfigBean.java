package org.code4everything.springbee.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.code4everything.boot.web.mvc.FilterPath;

/**
 * @author pantao
 * @since 2018/10/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeeConfigBean extends FilterPath {

    private Integer tokenExpired;

    private String storagePath;
}
