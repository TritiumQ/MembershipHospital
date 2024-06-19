interface User
{
    id: string;
}

interface Token 
{
    token: string;    
}

interface UserLogin extends User
{
    password: string;
}

interface UserInfo extends User
{
    realName: string;
    idCard: string;
    birthday: string;
    type: number;
    sex: number;
}


interface UserInfoWithToken extends UserInfo, Token
{
}

interface UserRegister extends UserLogin, UserInfo
{
    code: number;
}

export type 
{
    User,
    Token,
    UserLogin,
    UserInfo,
    UserInfoWithToken,
    UserRegister
}