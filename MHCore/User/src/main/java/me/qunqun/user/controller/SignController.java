package me.qunqun.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import me.qunqun.shared.entity.Result;
import me.qunqun.user.entity.dto.UserSignInDto;
import me.qunqun.user.entity.dto.UserSignUpDto;
import me.qunqun.user.entity.vo.UserInfoVo;
import me.qunqun.user.service.ISignService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "登录管理", description = "Sign API")
@RestController
@RequestMapping("/api")
public class SignController
{
	@Resource
	private ISignService signService;
	
	@Operation(summary = "登录")
	@PostMapping("/signin")
	public Result<UserInfoVo> signIn(@RequestBody UserSignInDto dto)
	{
		return Result.success(signService.signIn(dto));
	}
	
	@Operation(summary = "注册")
	@PostMapping("/signup")
	public Result<UserInfoVo> signUp(@RequestBody UserSignUpDto dto)
	{
		return Result.success(signService.signUp(dto));
	}
}
