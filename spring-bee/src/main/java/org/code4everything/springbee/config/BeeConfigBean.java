package org.code4everything.springbee.config;

import lombok.*;
import org.code4everything.boot.web.mvc.FilterPath;

/**
 * @author pantao
 * @since 2018/10/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BeeConfigBean extends FilterPath {

    private Integer tokenExpired;

    private String storagePath;
}
