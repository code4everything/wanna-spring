package org.code4everything.springbee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2018/9/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateBO implements Serializable {

    private Integer year;

    private Integer month;

    private Integer day;
}
