import * as CryptoJS from 'crypto-js';
import JSEncrypt from 'jsencrypt';


/*
* 前端对密码使用sha256获取哈希值，并使用RSA公钥加密哈希值发送到后端
* 后端使用RSA私钥解密哈希值并与数据库进行比对
*/

const publicKey = '';

function sha256_string(data: string): string
{
    return CryptoJS.SHA256(data).toString();
}

function rsa_encrypt_string(data: string): string | false
{
    const encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey);
    return encrypt.encrypt(data);
}

function encrypt_password(password: string) : string | false
{
    return rsa_encrypt_string(sha256_string(password));
}

export {
    encrypt_password
}


