package com.ec.instructor.core.exceptions;

import com.ec.instructor.core.enums.EnumBase;
import lombok.Getter;

/**
 * @author Colin.Z.Chen
 * @description Business Exception, it need to deal with caller.
 * @time 2017/12/3.
 */
public class BizException extends Exception {
    @Getter
    private int code;

    public BizException(EnumBase enumBase){
        super(enumBase.getDesc());
        this.code = enumBase.getCode();
    }
}
