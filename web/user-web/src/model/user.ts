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
    sex: number;
    idCard: string;
    birthday: string;
    type: number;
}


interface UserInfoWithToken extends UserInfo, Token
{
}

interface UserRegister extends UserLogin, UserInfo
{
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