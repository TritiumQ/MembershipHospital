package me.qunqun.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CrudExceptionCode implements IExceptionCode
{
	//TODO 数据操作错误代码, 待补充
	ERROR(2000, "数据操作错误"),
	ADD_ERROR(2001, "添加失败"),
	DELETE_ERROR(2002, "删除失败"),
	UPDATE_ERROR(2003, "修改失败"),
	QUERY_ERROR(2004, "查询失败"),
	;
	
	private final int code;
	private final String message;
}
