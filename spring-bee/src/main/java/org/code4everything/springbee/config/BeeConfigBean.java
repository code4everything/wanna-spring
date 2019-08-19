package org.code4everything.springbee.config;

import cn.hutool.core.util.StrUtil;
import lombok.*;
import org.code4everything.boot.web.mvc.FilterPath;
import org.code4everything.springbee.constant.BeeConfigConsts;

import java.util.Objects;

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

    @Override
    public void nulls2Default() {
        if (StrUtil.isEmpty(storagePath)) {
            storagePath = BeeConfigConsts.STORAGE_PATH;
        }
        if (Objects.isNull(tokenExpired)) {
            tokenExpired = BeeConfigConsts.TOKEN_EXPIRED;
        }
    }
}
