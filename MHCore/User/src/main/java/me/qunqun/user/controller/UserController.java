package me.qunqun.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import me.qunqun.shared.entity.Result;
import me.qunqun.user.entity.dto.UserSignInDto;
import me.qunqun.user.entity.dto.UserSignUpDto;
import me.qunqun.user.entity.vo.UserInfoVo;
import me.qunqun.user.service.IUserService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "登录管理", description = "Sign API")
@RestController
@RequestMapping("/api")
public class UserController
{
	@Resource
	private IUserService userService;
	
	@Operation(summary = "登录")
	@PostMapping("/signin")
	public Result<UserInfoVo> signIn(@RequestBody UserSignInDto dto)
	{
		return Result.success(userService.signIn(dto));
	}
	
	@Operation(summary = "注册")
	@PostMapping("/signup")
	public Result<UserInfoVo> signUp(@RequestBody UserSignUpDto dto)
	{
		return Result.success(userService.signUp(dto));
	}
	
	@Operation(summary = "获取用户信息", description = "获取用户信息, 也可以用于检查用户是否登录")
	@GetMapping("/user/info")
	public Result<UserInfoVo> info()
	{
		return Result.success(userService.info());
	}
	
	@Operation(summary = "刷新token")
	@GetMapping("/user/refresh")
	public Result<UserInfoVo> refresh()
	{
		return Result.success(userService.refresh());
	}
}
