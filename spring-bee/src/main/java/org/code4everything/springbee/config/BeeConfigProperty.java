package org.code4everything.springbee.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.code4everything.boot.bean.ConfigBean;

/**
 * @author pantao
 * @since 2018/10/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeeConfigProperty extends ConfigBean {

    private Integer tokenExpired;

    private String storagePath;
}
