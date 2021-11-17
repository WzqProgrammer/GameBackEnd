package com.wzqCode.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wzqCode.utils.JwtUtil;
import com.wzqCode.mapper.AccountMapper;
import com.wzqCode.obj.db.Account;
import com.wzqCode.obj.msg.HttpStatus;
import com.wzqCode.obj.msg.server.login.SLoginMsg;
import com.wzqCode.obj.msg.server.SReturnMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginService {

    @Autowired
    AccountMapper accountMapper;

    //注册功能
    public SReturnMsg register(String account, String password){

        //使用正则表达式判断账号格式是否正确
        //字母、数字、特殊符号组成，账号字数限制为5-20个字符，特殊符号仅限 @$^!~,.*
        if(!checkAccount(account)){
            return SReturnMsg.error(
                    HttpStatus.REGISTER_ACCOUNT_FORMAT_ERROR.getCode(),
                    HttpStatus.REGISTER_ACCOUNT_FORMAT_ERROR.getMsg());
        }

        //使用正则表达式判断密码格式是否正确
        if(!checkPassword(password)){
            return SReturnMsg.error(
                    HttpStatus.REGISTER_PASSWORD_FORMAT_ERROR.getCode(),
                    HttpStatus.REGISTER_PASSWORD_FORMAT_ERROR.getMsg());
        }

        //将客户端发来的密码进行MD5加密
        String md5Password = password2MD5(password);
        //使用mybatis-plus插入数据到account表
        Account accountObj = new Account();
        accountObj.setAccount(account);
        accountObj.setPassword(md5Password);
        try{
            accountMapper.insert(accountObj);
        }catch (Exception e){
            //数据库插入失败
            return SReturnMsg.error(
                    HttpStatus.REGISTER_ACCOUNT_REPEAT_ERROR.getCode(),
                    HttpStatus.REGISTER_ACCOUNT_REPEAT_ERROR.getMsg());
        }
        //设置sRegMsg返回码200，并附加成功信息
        return SReturnMsg.success();
    }

    //登录功能
    public SReturnMsg login(String account, String password){
        SLoginMsg sLoginMsg = new SLoginMsg();

        //查找后的Account对象 = 使用mybatis从account表查找账号为参数账号的一条数据
        QueryWrapper<Account> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("account", account);
        Account accountObj = accountMapper.selectOne(objectQueryWrapper);
        if(accountObj==null){
            return SReturnMsg.error(
                    HttpStatus.LOGIN_ACCOUNT_NOT_EXIST_ERROR.getCode(),
                    HttpStatus.LOGIN_ACCOUNT_NOT_EXIST_ERROR.getMsg());
        }
        //对比MD5加密的密码
        String md5Password = password2MD5(password);
        //密码不匹配
        if(!md5Password.equals(accountObj.getPassword())){
            return SReturnMsg.error(
                    HttpStatus.LOGIN_PASSWORD_ERROR.getCode(),
                    HttpStatus.LOGIN_PASSWORD_ERROR.getMsg());
        }

        //登录账号密码均正确，生成token
        String token = JwtUtil.createAccountToken(accountObj.getId());

        sLoginMsg.setToken(token);
        return SReturnMsg.success(sLoginMsg);
    }

    //检查账号格式
    private boolean checkAccount(String accountNumber){
        String verifyAccount = "^[\\w@\\$\\^!~,.\\*]{5,20}+$";
        Pattern pattern = Pattern.compile(verifyAccount);
        Matcher matcher = pattern.matcher(accountNumber);
        return matcher.matches();
    }

    //检查密码格式
    private boolean checkPassword(String password){
        String verifyAccount = "^[\\w@\\$\\^!~,.\\*]{8,16}+$";
        Pattern pattern = Pattern.compile(verifyAccount);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    //MD5的生成
    private String password2MD5(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
