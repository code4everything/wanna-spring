package org.code4everything.springbee.constant;

import org.code4everything.boot.web.mvc.exception.HttpException;
import org.code4everything.boot.web.mvc.exception.template.UsernamePasswordIncorrectException;

/**
 * @author pantao
 * @since 2019/5/9
 **/
public class BeeErrorConsts {

    public static final HttpException CATEGORY_EXISTS = new HttpException(1001, "该分类已存在，无需添加", true);

    public static final HttpException CODE_EXCEPTION = new HttpException(1002, "请勿频繁发送验证码", true);

    public static final HttpException DAILY_NOT_FOUND = new HttpException(1003, "该日程记录尚未添加", true);

    public static final HttpException DAILIES_NOT_FOUND = new HttpException(1004, "该日程详细记录不存在", true);

    public static final HttpException DATE_FUTURE = new HttpException(1005, "该日期不合法", true);

    public static final HttpException DAILY_EXISTS = new HttpException(1006, "该日程记录已存在", true);

    public static final HttpException INCOME_NOT_FOUND = new HttpException(1007, "收入支付记录不存在", true);

    public static final HttpException JOB_EXISTS = new HttpException(1008, "该工作记录已存在", true);

    public static final HttpException JOB_NOT_FOUND = new HttpException(1009, "该工作记录尚未添加", true);

    public static final HttpException TODO_NOT_FOUND = new HttpException(1010, "该待办事项尚未添加", true);

    public static final HttpException CODE_INCORRECT = new HttpException(1011, "验证码不正确", true);

    public static final HttpException USERNAME_EXISTS = new HttpException(1012, "该用户已被注册啦", true);

    public static final HttpException EMAIL_EXISTS = new HttpException(1012, "该邮箱已被注册啦", true);

    public static final HttpException USERNAME_PASSWORD_INCORRECT = new UsernamePasswordIncorrectException(1013);

    public static final HttpException FILE_UPLOAD_ERROR = new HttpException(1014, "文件上传失败", true);

    private BeeErrorConsts() {}
}
